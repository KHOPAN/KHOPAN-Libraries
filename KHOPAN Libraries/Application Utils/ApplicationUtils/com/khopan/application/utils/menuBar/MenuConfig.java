package com.khopan.application.utils.menuBar;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;

public class MenuConfig {
	public Font Font;
	public Color Background;
	public Color Foreground;
	public Border Border;
	public Border MenuBarBorder;

	public MenuConfig() {

	}

	public Font getFont() {
		return this.Font;
	}

	public Color getBackground() {
		return this.Background;
	}

	public Color getForeground() {
		return this.Foreground;
	}

	public Border getBorder() {
		return this.Border;
	}

	public Border getMenuBarBorder() {
		return this.MenuBarBorder;
	}

	public void setFont(Font Font) {
		this.Font = Font;
	}

	public void setBackground(Color Background) {
		this.Background = Background;
	}

	public void setForeground(Color Foreground) {
		this.Foreground = Foreground;
	}

	public void setBorder(Border Border) {
		this.Border = Border;
	}

	public void setMenuBarBorder(Border MenuBarBorder) {
		this.MenuBarBorder = MenuBarBorder;
	}
}
