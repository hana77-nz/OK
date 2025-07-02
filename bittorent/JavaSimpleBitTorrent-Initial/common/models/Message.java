package common.models;

import java.util.HashMap;
import java.util.Map;

public class Message {
	private Type type;
	private HashMap<String, Object> body;

	/*
	 * Empty constructor needed for JSON Serialization/Deserialization
	 */
	public Message() {}

	public Message(Map<String, Object> body, Type type) {
		this.body = new HashMap<>(body); // تا همچنان body داخلی HashMap باشد
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public <T> T getFromBody(String fieldName) {
		return (T) body.get(fieldName);
	}

	public int getIntFromBody(String fieldName) {
		return (int) ((double) ((Double) body.get(fieldName)));
	}

	public enum Type {
		command,
		response,
		file_request,
		download_request
	}
}
