package com.khopan.parser.json;

import java.util.ArrayList;

public class JsonList {
	protected Object[] Value;

	public JsonList() {
		Value = new Object[0];
	}

	public void add(Object Value) {
		Object[] NewValue = new Object[this.Value.length + 1];

		for(int i = 0; i < this.Value.length; i++) {
			NewValue[i] = this.Value[i];
		}

		NewValue[this.Value.length] = Value;

		this.Value = NewValue;
	}

	public void remove(int Index) {
		Object[] NewValue = new Object[this.Value.length - 1];

		for(int i = 0; i < Index; i++) {
			NewValue[i] = this.Value[i];
		}

		for(int i = Index + 1; i < this.Value.length; i++) {
			NewValue[i - 1] = this.Value[i];
		}

		this.Value = NewValue;
	}

	public boolean has(Object Value) {
		for(int i = 0; i < this.Value.length; i++) {
			if(this.Value[i].equals(Value)) {
				return true;
			}
		}

		return false;
	}

	public Object get(int Index) {
		return this.Value[Index];
	}

	public ArrayList<Object> list() {
		ArrayList<Object> List = new ArrayList<>();

		for(Object Value : this.Value) {
			List.add(Value);
		}

		return List;
	}

	public int size() {
		return this.Value.length;
	}

	public boolean hasNext(Object Value) {
		return this.hasNext(this.indexOf(Value));
	}

	@SuppressWarnings("unused")
	public boolean hasNext(int Index) {
		try {
			Object Temp = this.Value[Index + 1];
			return true;
		} catch(ArrayIndexOutOfBoundsException Exception) {
			return false;
		}
	}

	private int indexOf(Object Value) {
		for(int i = 0; i < this.Value.length; i++) {
			if(this.Value[i].equals(Value)) {
				return i;
			}
		}

		return -1;
	}
}
