package tracker.controllers;

import common.models.Message;
import tracker.app.PeerConnectionThread;
import tracker.app.TrackerApp;

import java.util.*;

public class TrackerConnectionController {

	// Handles an incoming file request from a peer
	public static Message handleCommand(Message message) {
		if (message == null || message.getType() != Message.Type.file_request)
			return errorMessage("Invalid or unsupported command.");

		String fileName = message.getFromBody("file");
		String hash = message.getFromBody("hash");

		if (fileName == null || fileName.isEmpty())
			return errorMessage("Missing file name.");

		List<Map<String, Object>> peersWithFile = new ArrayList<>();
		for (PeerConnectionThread peer : TrackerApp.getConnections()) {
			Map<String, String> files = peer.getFileAndHashes();
			if (files.containsKey(fileName)) {
				if (hash == null || hash.equals(files.get(fileName))) {
					Map<String, Object> info = new HashMap<>();
					info.put("ip", peer.getSocket().getInetAddress().getHostAddress());
					info.put("port", peer.getSocket().getPort());
					info.put("hash", files.get(fileName));
					peersWithFile.add(info);
				}
			}
		}

		if (peersWithFile.isEmpty()) {
			return errorMessage("No peers found with the requested file (or matching hash).");
		}

		HashMap<String, Object> body = new HashMap<>();
		body.put("peers", peersWithFile);
		body.put("file", fileName);
		if (hash != null) body.put("hash", hash);

		return new Message(body, Message.Type.response);
	}

	private static Message errorMessage(String error) {
		HashMap<String, Object> body = new HashMap<>();
		body.put("error", error);
		return new Message(body, Message.Type.response);
	}

	// Get list of files sent by a peer (asks the peer and returns result)
	public static Map<String, List<String>> getSends(PeerConnectionThread connection) {
		if (connection == null) return Collections.emptyMap();
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "GET_SENDS");
		Message request = new Message(body, Message.Type.command);
		connection.sendMessage(request);
		Message response = connection.readMessage();
		if (response == null) return Collections.emptyMap();

		Object obj = response.getFromBody("sends");
		if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			Map<String, List<String>> result = new HashMap<>();
			for (Map.Entry<?, ?> e : map.entrySet()) {
				if (e.getKey() instanceof String && e.getValue() instanceof List) {
					result.put((String) e.getKey(), (List<String>) e.getValue());
				}
			}
			return result;
		}
		return Collections.emptyMap();
	}

	// Get list of files received by a peer (asks the peer and returns result)
	public static Map<String, List<String>> getReceives(PeerConnectionThread connection) {
		if (connection == null) return Collections.emptyMap();
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "GET_RECEIVES");
		Message request = new Message(body, Message.Type.command);
		connection.sendMessage(request);
		Message response = connection.readMessage();
		if (response == null) return Collections.emptyMap();

		Object obj = response.getFromBody("receives");
		if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			Map<String, List<String>> result = new HashMap<>();
			for (Map.Entry<?, ?> e : map.entrySet()) {
				if (e.getKey() instanceof String && e.getValue() instanceof List) {
					result.put((String) e.getKey(), (List<String>) e.getValue());
				}
			}
			return result;
		}
		return Collections.emptyMap();
	}


	// ساخت پیام درخواست وضعیت Peer (مثلاً برای handshake)
	public static Message buildStatusRequest() {
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "GET_STATUS");
		return new Message(body, Message.Type.command);
	}

	// ساخت پیام درخواست لیست فایل‌های Peer
	public static Message buildFilesListRequest() {
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "GET_FILES_LIST");
		return new Message(body, Message.Type.command);
	}

	// به‌روزرسانی وضعیت Peer بر اساس پیام دریافتی
	public static void updatePeerStatus(PeerConnectionThread peer, Message statusMsg) {
		String ip = statusMsg.getFromBody("peer");
		Integer port = statusMsg.getIntFromBody("listen_port");
		if (ip != null) {
			peer.setOtherSideIP(ip);
		}
		if (port != null) {
			peer.setOtherSidePort(port);
		}
	}

	// به‌روزرسانی لیست فایل‌های Peer بر اساس پیام دریافتی
	public static HashMap<String, String> updatePeerFiles(PeerConnectionThread peer, Message filesMsg) {
		// فرض: فایل‌ها به شکل map<String, String> در کلید "files" در body پیام هستند
		Object obj = filesMsg.getFromBody("files");
		if (obj instanceof HashMap) {
			// اگر واقعاً HashMap است
			return (HashMap<String, String>) obj;
		} else if (obj instanceof java.util.Map) {
			// اگر Map است ولی HashMap نیست، تبدیل می‌کنیم
			return new HashMap<>((java.util.Map<String, String>) obj);
		}
		return new HashMap<>();
	}
}