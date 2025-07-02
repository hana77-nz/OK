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
				body.put("command", command);
				body.put("response", "error");
				body.put("error", "unknown_command");
				return new Message(body, Message.Type.response);
		}
	}

	public static Message status() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "status");
		body.put("response", "ok");
		body.put("peer", PeerApp.getPeerIP());
		body.put("listen_port", PeerApp.getPeerPort());
		return new Message(body, Message.Type.response);
	}

	public static Message getFilesList() {
		Map<String, String> files = FileUtils.listFilesInFolder(PeerApp.getSharedFolderPath());
		Map<String, Object> body = new HashMap<>();
		body.put("command", "get_files_list");
		body.put("response", "ok");
		body.put("files", files);
		return new Message(body, Message.Type.response);
	}

	public static Message getSends() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "get_sends");
		body.put("response", "ok");
		body.put("sent_files", PeerApp.getSentFiles());
		return new Message(body, Message.Type.response);
	}

	public static Message getReceives() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "get_receives");
		body.put("response", "ok");
		body.put("received_files", PeerApp.getReceivedFiles());
		return new Message(body, Message.Type.response);
	}

	// درخواست فایل - توسط Peer به Tracker
	public static Message sendFileRequest(P2TConnectionThread tracker, String fileName) throws Exception {
		Map<String, Object> body = new HashMap<>();
		body.put("name", fileName);
		Message request = new Message(body, Message.Type.file_request);
		tracker.sendMessage(request);
		Message response = tracker.readMessage();
		if (response == null) {
			throw new Exception("No response from tracker");
		}
		// بررسی خطاها مطابق صورت سؤال
		String respType = (String) response.getFromBody("response");
		if ("error".equals(respType)) {
			String errorType = (String) response.getFromBody("error");
			if ("not_found".equals(errorType)) {
				throw new Exception("No peer has the file!");
			}
			if ("multiple_hash".equals(errorType)) {
				throw new Exception("Multiple hashes found!");
			}
			throw new Exception("Unknown error");
		}
		return response;
	}
}