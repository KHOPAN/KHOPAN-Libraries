package com.khopan.theme;

import java.util.Objects;

public class ThemeLocal {
	protected static RuntimeException THEME_TYPE_NULL_EXCEPTION;
	protected static RuntimeException CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION;

	private static final RuntimeException THEME_TYPE_NULL_EXCEPTION_DEFAULT = new NullPointerException("Cannot assign value of Type because Type is null.");
	private static final RuntimeException CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION_DEFAULT = new IllegalArgumentException("Themes.CUSTOM_THEME is not available in here.");

	static {
		ThemeLocal.THEME_TYPE_NULL_EXCEPTION = ThemeLocal.THEME_TYPE_NULL_EXCEPTION_DEFAULT;
		ThemeLocal.CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION = ThemeLocal.CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION_DEFAULT;
	}

	public static void setThemeTypeNullException(RuntimeException ThemeTypeNullException) {
		ThemeLocal.THEME_TYPE_NULL_EXCEPTION = Objects.requireNonNull(ThemeTypeNullException);
	}

	public static void setCustomThemeNotAvailable(RuntimeException CustomThemeNotAvailableException) {
		ThemeLocal.CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION = Objects.requireNonNull(CustomThemeNotAvailableException);
	}

	public static RuntimeException getThemeTypeNullException() {
		return ThemeLocal.THEME_TYPE_NULL_EXCEPTION;
	}

	public static RuntimeException getCustomThemeNotAvailableException() {
		return ThemeLocal.CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION;
	}

	public static void defaultThemeTypeNullException() {
		ThemeLocal.THEME_TYPE_NULL_EXCEPTION = ThemeLocal.THEME_TYPE_NULL_EXCEPTION_DEFAULT;
	}

	public static void defaultCustomThemeNotAvailableException() {
		ThemeLocal.CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION = ThemeLocal.CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION_DEFAULT;
	}
}
