package tracker.controllers;

import common.models.Message;
import tracker.app.PeerConnectionThread;
import tracker.app.TrackerApp;

import java.util.*;

public class TrackerConnectionController {

	// Handles an incoming file request from a peer
	public static Message handleCommand(Message message) {
		if (message == null || message.getType() != Message.Type.file_request)
			return errorMessage("invalid_command");

		String fileName = (String) message.getFromBody("name");
		if (fileName == null || fileName.isEmpty())
			return errorMessage("missing_file_name");

		// جمع‌آوری peerهایی که این فایل را دارند و hashهای مختلف
		Set<String> hashes = new HashSet<>();
		Map<String, List<PeerConnectionThread>> hashToPeers = new HashMap<>();

		for (PeerConnectionThread peer : TrackerApp.getConnections()) {
			Map<String, String> files = peer.getFileAndHashes();
			if (files.containsKey(fileName)) {
				String hash = files.get(fileName);
				hashes.add(hash);
				hashToPeers.computeIfAbsent(hash, h -> new ArrayList<>()).add(peer);
			}
		}

		if (hashes.isEmpty()) {
			// هیچ peerی فایل را ندارد
			HashMap<String, Object> body = new HashMap<>();
			body.put("response", "error");
			body.put("error", "not_found");
			return new Message(body, Message.Type.response);
		}

		if (hashes.size() > 1) {
			// چند hash مختلف برای این فایل وجود دارد
			HashMap<String, Object> body = new HashMap<>();
			body.put("response", "error");
			body.put("error", "multiple_hash");
			body.put("hashes", new ArrayList<>(hashes));
			return new Message(body, Message.Type.response);
		}

		// فقط یک hash وجود دارد، همه peerهایی که فایل را دارند
		String hash = hashes.iterator().next();
		List<PeerConnectionThread> peersWithFile = hashToPeers.get(hash);
		// یک peer را انتخاب کن (مثلاً اولین)
		PeerConnectionThread selectedPeer = peersWithFile.get(0);

		HashMap<String, Object> body = new HashMap<>();
		body.put("response", "ok");
		body.put("peer_ip", selectedPeer.getSocket().getInetAddress().getHostAddress());
		body.put("peer_port", selectedPeer.getSocket().getPort());
		body.put("md5", hash);
		body.put("file", fileName);
		return new Message(body, Message.Type.response);
	}

	private static Message errorMessage(String error) {
		HashMap<String, Object> body = new HashMap<>();
		body.put("response", "error");
		body.put("error", error);
		return new Message(body, Message.Type.response);
	}

	// Get list of files sent by a peer (asks the peer and returns result)
	public static Map<String, List<String>> getSends(PeerConnectionThread connection) {
		if (connection == null) return Collections.emptyMap();
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "get_sends");
		Message request = new Message(body, Message.Type.command);
		connection.sendMessage(request);
		Message response = connection.readMessage();
		if (response == null) return Collections.emptyMap();

		Object obj = response.getFromBody("sent_files");
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
		body.put("command", "get_receives");
		Message request = new Message(body, Message.Type.command);
		connection.sendMessage(request);
		Message response = connection.readMessage();
		if (response == null) return Collections.emptyMap();

		Object obj = response.getFromBody("received_files");
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

	// ساخت پیام handshake برای وضعیت peer (مثلاً برای handshake)
	public static Message buildStatusRequest() {
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "status");
		return new Message(body, Message.Type.command);
	}

	// ساخت پیام handshake برای دریافت لیست فایل‌های peer
	public static Message buildFilesListRequest() {
		HashMap<String, Object> body = new HashMap<>();
		body.put("command", "get_files_list");
		return new Message(body, Message.Type.command);
	}

	// به‌روزرسانی وضعیت Peer بر اساس پیام دریافتی
	public static void updatePeerStatus(PeerConnectionThread peer, Message statusMsg) {
		String ip = (String) statusMsg.getFromBody("peer");
		Integer port = null;
		try {
			port = Integer.parseInt(statusMsg.getFromBody("listen_port").toString());
		} catch (Exception ignored) {}
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
			return (HashMap<String, String>) obj;
		} else if (obj instanceof java.util.Map) {
			return new HashMap<String, String>((java.util.Map<String, String>) obj);
		}
		return new HashMap<>();
	}
}