package tracker.app;

import common.models.ConnectionThread;
import common.models.Message;
import tracker.controllers.TrackerConnectionController;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static tracker.app.TrackerApp.TIMEOUT_MILLIS;

public class PeerConnectionThread extends ConnectionThread {
	private HashMap<String, String> fileAndHashes = new HashMap<>();

	public PeerConnectionThread(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public boolean initialHandshake() {
		try {
			socket.setSoTimeout(TIMEOUT_MILLIS);

			// 1. درخواست وضعیت Peer
			sendMessage(TrackerConnectionController.buildStatusRequest());
			Message statusMsg = readMessage();
			if (statusMsg == null) return false;
			TrackerConnectionController.updatePeerStatus(this, statusMsg);

			// 2. درخواست لیست فایل‌های Peer
			sendMessage(TrackerConnectionController.buildFilesListRequest());
			Message filesMsg = readMessage();
			if (filesMsg == null) return false;
			fileAndHashes = TrackerConnectionController.updatePeerFiles(this, filesMsg);

			// 3. اضافه کردن این اتصال به لیست Tracker
			TrackerApp.addPeerConnection(this);

			socket.setSoTimeout(0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void refreshStatus() {
		sendMessage(TrackerConnectionController.buildStatusRequest());
		Message statusMsg = readMessage();
		if (statusMsg != null) {
			TrackerConnectionController.updatePeerStatus(this, statusMsg);
		}
	}

	public void refreshFileList() {
		sendMessage(TrackerConnectionController.buildFilesListRequest());
		Message filesMsg = readMessage();
		if (filesMsg != null) {
			fileAndHashes = TrackerConnectionController.updatePeerFiles(this, filesMsg);
		}
	}

	@Override
	protected boolean handleMessage(Message message) {
		if (message.getType() == Message.Type.file_request) {
			sendMessage(TrackerConnectionController.handleCommand(message));
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		super.run();
		TrackerApp.removePeerConnection(this);
	}

	public Map<String, String> getFileAndHashes() {
		return Map.copyOf(fileAndHashes);
	}
}