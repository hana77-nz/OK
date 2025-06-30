package tracker.controllers;

import tracker.app.TrackerApp;
import tracker.app.PeerConnectionThread;

import java.util.List;
import java.util.Map;

public class TrackerCLIController {
	public static String processCommand(String command) {
		String[] tokens = command.trim().split("\\s+");
		String cmd = tokens[0].toUpperCase();
		try {
			switch (cmd) {
				case "END_PROGRAM":
					endProgram();
					return "Program ended.";
				case "REFRESH_FILES":
					return refreshFiles();
				case "RESET_CONNECTIONS":
					return resetConnections();
				case "LIST_PEERS":
					return listPeers();
				case "LIST_FILES":
					if (tokens.length < 3)
						return "Usage: LIST_FILES <ip> <port>";
					return listFiles(tokens[1], Integer.parseInt(tokens[2]));
				case "GET_RECEIVES":
					if (tokens.length < 3)
						return "Usage: GET_RECEIVES <ip> <port>";
					return getReceives(tokens[1], Integer.parseInt(tokens[2]));
				case "GET_SENDS":
					if (tokens.length < 3)
						return "Usage: GET_SENDS <ip> <port>";
					return getSends(tokens[1], Integer.parseInt(tokens[2]));
				default:
					return "Unknown command!";
			}
		} catch (Exception e) {
			return "Error processing command: " + e.getMessage();
		}
	}

	private static String getReceives(String ip, int port) {
		PeerConnectionThread peer = TrackerApp.getConnectionByIpPort(ip, port);
		if (peer == null) return "Peer not found.";
		// You should implement your own received files data here
		return "Received files for " + ip + ":" + port + ":\n(Not implemented yet)";
	}

	private static String getSends(String ip, int port) {
		PeerConnectionThread peer = TrackerApp.getConnectionByIpPort(ip, port);
		if (peer == null) return "Peer not found.";
		// You should implement your own sent files data here
		return "Sent files for " + ip + ":" + port + ":\n(Not implemented yet)";
	}

	private static String listFiles(String ip, int port) {
		PeerConnectionThread peer = TrackerApp.getConnectionByIpPort(ip, port);
		if (peer == null) return "Peer not found.";
		Map<String, String> files = peer.getFileAndHashes();
		if (files.isEmpty()) return "No files registered.";
		StringBuilder sb = new StringBuilder("Files of peer " + ip + ":" + port + ":\n");
		for (Map.Entry<String, String> entry : files.entrySet()) {
			sb.append(entry.getKey()).append("  [md5: ").append(entry.getValue()).append("]\n");
		}
		return sb.toString();
	}

	private static String listPeers() {
		List<PeerConnectionThread> peers = TrackerApp.getConnections();
		if (peers.isEmpty()) return "No connected peers.";
		StringBuilder sb = new StringBuilder("Connected peers:\n");
		for (PeerConnectionThread peer : peers) {
			try {
				sb.append(peer.getSocket().getInetAddress().getHostAddress())
						.append(":")
						.append(peer.getSocket().getPort())
						.append('\n');
			} catch (Exception ignored) {}
		}
		return sb.toString();
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