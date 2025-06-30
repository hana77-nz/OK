package peer.controllers;

import common.models.Message;
import peer.app.P2TConnectionThread;
import peer.app.PeerApp;
import common.utils.FileUtils;

import java.util.HashMap;
import java.util.Map;

public class P2TConnectionController {
	public static Message handleCommand(Message message) {
		String command = (String) message.getFromBody("command");
		switch (command) {
			case "status":
				return status();
			case "get_files_list":
				return getFilesList();
			case "get_sends":
				return getSends();
			case "get_receives":
				return getReceives();
			default:
				Map<String, Object> body = new HashMap<>();
				body.put("error", "Unknown command: " + command);
				return new Message(new HashMap<>(body), Message.Type.response);
		}
	}

	private static Message getReceives() {
		Map<String, Object> body = new HashMap<>();
		body.put("receives", PeerApp.getReceivedFiles());
		return new Message(new HashMap<>(body), Message.Type.response);
	}

	private static Message getSends() {
		Map<String, Object> body = new HashMap<>();
		body.put("sends", PeerApp.getSentFiles());
		return new Message(new HashMap<>(body), Message.Type.response);
	}

	public static Message getFilesList() {
		Map<String, String> files = FileUtils.listFilesInFolder(PeerApp.getSharedFolderPath());
		String formatted = FileUtils.getSortedFileList(files);
		Map<String, Object> body = new HashMap<>();
		body.put("files", formatted);
		return new Message(new HashMap<>(body), Message.Type.response);
	}

	public static Message status() {
		Map<String, Object> body = new HashMap<>();
		body.put("peer_ip", PeerApp.getPeerIP());
		body.put("peer_port", PeerApp.getPeerPort());
		body.put("shared_folder", PeerApp.getSharedFolderPath());
		body.put("sent_files", PeerApp.getSentFiles());
		body.put("received_files", PeerApp.getReceivedFiles());
		return new Message(new HashMap<>(body), Message.Type.response);
	}

	public static Message sendFileRequest(P2TConnectionThread tracker, String fileName) throws Exception {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "request_file");
		body.put("filename", fileName);
		Message request = new Message(new HashMap<>(body), Message.Type.command);
		tracker.sendMessage(request);
		Message response = tracker.readMessage();
		if (response == null || response.getFromBody("error") != null) {
			throw new Exception("File request failed: " + (response != null ? response.getFromBody("error") : "No response"));
		}
		return response;
	}
}