package com.khopan.theme;

import java.awt.Color;

@Factory
public class ThemeFactory {
	public static Theme LIGHT_THEME = new Theme(
			new Color(0x000000),
			new Color(0xCCCCCC),
			new Color(0xCCCCCC),
			new Color(0x000000),
			new Color(0xCCCCCC),
			new Color(0x7A7A7A),
			new Color(0x212121),
			new Color(0x999999),
			new Color(0x999999),
			new Color(0x000000),
			new Color(0x6E6E6E),
			new Color(0x6E6E6E)
			);

	public static Theme DARK_THEME = new Theme(
			new Color(0xFFFFFF),
			new Color(0x333333),
			new Color(0x333333),
			new Color(0xFFFFFF),
			new Color(0x333333),
			new Color(0x858585),
			new Color(0xDEDEDE),
			new Color(0x666666),
			new Color(0x666666),
			new Color(0xFFFFFF),
			new Color(0x919191),
			new Color(0x919191)
			);

	public static Theme WINDOW10_THEME = new Theme(
			new Color(0x000000),
			new Color(0xE1E1E1),
			new Color(0xADADAD),
			new Color(0x000000),
			new Color(0xE5F1FB),
			new Color(0x0078D7),
			new Color(0x000000),
			new Color(0xCCE4F7),
			new Color(0x005499),
			new Color(0xBFBFBF),
			new Color(0xCCCCCC),
			new Color(0x000000)
			);
}
