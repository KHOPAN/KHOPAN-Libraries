package com.khopan.theme;

class Utilities {
	public static <T> T requireNonNull(T Object, RuntimeException Error) {
		if(Object == null) {
			throw Error;
		}

		return Object;
	}
}
