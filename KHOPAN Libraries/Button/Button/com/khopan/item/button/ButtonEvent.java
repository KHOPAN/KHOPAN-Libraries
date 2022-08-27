package com.khopan.item.button;

import java.awt.Component;
import java.awt.Point;

public class ButtonEvent {
	private Component Source;
	private Point MouseLocation;
	private boolean Enabled;

	public ButtonEvent(Component Source, Point MouseLocation, boolean Enabled) {
		this.Source = Source;
		this.MouseLocation = MouseLocation;
		this.Enabled = Enabled;
	}

	public Component getSource() {
		return this.Source;
	}

	public Point getMouseLocation() {
		return this.MouseLocation;
	}

	public int getMouseX() {
		return this.MouseLocation.x;
	}

	public int getMouseY() {
		return this.MouseLocation.y;
	}

	public boolean isEnabled() {
		return this.Enabled;
	}
}
