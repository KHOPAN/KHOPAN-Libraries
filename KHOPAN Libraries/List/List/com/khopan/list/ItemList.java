package com.khopan.list;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemList<T> implements Serializable {
	private static final long serialVersionUID = 6556174333511860919L;

	protected Object[] Value;

	public ItemList() {
		Value = new Object[0];
	}

	public void add(T Value) {
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

	public boolean has(T Value) {
		for(int i = 0; i < this.Value.length; i++) {
			if(this.Value[i].equals(Value)) {
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public T get(int Index) {
		return (T) this.Value[Index];
	}

	@SuppressWarnings("unchecked")
	public ArrayList<T> list() {
		ArrayList<T> List = new ArrayList<>();

		for(Object Value : this.Value) {
			List.add((T) Value);
		}

		return List;
	}

	public int size() {
		return this.Value.length;
	}

	public boolean hasNext(T Value) {
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

	private int indexOf(T Value) {
		for(int i = 0; i < this.Value.length; i++) {
			if(this.Value[i].equals(Value)) {
				return i;
			}
		}

		return -1;
	}
}
