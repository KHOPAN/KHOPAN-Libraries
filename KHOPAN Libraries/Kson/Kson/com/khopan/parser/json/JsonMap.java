package com.khopan.parser.json;

import java.util.ArrayList;
import java.util.Map.Entry;

public class JsonMap {
	protected String[] Key;
	protected Object[] Value;

	public JsonMap() {
		Key = new String[0];
		Value = new Object[0];
	}

	public void put(String Key, Object Value) {
		String[] NewKey = new String[this.Key.length + 1];
		Object[] NewValue = new Object[this.Key.length + 1];

		for(int i = 0; i < this.Key.length; i++) {
			NewKey[i] = this.Key[i];
			NewValue[i] = this.Value[i];
		}

		NewKey[this.Key.length] = Key;
		NewValue[this.Key.length] = Value;

		this.Key = NewKey;
		this.Value = NewValue;
	}

	public void remove(String Key) {
		int Index = this.indexOf(Key);

		if(Index != -1) {
			String[] NewKey = new String[this.Key.length - 1];
			Object[] NewValue = new Object[this.Key.length - 1];

			for(int i = 0; i < Index; i++) {
				NewKey[i] = this.Key[i];
				NewValue[i] = this.Value[i];
			}

			for(int i = Index + 1; i < this.Key.length; i++) {
				NewKey[i - 1] = this.Key[i];
				NewValue[i - 1] = this.Value[i];
			}

			this.Key = NewKey;
			this.Value = NewValue;
		} else {
			throw JsonParser.KEY_DOES_NOT_EXIST;
		}
	}

	public boolean has(String Key) {
		for(int i = 0; i < this.Key.length; i++) {
			if(this.Key[i].equals(Key)) {
				return true;
			}
		}

		return false;
	}

	public Object get(String Key) {
		if(this.has(Key)) {
			for(int i = 0; i < this.Key.length; i++) {
				if(this.Key[i].equals(Key)) {
					return this.Value[i];
				}
			}

			return null;
		} else {
			throw this.keyDoesNotExist(Key);
		}
	}

	public void set(String Key, Object Value) {
		this.Value[this.indexOf(Key)] = Value;
	}

	public ArrayList<String> keyList() {
		ArrayList<String> List = new ArrayList<>();

		for(String Key : this.Key) {
			List.add(Key);
		}

		return List;
	}

	public ArrayList<Object> valueList() {
		ArrayList<Object> List = new ArrayList<>();

		for(Object Value : this.Value) {
			List.add(Value);
		}

		return List;
	}

	private int i;

	public ArrayList<Entry<String, Object>> entryList() {
		ArrayList<Entry<String, Object>> List = new ArrayList<>();

		for(i = 0; i < this.Key.length; i++) {
			Entry<String, Object> Entry = new Entry<>() {
				@Override
				public String getKey() {
					return JsonMap.this.Key[i];
				}

				@Override
				public Object getValue() {
					return JsonMap.this.Value[i];
				}

				@Override
				public Object setValue(Object Value) {
					return JsonMap.this.Value[i] = Value;
				}
			};

			List.add(Entry);
		}

		return List;
	}

	public int size() {
		return this.Key.length;
	}

	@SuppressWarnings("unused")
	public boolean hasNext(String Key) {
		try {
			String Temp = this.Key[this.indexOf(Key) + 1];
			return true;
		} catch(ArrayIndexOutOfBoundsException Exception) {
			return false;
		}
	}

	private int indexOf(String Key) {
		for(int i = 0; i < this.Key.length; i++) {
			if(this.Key[i].equals(Key)) {
				return i;
			}
		}

		return -1;
	}

	private JsonException keyDoesNotExist(String Key) {
		return new JsonException("Key '" + Key + "' does not exist.");
	}
}
