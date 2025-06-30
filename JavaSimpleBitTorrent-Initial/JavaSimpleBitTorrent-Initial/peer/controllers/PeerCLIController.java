package peer.controllers;

import peer.app.PeerApp;
import peer.app.P2TConnectionThread;

public class PeerCLIController {
	public static String processCommand(String command) {
		if (command == null || command.trim().isEmpty()) return "Empty command!";

		String cmd = command.trim().toUpperCase();
		if (cmd.equals("END_PROGRAM")) {
			endProgram();
			return "Program ended.";
		} else if (cmd.equals("LIST")) {
			return handleListFiles();
		} else if (cmd.startsWith("DOWNLOAD")) {
			return handleDownload(command);
		} else {
			return "Unknown command!";
		}
	}

	private static String handleListFiles() {
		try {
			return PeerApp.getFilesList();
		} catch (Exception e) {
			return "Error listing files: " + e.getMessage();
		}
	}

	private static String handleDownload(String command) {
		try {
			// Format: DOWNLOAD <filename>
			String[] parts = command.trim().split("\\s+", 2);
			if (parts.length < 2) return "Usage: DOWNLOAD <filename>";
			String filename = parts[1].trim();

			P2TConnectionThread tracker = PeerApp.getP2TConnection();
			if (tracker == null) return "Tracker connection not available.";

			// Send file request to tracker and get peer info and file hash
			var response = peer.controllers.P2TConnectionController.sendFileRequest(tracker, filename);
			String peerIp = (String) response.getFromBody("peer_ip");
			int peerPort = Integer.parseInt(response.getFromBody("peer_port").toString());
			String md5 = (String) response.getFromBody("md5");

			// Request file from peer
			PeerApp.requestDownload(peerIp, peerPort, filename, md5);

			return "Download completed successfully.";
		} catch (Exception e) {
			return "Download failed: " + e.getMessage();
		}
	}

	public static String endProgram() {
		PeerApp.endAll();
		return "";
	}
}