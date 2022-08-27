package com.khopan.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;

public class ItemMap<K, V> implements Serializable {
	private static final long serialVersionUID = -8633572053613196279L;

	protected Object[] Key;
	protected Object[] Value;

	public ItemMap() {
		Key = new Object[0];
		Value = new Object[0];
	}

	public void put(K Key, V Value) {
		Object[] NewKey = new Object[this.Key.length + 1];
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

	public void remove(K Key) {
		int Index = this.indexOf(Key);

		if(Index != -1) {
			Object[] NewKey = new Object[this.Key.length - 1];
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
		}
	}

	public boolean has(K Key) {
		for(int i = 0; i < this.Key.length; i++) {
			if(this.Key[i].equals(Key)) {
				return true;
			}
		}

		return false;
	}

	public Object get(K Key) {
		if(this.has(Key)) {
			for(int i = 0; i < this.Key.length; i++) {
				if(this.Key[i].equals(Key)) {
					return this.Value[i];
				}
			}

			return null;
		}

		return null;
	}

	public void set(K Key, V Value) {
		this.Value[this.indexOf(Key)] = Value;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<K> keyList() {
		ArrayList<K> List = new ArrayList<>();

		for(Object Key : this.Key) {
			List.add((K) Key);
		}

		return List;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<V> valueList() {
		ArrayList<V> List = new ArrayList<>();

		for(Object Value : this.Value) {
			List.add((V) Value);
		}

		return List;
	}

	private int i;

	@SuppressWarnings("unchecked")
	public ArrayList<Entry<K, V>> entryList() {
		ArrayList<Entry<K, V>> List = new ArrayList<>();

		for(i = 0; i < this.Key.length; i++) {
			Entry<K, V> Entry = new Entry<>() {
				@Override
				public K getKey() {
					return (K) ItemMap.this.Key[i];
				}

				@Override
				public V getValue() {
					return (V) ItemMap.this.Value[i];
				}

				@Override
				public V setValue(V Value) {
					return (V) (ItemMap.this.Value[i] = Value);
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
	public boolean hasNext(K Key) {
		try {
			Object Temp = this.Key[this.indexOf(Key) + 1];
			return true;
		} catch(ArrayIndexOutOfBoundsException Exception) {
			return false;
		}
	}

	private int indexOf(K Key) {
		for(int i = 0; i < this.Key.length; i++) {
			if(this.Key[i].equals(Key)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public String toString() {
		String Text = "";

		for(int i = 0; i < this.Key.length; i++) {
			Text += (i == 0 ? "" : ",") + this.Key[i] + "=" + this.Value[i];
		}

		return Text;
	}
}
