package com.khopan.item.comboBox;

import java.awt.Component;
import java.awt.Point;

import com.khopan.math.MathUtils;

public class ComboBoxEvent {
	private Component Source;
	private Object SelectedItem;
	private Point MouseLocation;
	private int ScrollProgress;

	public ComboBoxEvent(Component Source, Object SelectedItem, Point MouseLocation, int ScrollProgress) {
		this.Source = Source;
		this.SelectedItem = SelectedItem;
		this.MouseLocation = MouseLocation;
		this.ScrollProgress = ScrollProgress;
	}

	public Object getSelectedItem() {
		return this.SelectedItem;
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

	public int getScrollProgress() {
		return this.ScrollProgress;
	}

	public int getScrollProgress(int Min, int Max) {
		return this.ScrollProgress == -1 ? this.ScrollProgress : (int) MathUtils.map(this.ScrollProgress, 0, 100, Min, Max);
	}
}
