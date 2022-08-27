package com.khopan.math;

@SuppressWarnings("serial")
public class Binary extends Number {
	private final long Value;

	public Binary(String Value) {
		this.Value = Long.parseLong(Value, 2);
	}

	@Override
	public int intValue() {
		return (int) this.Value;
	}

	@Override
	public long longValue() {
		return this.Value;
	}

	@Override
	public float floatValue() {
		return this.Value;
	}

	@Override
	public double doubleValue() {
		return this.Value;
	}

	public static Binary valueOf(String Value) {
		return new Binary(Value);
	}

	public static String toString(long Value) {
		return Long.toBinaryString(Value);
	}

	public static long getInt(String Value) {
		return Binary.valueOf(Value).intValue();
	}

	public static long getLong(String Value) {
		return Binary.valueOf(Value).longValue();
	}
}
