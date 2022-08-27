package com.khopan.theme;

public class ThemePair<T> {
	private String Key;
	private T Value;

	public ThemePair(String Key, T Value) {
		this.Key = Key;
		this.Value = Value;
	}

	public ThemePair<T> setKey(String Key) {
		this.Key = Key;
		return this;
	}

	public ThemePair<T> setValue(T Value) {
		this.Value = Value;
		return this;
	}

	public String getKey() {
		return this.Key;
	}

	public T getValue() {
		return this.Value;
	}

	@Override
	public String toString() {
		return "[" + this.Key + "=" + this.Value + "]";
	}

	public static <T> ThemePair<T> of(String Key, T Value) {
		return new ThemePair<T>(Key, Value);
	}
}
