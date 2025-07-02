package peer.app;

import common.utils.MD5Hash;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class TorrentP2PThread extends Thread {
	private final Socket socket;
	private final File file;
	private final String receiver;
	private final BufferedOutputStream dataOutputStream;

	public TorrentP2PThread(Socket socket, File file, String receiver) throws IOException {
		this.socket = socket;
		this.file = file;
		this.receiver = receiver;
		this.dataOutputStream = new BufferedOutputStream(socket.getOutputStream());
		PeerApp.addTorrentP2PThread(this);
	}

	@Override
	public void run() {
		BufferedInputStream fileInputStream = null;
		try {
			fileInputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				dataOutputStream.write(buffer, 0, bytesRead);
			}
			dataOutputStream.flush();

			// Update sent files list (if needed)
			String md5 = MD5Hash.getMD5(file.getAbsolutePath());
			PeerApp.updateSentFiles(file.getName(), md5);
		} catch (IOException e) {
			System.err.println("Error while sending file: " + e.getMessage());
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException ignored) {}
			try {
				dataOutputStream.close();
			} catch (IOException ignored) {}
			try {
				socket.close();
			} catch (IOException ignored) {}
			PeerApp.removeTorrentP2PThread(this);
		}
	}

	public void end() {
		try {
			dataOutputStream.close();
			socket.close();
		} catch (Exception e) {}
	}
}