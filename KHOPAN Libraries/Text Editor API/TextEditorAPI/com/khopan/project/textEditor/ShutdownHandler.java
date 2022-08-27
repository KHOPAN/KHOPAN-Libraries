package com.khopan.project.textEditor;

import java.io.File;
import java.nio.file.Files;

import com.khopan.application.utils.data.ApplicationDataInitializer;
import com.khopan.file.ExceptionHandler;

public class ShutdownHandler extends Thread {
	public TextEditor Editor;

	public ShutdownHandler(TextEditor Editor) {
		this.Editor = Editor;
	}

	@Override
	public void run() {
		this.deleteDirectory(TextEditor.RUNTIME_FOLDER);
		ApplicationDataInitializer.setData(TextEditor.DATA_FILE, "theme", this.Editor.WindowTheme, ExceptionHandler.DEFAULT_HANDLER);
	}

	public void deleteDirectory(File Directory) {
		File[] Contents = Directory.listFiles();

		if(Contents != null) {
			for(File File : Contents) {
				if(!Files.isSymbolicLink(File.toPath())) {
					this.deleteDirectory(File);
				}
			}
		}

		Directory.delete();
	}
}
