package com.khopan.animationator;

public class Keyframe {
	protected final double[] Values;
	protected final int Frame;

	public Keyframe(double[] Values, int Frame) {
		if(Values == null || Values.length <= 0) {
			throw new IllegalArgumentException("Values cannot be null or length equal to zero");
		}

		if(Frame < 0) {
			throw new IllegalArgumentException("Frame cannot be less than zero");
		}

		this.Values = Values;
		this.Frame = Frame;
	}
}
