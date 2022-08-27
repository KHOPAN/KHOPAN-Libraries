package com.khopan.parser.json.interpreter;

import com.khopan.parser.json.JsonException;

public class JsonCase {
	private static final JsonException JSON_KEY_CANNOT_BE_NULL = new JsonException("Json Key cannot be null.");
	private static final JsonException JSON_FINDER_CANNOT_BE_NULL = new JsonException("Json Finder cannot be null.");

	private JsonKey Key;
	private JsonFinder Finder;

	public JsonCase(JsonKey Key, JsonFinder Finder) {
		this.checkJsonKey(Key);
		this.Key = Key;
		this.checkJsonFinder(Finder);
		this.Finder = Finder;
	}

	public JsonKey getKey() {
		return this.Key;
	}

	public JsonFinder getFinder() {
		return this.Finder;
	}

	public void setKey(JsonKey Key) {
		this.checkJsonKey(Key);
		this.Key = Key;
	}

	public void setFinder(JsonFinder Finder) {
		this.checkJsonFinder(Finder);
		this.Finder = Finder;
	}

	private void checkJsonKey(JsonKey Key) {
		if(Key == null) {
			throw JSON_KEY_CANNOT_BE_NULL;
		}
	}

	private void checkJsonFinder(JsonFinder Finder) {
		if(Finder == null) {
			throw JSON_FINDER_CANNOT_BE_NULL;
		}
	}
}
