package com.khopan.file;

import java.io.File;

@FunctionalInterface
public interface FileChangeListener {
	public void fileChanged(File File);
}
