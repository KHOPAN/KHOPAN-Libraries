package com.khopan.project.textEditor.plugin;

import com.khopan.application.utils.menuBar.ItemType;
import com.khopan.application.utils.menuBar.Menu;
import com.khopan.application.utils.menuBar.MenuComponent;
import com.khopan.application.utils.menuBar.MenuComponentListener;
import com.khopan.application.utils.menuBar.MenuItem;

public class MenuHandler {
	public MenuHandler() {

	}

	public Menu getMenu(String Name, MenuComponent... Components) {
		return new Menu(Name, Components);
	}

	public MenuItem getMenuItem(String Name, ItemType Type) {
		return new MenuItem(Name, Type);
	}

	public MenuItem getMenuItem(String Name, ItemType Type, MenuComponentListener Listener) {
		return new MenuItem(Name, Type, Listener);
	}
}
