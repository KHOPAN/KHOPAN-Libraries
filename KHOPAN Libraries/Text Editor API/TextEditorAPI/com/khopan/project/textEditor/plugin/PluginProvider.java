package com.khopan.project.textEditor.plugin;

import com.khopan.project.textEditor.TextEditor;

public class PluginProvider {
	protected static TextEditor Editor;

	private PluginProvider() {

	}

	public static TextEditor getLocalTextEditor() {
		return PluginProvider.Editor;
	}
}
