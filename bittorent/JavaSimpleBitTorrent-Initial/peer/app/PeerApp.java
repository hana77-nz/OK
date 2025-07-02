package peer.app;

import common.models.Message;
import common.utils.FileUtils;
import common.utils.MD5Hash;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class PeerApp {
	public static final int TIMEOUT_MILLIS = 500;

	// Static fields for peer's ip, port, shared folder path, sent files, received files, tracker connection thread, p2p listener thread, torrent p2p threads
	private static String peerIP;
	private static int peerPort;
	private static String trackerIP;
	private static int trackerPort;
	private static String sharedFolderPath;
	private static final Map<String, List<String>> sentFiles = new HashMap<>();
	private static final Map<String, List<String>> receivedFiles = new HashMap<>();
	private static P2TConnectionThread trackerConnectionThread;
	private static P2PListenerThread peerListenerThread;
	private static final Set<TorrentP2PThread> torrentP2PThreads = Collections.synchronizedSet(new HashSet<>());
	private static boolean exitFlag = false;

	public static boolean isEnded() {
		return exitFlag;
	}

	public static void initFromArgs(String[] args) throws Exception {
		// 1. Parse self address (ip:port)
		String[] selfAddr = args[0].split(":");
		peerIP = selfAddr[0];
		peerPort = Integer.parseInt(selfAddr[1]);

		// 2. Parse tracker address (ip:port)
		String[] trackerAddr = args[1].split(":");
		trackerIP = trackerAddr[0];
		trackerPort = Integer.parseInt(trackerAddr[1]);

		// 3. Set shared folder path
		sharedFolderPath = args[2];
		File folder = new File(sharedFolderPath);
		if (!folder.exists() || !folder.isDirectory())
			throw new IllegalArgumentException("Shared folder does not exist: " + sharedFolderPath);

		// 4. Create tracker connection thread
		trackerConnectionThread = new P2TConnectionThread(new Socket(trackerIP, trackerPort));

		// 5. Create peer listener thread
		peerListenerThread = new P2PListenerThread(peerPort);
	}

	public static void endAll() {
		exitFlag = true;
		try {
			if (trackerConnectionThread != null) trackerConnectionThread.end();
		} catch (Exception ignored) {}
		try {
			if (peerListenerThread != null) peerListenerThread.interrupt();
		} catch (Exception ignored) {}
		synchronized (torrentP2PThreads) {
			for (TorrentP2PThread t : torrentP2PThreads) {
				try {
					t.end();
				} catch (Exception ignored) {}
			}
			torrentP2PThreads.clear();
		}
		sentFiles.clear();
		receivedFiles.clear();
	}

	public static void connectTracker() {
		if (trackerConnectionThread != null && !trackerConnectionThread.isAlive()) {
			trackerConnectionThread.start();
		}
	}

	public static void startListening() {
		if (peerListenerThread != null && !peerListenerThread.isAlive()) {
			peerListenerThread.start();
		}
	}

	public static void removeTorrentP2PThread(TorrentP2PThread torrentP2PThread) {
		torrentP2PThreads.remove(torrentP2PThread);
	}

	public static void addTorrentP2PThread(TorrentP2PThread torrentP2PThread) {
		if (torrentP2PThread == null) return;
		torrentP2PThreads.add(torrentP2PThread);
	}

	public static String getSharedFolderPath() {
		return sharedFolderPath;
	}

	public static File getSharedFolder() {
		return new File(sharedFolderPath);
	}

	public static void addSentFile(String receiver, String fileNameAndHash) {
		synchronized (sentFiles) {
			sentFiles.computeIfAbsent(receiver, k -> new ArrayList<>()).add(fileNameAndHash);
		}
	}

	public static void addReceivedFile(String sender, String fileNameAndHash) {
		synchronized (receivedFiles) {
			receivedFiles.computeIfAbsent(sender, k -> new ArrayList<>()).add(fileNameAndHash);
		}
	}

	public static String getPeerIP() {
		return peerIP;
	}

	public static int getPeerPort() {
		return peerPort;
	}

	public static Map<String, List<String>> getSentFiles() {
		synchronized (sentFiles) {
			return new HashMap<>(sentFiles);
		}
	}

	public static Map<String, List<String>> getReceivedFiles() {
		synchronized (receivedFiles) {
			return new HashMap<>(receivedFiles);
		}
	}

	public static P2TConnectionThread getP2TConnection() {
		return trackerConnectionThread;
	}

	public static void updateSentFiles(String filename, String md5) {
		addSentFile("unknown", filename + " " + md5);
	}

	public static void updateReceivedFiles(String sender, String filename, String md5) {
		addReceivedFile(sender, filename + " " + md5);
	}

	public static void requestDownload(String ip, int port, String filename, String md5) throws Exception {
		// 1. Check if file already exists
		File file = new File(sharedFolderPath, filename);
		if (file.exists()) {
			throw new IOException("File already exists in shared folder");
		}

		// 2. Create download request message
		Map<String, Object> body = new HashMap<>();
		body.put("filename", filename);
		body.put("md5", md5);
		Message msg = new Message(new HashMap<>(body), Message.Type.download_request);

		// 3. Connect to peer
		try (Socket peerSocket = new Socket()) {
			peerSocket.connect(new InetSocketAddress(ip, port), TIMEOUT_MILLIS * 10);
			peerSocket.setSoTimeout(TIMEOUT_MILLIS * 20);

			// 4. Send request
			DataOutputStream out = new DataOutputStream(peerSocket.getOutputStream());
			out.writeUTF(common.utils.JSONUtils.toJson(msg));
			out.flush();

			// 5. Receive file data
			try (DataInputStream in = new DataInputStream(peerSocket.getInputStream());
				 FileOutputStream fos = new FileOutputStream(file)) {
				byte[] buffer = new byte[4096];
				int read;
				while ((read = in.read(buffer)) != -1) {
					fos.write(buffer, 0, read);
				}
			}

			// 6. Save file (already done above)

			// 7. Verify file integrity
			String fileMd5 = MD5Hash.getMD5(file.getAbsolutePath());
			if (!md5.equalsIgnoreCase(fileMd5)) {
				file.delete();
				throw new IOException("MD5 mismatch! File is corrupted or altered.");
			}

			// 8. Update received files list
			updateReceivedFiles(ip + ":" + port, filename, md5);
		}
	}

	public static String getFilesList() {
		File folder = getSharedFolder();
		File[] files = folder.listFiles();
		if (files == null || files.length == 0) return "No files in shared folder.";
		StringBuilder sb = new StringBuilder();
		for (File f : files) {
			if (f.isFile()) sb.append(f.getName()).append('\n');
		}
		return sb.length() == 0 ? "No files in shared folder." : sb.toString();
	}
}