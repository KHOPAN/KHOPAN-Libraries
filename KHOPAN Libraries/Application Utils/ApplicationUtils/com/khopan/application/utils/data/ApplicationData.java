package com.khopan.application.utils.data;

import java.io.Serializable;

import com.khopan.list.ItemMap;

class ApplicationData implements Serializable {
	private static final long serialVersionUID = -4407777527655544765L;

	public final ItemMap<String, Serializable> Map;

	public ApplicationData() {
		this.Map = new ItemMap<>();
	}

	public void put(String Key, Serializable Value) {
		this.Map.put(Key, Value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String Key, Class<T> Type) {
		return (T) this.Map.get(Key);
	}
}
