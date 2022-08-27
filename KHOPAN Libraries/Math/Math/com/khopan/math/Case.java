package com.khopan.math;

import java.util.Objects;

public class Case<T> {
	protected final Executable<T> Executor;
	protected final T[] Cases;

	@SafeVarargs
	public Case(Executable<T> Executor, T... Cases) {
		this.Executor = Objects.requireNonNull(Executor);
		this.Cases = Objects.requireNonNull(Cases);
	}

	@SafeVarargs
	public static <T> Case<T> of(Executable<T> Executor, T... Cases) {
		return new Case<T>(Executor, Cases);
	}
}
