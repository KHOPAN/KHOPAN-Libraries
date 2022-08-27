package com.khopan.parser.json;

public final class JsonNull {
	@Override
	public Object clone() {
		return this;
	}

	@Override
	public boolean equals(Object Object) {
		return Object == null || Object == this;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "null";
	}
}
