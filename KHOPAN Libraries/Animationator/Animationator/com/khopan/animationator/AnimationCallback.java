package com.khopan.animationator;

@FunctionalInterface
public interface AnimationCallback {
	public void progress(double[] Values, int Frame);
}
