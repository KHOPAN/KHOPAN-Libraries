package com.khopan.parser.json;

public class JsonException extends RuntimeException {
	private static final long serialVersionUID = 5552626938127139676L;

	public JsonException() {
		super();
	}

	public JsonException(String Message) {
		super(Message);
	}

	public JsonException(String Message, Throwable Cause) {
		super(Message, Cause);
	}

	public JsonException(Throwable Cause) {
		super(Cause);
	}

	public JsonException(String Message, Throwable Cause, boolean EnableSuppression, boolean WritableStackTrace) {
		super(Message, Cause, EnableSuppression, WritableStackTrace);
	}
}
