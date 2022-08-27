package com.khopan.application.utils.menuBar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Objects;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.khopan.list.ItemList;

@SuppressWarnings("serial")
public class MenuBar {
	private static final Font DEFAULT_FONT = new JMenu().getFont();
	private static final Color DEFAULT_BACKGROUND = new Color(0xFFFFFF);
	private static final Color DEFAULT_FOREGROUND = new Color(0x000000);
	private static final Border DEFAULT_BORDER = new LineBorder(new Color(0x000000), 1);
	private static final Border DEFAULT_MENU_BAR_BORDER = new MenuBarBorder(new Color(0x000000), 2);

	private final ItemList<Menu> Menus;
	private final MenuConfig Config;

	private JMenuBar Bar;

	public MenuBar() {
		this.Menus = new ItemList<>();
		this.Config = new MenuConfig();
	}

	public void addMenu(Menu Menu) {
		this.Menus.add(Menu);
	}

	public void addMenus(Menu... Menus) {
		for(Menu Menu : Objects.requireNonNull(Menus)) {
			this.Menus.add(Menu);
		}
	}

	public void removeMenu(int Index) {
		this.Menus.remove(Index);
	}

	public MenuConfig config() {
		return this.Config;
	}

	public void build() {
		this.Bar = new ColorJMenuBar();
		this.Bar.setFont(this.Config.Font != null ? this.Config.Font : MenuBar.DEFAULT_FONT);
		this.Bar.setBorder(this.Config.MenuBarBorder != null ? this.Config.MenuBarBorder : MenuBar.DEFAULT_MENU_BAR_BORDER);

		for(int i = 0; i < this.Menus.size(); i++) {
			this.Bar.add(this.menu(this.Menus.get(i)));
		}
	}

	private JMenu menu(Menu Menu) {
		JMenu JMenu = new JMenu();
		JMenu.setText(Menu.Name);
		JMenu.setFont(this.Config.Font != null ? this.Config.Font : MenuBar.DEFAULT_FONT);
		JMenu.setBackground(this.Config.Background != null ? this.Config.Background : MenuBar.DEFAULT_BACKGROUND);
		JMenu.setForeground(this.Config.Foreground != null ? this.Config.Foreground : MenuBar.DEFAULT_FOREGROUND);
		JMenu.getPopupMenu().setBorder(this.Config.Border != null ? this.Config.Border : MenuBar.DEFAULT_BORDER);
		JMenu.setOpaque(true);

		ItemList<MenuComponent> Components = Menu.Components;

		for(int i = 0; i < Components.size(); i++) {
			MenuComponent Component = Components.get(i);

			if(Component instanceof MenuItem ComponentMenuItem) {
				String Name = ComponentMenuItem.Name;
				ItemType Type = ComponentMenuItem.Type;
				JMenuItem Item;

				if(Type.equals(ItemType.NORMAL)) {
					Item = new JMenuItem(Name);
				} else if(Type.equals(ItemType.RADIO)) {
					Item = new JRadioButtonMenuItem(Name);

					MenuGroup MenuGroup = ComponentMenuItem.MenuGroup;

					if(MenuGroup != null) {
						for(MenuItem MenuItem : MenuGroup.Items) {
							MenuItem.ButtonGroup.add(Item);
						}
					}
				} else if(Type.equals(ItemType.CHECKBOX)) {
					Item = new JCheckBoxMenuItem(Name);
				} else {
					Item = null;
				}

				if(ComponentMenuItem.Listener != null) {
					Item.addActionListener(Event -> ComponentMenuItem.Listener.menuComponentSelected(new MenuComponentEvent(ComponentMenuItem, Item.isSelected())));
				}

				Item.setFont(this.Config.Font != null ? this.Config.Font : MenuBar.DEFAULT_FONT);
				Item.setBackground(this.Config.Background != null ? this.Config.Background : MenuBar.DEFAULT_BACKGROUND);
				Item.setForeground(this.Config.Foreground != null ? this.Config.Foreground : MenuBar.DEFAULT_FOREGROUND);
				Item.setOpaque(true);
				JMenu.add(Item);
			} else if(Component instanceof Menu ComponentMenu) {
				JMenu.add(this.menu(ComponentMenu));
			}
		}

		return JMenu;
	}

	public JMenuBar get() {
		return this.Bar;
	}

	private class ColorJMenuBar extends JMenuBar {
		@Override
		public void paintComponent(Graphics Graphics) {
			super.paintComponent(Graphics);
			Graphics.setColor(MenuBar.this.Config.Background != null ? MenuBar.this.Config.Background : MenuBar.DEFAULT_BACKGROUND);
			Graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}

	public static class MenuBarBorder implements Border {
		private final Color Color;
		private final int Thickness;

		public MenuBarBorder(Color Color, int Thickness) {
			this.Color = Color;
			this.Thickness = Thickness;
		}

		@Override
		public void paintBorder(Component Component, Graphics Graphics, int x, int y, int width, int height) {
			Color Color = Graphics.getColor();
			Graphics.translate(x, y);
			Graphics.setColor(this.Color);
			Graphics.fillRect(0, height - this.Thickness, width, this.Thickness);
			Graphics.translate(-x, -y);
			Graphics.setColor(Color);
		}

		@Override
		public Insets getBorderInsets(Component Component) {
			return new Insets(0, 0, this.Thickness, 0);
		}

		@Override
		public boolean isBorderOpaque() {
			return false;
		}
	}
}
