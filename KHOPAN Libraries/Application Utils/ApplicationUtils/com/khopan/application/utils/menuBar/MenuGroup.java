package com.khopan.application.utils.menuBar;

import java.util.Objects;

import javax.swing.ButtonGroup;

public class MenuGroup {
	MenuItem[] Items;

	private MenuGroup() {

	}

	public static void group(MenuItem... Items) {
		for(MenuItem Item : Objects.requireNonNull(Items)) {
			if(!Item.Type.equals(ItemType.RADIO)) {
				return;
			}
		}

		MenuGroup Group = new MenuGroup();
		Group.Items = Items;

		for(MenuItem Item : Items) {
			Item.ButtonGroup = new ButtonGroup();
			Item.MenuGroup = Group;
		}
	}
}
