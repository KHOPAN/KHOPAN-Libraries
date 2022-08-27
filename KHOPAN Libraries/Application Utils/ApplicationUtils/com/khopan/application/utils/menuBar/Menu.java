package com.khopan.application.utils.menuBar;

import java.util.Objects;

import com.khopan.list.ItemList;

public class Menu extends MenuComponent {
	public final String Name;
	final ItemList<MenuComponent> Components;

	public Menu(String Name, MenuComponent... Components) {
		this.Name = Objects.requireNonNull(Name);
		this.Components = new ItemList<>();

		if(Components != null) {
			for(MenuComponent Component : Components) {
				this.Components.add(Component);
			}
		}
	}

	public void addComponent(MenuComponent Component) {
		this.Components.add(Objects.requireNonNull(Component));
	}

	public void addComponents(MenuComponent... Components) {
		for(MenuComponent Component : Objects.requireNonNull(Components)) {
			this.Components.add(Component);
		}
	}

	public void removeComponent(int Index) {
		this.Components.remove(Index);
	}

	public ItemList<MenuComponent> list() {
		return this.Components;
	}
}
