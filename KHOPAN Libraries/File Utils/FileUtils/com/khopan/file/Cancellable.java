package com.khopan.file;

public interface Cancellable {
	public void cancel();
	public void enableFileWatcher();
	public void disableFileWatcher();
}
