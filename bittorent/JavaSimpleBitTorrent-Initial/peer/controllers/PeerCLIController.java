package peer.controllers;

import common.utils.MD5Hash;
import peer.app.PeerApp;
import peer.app.P2TConnectionThread;

public class PeerCLIController {
	public static String processCommand(String command) {
		if (command == null || command.trim().isEmpty()) return "Empty command!";

		String cmd = command.trim();
		if (PeerCommands.END.matches(cmd)) {
			endProgram();
			return "";
		} else if (PeerCommands.LIST.matches(cmd)) {
			return handleListFiles();
		} else if (PeerCommands.DOWNLOAD.matches(cmd)) {
			return handleDownload(cmd);
		} else {
			return "Invalid Command";
		}
	}

	private static String handleListFiles() {
		try {
			// نمایش لیست فایل‌ها به فرمت دقیق متن سؤال
			var files = peer.app.PeerApp.getSharedFolder().listFiles();
			if (files == null || files.length == 0) {
				return "Repository is empty.";
			}
			// فقط فایل‌های سطح اول
			java.util.TreeMap<String, String> map = new java.util.TreeMap<>();
			for (var f : files) {
				if (f.isFile()) {
                    String md5 = MD5Hash.getMD5(f.getAbsolutePath());
					map.put(f.getName(), md5);
				}
			}
			if (map.isEmpty()) return "Repository is empty.";
			StringBuilder sb = new StringBuilder();
			for (var e : map.entrySet()) {
				sb.append(e.getKey()).append(" ").append(e.getValue()).append("\n");
			}
			return sb.toString().trim();
		} catch (Exception e) {
			return "Repository is empty.";
		}
	}

	private static String handleDownload(String command) {
		try {
			// استخراج نام فایل
			String filename = PeerCommands.DOWNLOAD.getGroup(command, "1");
			if (filename == null || filename.isBlank()) return "Usage: DOWNLOAD <filename>";

			if (peer.app.PeerApp.getSharedFolder().toPath().resolve(filename).toFile().exists()) {
				return "You already have the file!";
			}

			P2TConnectionThread tracker = PeerApp.getP2TConnection();
			if (tracker == null) return "Tracker connection not available.";

			// درخواست فایل به Tracker با رعایت پیام و خطاها
			var response = peer.controllers.P2TConnectionController.sendFileRequest(tracker, filename);

			// اگر همه چیز اوکی بود:
			String peerIp = (String) response.getFromBody("peer_have");
			int peerPort = ((Double) response.getFromBody("peer_port")).intValue();
			String md5 = (String) response.getFromBody("md5");

			// دانلود فایل از peer
			try {
				PeerApp.requestDownload(peerIp, peerPort, filename, md5);
			} catch (Exception downloadErr) {
				if (downloadErr.getMessage().contains("already exists")) {
					return "You already have the file!";
				} else if (downloadErr.getMessage().contains("corrupted")) {
					return "The file has been downloaded from peer but is corrupted!";
				} else {
					return "Download failed: " + downloadErr.getMessage();
				}
			}

			return "File downloaded successfully: " + filename;
		} catch (Exception e) {
			String msg = e.getMessage();
			if (msg != null) {
				if (msg.contains("No peer has the file!")) return "No peer has the file!";
				if (msg.contains("Multiple hashes found!")) return "Multiple hashes found!";
				if (msg.contains("already have the file")) return "You already have the file!";
				if (msg.contains("corrupted")) return "The file has been downloaded from peer but is corrupted!";
			}
			return "Download failed: " + msg;
		}
	}

	public static String endProgram() {
		PeerApp.endAll();
		return "";
	}
}