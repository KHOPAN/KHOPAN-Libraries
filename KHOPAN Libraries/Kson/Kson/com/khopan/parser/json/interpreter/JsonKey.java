package com.khopan.parser.json.interpreter;

import com.khopan.parser.json.JsonException;

public class JsonKey {
	private static final JsonException KEY_CANNOT_BE_NULL = new JsonException("Key cannot be null.");
	private static final JsonException KEY_TYPE_CANNOT_BE_NULL = new JsonException("Key type cannot be null.");

	private String Key;
	private KeyType Type;

	public JsonKey(String Key, KeyType Type) {
		this.checkKey(Key);
		this.Key = Key;
		this.checkKeyType(Type);
		this.Type = Type;
	}

	public String getKey() {
		return this.Key;
	}

	public KeyType getKeyType() {
		return this.Type;
	}

	public void setKey(String Key) {
		this.checkKey(Key);
		this.Key = Key;
	}

	public void setKeyType(KeyType Type) {
		this.checkKeyType(Type);
		this.Type = Type;
	}

	private void checkKey(String Key) {
		if(Key == null) {
			throw KEY_CANNOT_BE_NULL;
		}
	}

	private void checkKeyType(KeyType Type) {
		if(Type == null) {
			throw KEY_TYPE_CANNOT_BE_NULL;
		}
	}
}
