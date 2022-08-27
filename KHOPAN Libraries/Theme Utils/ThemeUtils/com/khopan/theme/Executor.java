package com.khopan.theme;

@FunctionalInterface
public interface Executor {
	public void execute(int Progress, int MaxProgress, boolean IsMax);
}
