package com.khopan.theme;

public interface Themeable {
	public void setTheme(Theme Theme);
	public Theme getTheme();
	public boolean isThemePresent(Theme Theme);
}
