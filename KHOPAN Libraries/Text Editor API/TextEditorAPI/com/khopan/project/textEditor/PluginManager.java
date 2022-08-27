package com.khopan.project.textEditor;

import java.util.Objects;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import com.khopan.list.ItemList;

public class PluginManager {
	public final ItemList<Plugin> Plugins;
	public final TextEditor Editor;
	public final JDialog Frame;

	public PluginManager(TextEditor Editor) {
		this.Plugins = new ItemList<>();
		this.Editor = Editor;
		this.Frame = new JDialog();
		this.Frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.Frame.setSize(500, 300);
		this.Frame.setLocationRelativeTo(null);
	}

	public void addPlugin(Plugin Plugin) {
		this.Plugins.add(Objects.requireNonNull(Plugin));
	}

	public void show() {
		this.Frame.setVisible(true);
	}
}
