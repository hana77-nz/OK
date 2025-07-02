package peer.app;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import common.models.Message;
import common.utils.JSONUtils;

import static peer.app.PeerApp.TIMEOUT_MILLIS;

public class P2PListenerThread extends Thread {
	private final ServerSocket serverSocket;

	public P2PListenerThread(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}

	private void handleConnection(Socket socket) throws Exception {
		socket.setSoTimeout(TIMEOUT_MILLIS);

		try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
			String msgString = in.readUTF();
			Message msg = JSONUtils.fromJson(msgString);

			if (msg != null && msg.getType() == Message.Type.download_request) {
				String filename = msg.getFromBody("filename");
				File file = new File(PeerApp.getSharedFolder(), filename);
				new TorrentP2PThread(socket, file, filename).start();
				return;
			}
		} catch (Exception e) {
			// If we can't read or it's not a download_request, just close
		}

		try { socket.close(); } catch (Exception ignored) {}
	}
	@Override
	public void run() {
		while (!PeerApp.isEnded()) {
			try {
				Socket socket = serverSocket.accept();
				handleConnection(socket);
			} catch (Exception e) {
				break;
			}
		}

		try {serverSocket.close();} catch (Exception ignored) {}
	}
}