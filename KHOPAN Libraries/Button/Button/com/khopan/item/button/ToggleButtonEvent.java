package com.khopan.item.button;

import java.awt.Component;
import java.awt.Point;

public class ToggleButtonEvent {
	private Component Source;
	private Point MouseLocation;
	private boolean Enabled;
	private boolean IsOn;

	public ToggleButtonEvent(Component Source, Point MouseLocation, boolean Enabled, boolean IsOn) {
		this.Source = Source;
		this.MouseLocation = MouseLocation;
		this.Enabled = Enabled;
		this.IsOn = IsOn;
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

	public boolean isOn() {
		return this.IsOn;
	}
}
