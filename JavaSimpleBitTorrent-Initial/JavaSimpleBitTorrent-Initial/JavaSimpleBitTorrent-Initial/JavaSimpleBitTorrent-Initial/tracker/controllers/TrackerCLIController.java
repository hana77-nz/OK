package tracker.controllers;

import tracker.app.TrackerApp;
import tracker.app.PeerConnectionThread;

import java.util.List;
import java.util.Map;

public class TrackerCLIController {
	public static String processCommand(String command) {
		if (command == null || command.trim().isEmpty()) return "Empty command!";

		String cmd = command.trim();
		if (TrackerCommands.END.getMatcher(cmd).matches()) {
			endProgram();
			return "Program ended.";
		} else if (TrackerCommands.REFRESH_FILES.getMatcher(cmd).matches()) {
			return refreshFiles();
		} else if (TrackerCommands.RESET_CONNECTIONS.getMatcher(cmd).matches()) {
			return resetConnections();
		} else if (TrackerCommands.LIST_PEERS.getMatcher(cmd).matches()) {
			return listPeers();
		} else if (TrackerCommands.LIST_FILES.getMatcher(cmd).matches()) {
			var m = TrackerCommands.LIST_FILES.getMatcher(cmd);
			m.find();
			String ip = m.group(1);
			int port = Integer.parseInt(m.group(2));
			return listFiles(ip, port);
		} else if (TrackerCommands.GET_RECEIVES.getMatcher(cmd).matches()) {
			var m = TrackerCommands.GET_RECEIVES.getMatcher(cmd);
			m.find();
			String ip = m.group(1);
			int port = Integer.parseInt(m.group(2));
			return getReceives(ip, port);
		} else if (TrackerCommands.GET_SENDS.getMatcher(cmd).matches()) {
			var m = TrackerCommands.GET_SENDS.getMatcher(cmd);
			m.find();
			String ip = m.group(1);
			int port = Integer.parseInt(m.group(2));
			return getSends(ip, port);
		} else {
			return "Unknown command!";
		}
	}

	// فقط true peerها را نمایش بده (مثلاً user peer را حذف کن اگر راهی برای تشخیصش داری)
	private static String listPeers() {
		List<PeerConnectionThread> peers = TrackerApp.getConnections();
		if (peers.isEmpty()) return "No connected peers.";
		StringBuilder sb = new StringBuilder("Connected peers:\n");
		for (PeerConnectionThread peer : peers) {
			try {
				// اگر فیلدی مثل isUserPeer داری، اینجا شرط بگذار:
				 if (peer.isUserPeer()) continue;
				sb.append(peer.getSocket().getInetAddress().getHostAddress())
						.append(":")
						.append(peer.getSocket().getPort())
						.append('\n');
			} catch (Exception ignored) {}
		}
		return sb.toString().trim();
	}

	private static String listFiles(String ip, int port) {
		PeerConnectionThread peer = TrackerApp.getConnectionByIpPort(ip, port);
		if (peer == null) return "Peer not found.";
		Map<String, String> files = peer.getFileAndHashes();
		if (files == null || files.isEmpty()) return "No files registered.";
		StringBuilder sb = new StringBuilder("Files of peer " + ip + ":" + port + ":\n");
		for (Map.Entry<String, String> entry : files.entrySet()) {
			sb.append(entry.getKey()).append("  [md5: ").append(entry.getValue()).append("]\n");
		}
		return sb.toString().trim();
	}

	private static String getReceives(String ip, int port) {
		PeerConnectionThread peer = TrackerApp.getConnectionByIpPort(ip, port);
		if (peer == null) return "Peer not found.";
		Map<String, java.util.List<String>> files = tracker.controllers.TrackerConnectionController.getReceives(peer);
		if (files.isEmpty()) return "No received files registered.";
		StringBuilder sb = new StringBuilder("Received files for " + ip + ":" + port + ":\n");
		for (var entry : files.entrySet()) {
			sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
		}
		return sb.toString().trim();
	}

	private static String getSends(String ip, int port) {
		PeerConnectionThread peer = TrackerApp.getConnectionByIpPort(ip, port);
		if (peer == null) return "Peer not found.";
		Map<String, java.util.List<String>> files = tracker.controllers.TrackerConnectionController.getSends(peer);
		if (files.isEmpty()) return "No sent files registered.";
		StringBuilder sb = new StringBuilder("Sent files for " + ip + ":" + port + ":\n");
		for (var entry : files.entrySet()) {
			sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
		}
		return sb.toString().trim();
	}

	private static String resetConnections() {
		List<PeerConnectionThread> peers = TrackerApp.getConnections();
		for (PeerConnectionThread peer : peers) {
			peer.refreshStatus();
			peer.refreshFileList();
		}
		return "Refreshed status and file list for all peers.";
	}

	private static String refreshFiles() {
		List<PeerConnectionThread> peers = TrackerApp.getConnections();
		for (PeerConnectionThread peer : peers) {
			peer.refreshFileList();
		}
		return "Refreshed file list for all peers.";
	}

	private static String endProgram() {
		TrackerApp.endAll();
		return "";
	}
}