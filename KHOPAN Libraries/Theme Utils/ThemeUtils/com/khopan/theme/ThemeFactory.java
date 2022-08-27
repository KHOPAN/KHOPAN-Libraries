package com.khopan.theme;

import java.awt.Color;

@FactoryTheme
public class ThemeFactory {
	@RegisterTheme(Themes.LIGHT_THEME)
	public static final Theme LIGHT_THEME = new Theme(
			Themes.CUSTOM_THEME,
			ThemePair.of(ThemeConstants.NORMAL_STATE, new Colorstate(new Color(0x000000), new Color(0xCCCCCC), new Color(0xCCCCCC), 1)),
			ThemePair.of(ThemeConstants.ENTERED_STATE, new Colorstate(new Color(0x000000), new Color(0xCCCCCC), new Color(0x7A7A7A), 1)),
			ThemePair.of(ThemeConstants.PRESSED_STATE, new Colorstate(new Color(0x212121), new Color(0x999999), new Color(0x999999), 1)),
			ThemePair.of(ThemeConstants.DISABLED_STATE, new Colorstate(new Color(0x000000), new Color(0x6E6E6E), new Color(0x6E6E6E), 1))
			);

	@RegisterTheme(Themes.DARK_THEME)
	public static final Theme DARK_THEME = new Theme(
			Themes.CUSTOM_THEME,
			ThemePair.of(ThemeConstants.NORMAL_STATE, new Colorstate(new Color(0xFFFFFF), new Color(0x333333), new Color(0x333333), 1)),
			ThemePair.of(ThemeConstants.ENTERED_STATE, new Colorstate(new Color(0xFFFFFF), new Color(0x333333), new Color(0x858585), 1)),
			ThemePair.of(ThemeConstants.PRESSED_STATE, new Colorstate(new Color(0xDEDEDE), new Color(0x666666), new Color(0x666666), 1)),
			ThemePair.of(ThemeConstants.DISABLED_STATE, new Colorstate(new Color(0xFFFFFF), new Color(0x919191), new Color(0x919191), 1))
			);

	@RegisterTheme(Themes.WINDOW10_THEME)
	public static final Theme WINDOW10_THEME = new Theme(
			Themes.CUSTOM_THEME,
			ThemePair.of(ThemeConstants.NORMAL_STATE, new Colorstate(new Color(0x000000), new Color(0xE1E1E1), new Color(0xADADAD), 1)),
			ThemePair.of(ThemeConstants.ENTERED_STATE, new Colorstate(new Color(0x000000), new Color(0xE5F1FB), new Color(0x0078D7), 1)),
			ThemePair.of(ThemeConstants.PRESSED_STATE, new Colorstate(new Color(0x000000), new Color(0xCCE4F7), new Color(0x005499), 1)),
			ThemePair.of(ThemeConstants.DISABLED_STATE, new Colorstate(new Color(0xBFBFBF), new Color(0xCCCCCC), new Color(0x000000), 1))
			);
}
