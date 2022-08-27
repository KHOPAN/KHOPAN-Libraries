package com.khopan.application.utils.menuBar;

import java.util.Objects;

import javax.swing.ButtonGroup;

public class MenuItem extends MenuComponent {
	public final String Name;
	public final ItemType Type;
	public MenuComponentListener Listener;
	MenuGroup MenuGroup;
	ButtonGroup ButtonGroup;

	public MenuItem(String Name, ItemType Type) {
		this.Name = Objects.requireNonNull(Name);
		this.Type = Objects.requireNonNull(Type);
	}

	public MenuItem(String Name, ItemType Type, MenuComponentListener Listener) {
		this.Name = Objects.requireNonNull(Name);
		this.Type = Objects.requireNonNull(Type);
		this.Listener = Objects.requireNonNull(Listener);
	}
}
