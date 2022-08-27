package com.khopan.theme;

@FunctionalInterface
public interface Executor<Type> {
	public void execute(Type Type);
}
