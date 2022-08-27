package com.khopan.item.selectionBox;

import java.awt.Component;
import java.awt.Point;

public class SelectionBoxEvent {
	private final Component Source;
	private final Point MouseLocation;
	private final boolean Enabled;
	private final int SelectedIndex;
	private final String SelectedValue;

	public SelectionBoxEvent(Component Source, Point MouseLocation, boolean Enabled, int SelectedIndex, String SelectedValue) {
		this.Source = Source;
		this.MouseLocation = MouseLocation;
		this.Enabled = Enabled;
		this.SelectedIndex = SelectedIndex;
		this.SelectedValue = SelectedValue;
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

	public int getSelectedIndex() {
		return this.SelectedIndex;
	}

	public String getSelectedValue() {
		return this.SelectedValue;
	}
}
