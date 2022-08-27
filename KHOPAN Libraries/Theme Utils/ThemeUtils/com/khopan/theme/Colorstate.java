package com.khopan.theme;

import java.awt.Color;

public class Colorstate {
	private Color Foreground;
	private Color Background;
	private Color Border;
	private int BorderThickness;

	public Colorstate(Color Foreground, Color Background, Color Border, int BorderThickness) {
		this.Foreground = Foreground;
		this.Background = Background;
		this.Border = Border;
		this.BorderThickness = BorderThickness;
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

	public int getBorderThickness() {
		return this.BorderThickness;
	}

	@Override
	public String toString() {
		return "[foreground=" + this.color(this.Foreground) + ",background=" + this.color(this.Background) + ",border=" + this.color(this.Border) + ",border-thickness=[" + this.BorderThickness + "]";
	}

	private String color(Color Color) {
		return "[0x" + String.format("%02x%02x%02x", Color.getRed(), Color.getGreen(), Color.getBlue()).toUpperCase() + "]";
	}

	public static Colorstate getDefault() {
		return new Colorstate(
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				0
				);
	}
}
