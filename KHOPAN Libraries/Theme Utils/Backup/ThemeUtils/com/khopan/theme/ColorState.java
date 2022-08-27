package com.khopan.theme;

import java.awt.Color;

public class ColorState {
	private Color Foreground;
	private Color Background;
	private Color Border;

	public ColorState(Color Foreground, Color Background, Color Border) {
		this.Foreground = Foreground;
		this.Background = Background;
		this.Border = Border;
	}

	public Color getForeground() {
		return this.Foreground;
	}

	public Color getBackground() {
		return this.Background;
	}

	public Color getBorder() {
		return this.Border;
	}
}
