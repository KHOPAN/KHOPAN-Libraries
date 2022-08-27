package com.khopan.item.slider;

import java.awt.Component;
import java.awt.Point;

public class SliderEvent {
	private final Component Source;
	private final Point MouseLocation;
	private final boolean Enabled;
	private final int Value;

	public SliderEvent(Component Source, Point MouseLocation, boolean Enabled, int Value) {
		this.Source = Source;
		this.MouseLocation = MouseLocation;
		this.Enabled = Enabled;
		this.Value = Value;
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

	public int getValue() {
		return this.Value;
	}
}
