package com.khopan.parser.json.interpreter;

import java.util.ArrayList;

import com.khopan.parser.json.JsonException;
import com.khopan.parser.json.JsonObject;

public class JsonInterpreter {
	private ArrayList<JsonCase> Cases;
	private JsonObject Object;

	public JsonInterpreter(JsonObject Object) {
		this.Cases = new ArrayList<>();
		this.Object = Object;
	}

	/*public void interpret() {
		for(String Key : Object.keyList()) {
			this.checkCase(Key);
		}
	}

	private void checkCase(String Key) {
		for(JsonCase Case : Cases) {
			JsonKey JsonKey = Case.getKey();
			String KeyString = JsonKey.getKey();

			if(Key.equals(KeyString)) {
				Case.getFinder().found(Object.get(KeyString));
				return;
			}

			if(!JsonKey.isOptional()) {
				throw this.keyDoesNotExist(JsonKey);
			}
		}
	}*/

	public void interpret() {
		ArrayList<String> KeyList = Object.keyList();

		for(int k = 0; k < Cases.size(); k++) {
			JsonCase Case = Cases.get(k);
			JsonKey JsonKey = Case.getKey();
			String Key = JsonKey.getKey();
			int Size = KeyList.size();

			for(int o = 0; o < Size; o++) {
				String ObjectKey = KeyList.get(o);

				if(ObjectKey.equals(Key)) {
					JsonFinder Finder = Case.getFinder();
					Object Found = Object.get(Key);
					Finder.found(Found);
					break;
				}

				if(o == Size - 1) {
					if(JsonKey.getKeyType().equals(KeyType.REQUIRED)) {
						throw this.keyDoesNotExist(JsonKey);
					}
				}
			}
		}
	}

	private JsonException keyDoesNotExist(JsonKey Key) {
		return new JsonException("Key '" + Key.getKey() + "' does not exist.");
	}

	public void addCases(JsonCase... Cases) {
		for(JsonCase Case : Cases) {
			this.Cases.add(Case);
		}
	}

	public void addCase(JsonCase Case) {
		this.addCases(Case);
	}
}
