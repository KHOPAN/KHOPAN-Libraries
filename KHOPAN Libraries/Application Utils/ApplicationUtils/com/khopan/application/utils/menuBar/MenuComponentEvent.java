package com.khopan.application.utils.menuBar;

public class MenuComponentEvent {
	private final MenuItem Source;
	private final boolean IsSelected;

	public MenuComponentEvent(MenuItem Source, boolean IsSelected) {
		this.Source = Source;
		this.IsSelected = IsSelected;
	}

	public MenuItem getSource() {
		return this.Source;
	}

	public boolean isSelected() {
		return this.IsSelected;
	}
}
