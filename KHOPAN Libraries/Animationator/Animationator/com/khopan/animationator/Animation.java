package com.khopan.animationator;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Animation {
	private final int Frames;
	private final int Framerate;
	private final int ValueSize;

	private ArrayList<Keyframe> Keyframes;
	private AnimationCallback Callback;
	private ScheduledFuture<?> Future;
	private int Progress;

	public Animation(int Frames, int Framerate, int ValueSize, Keyframe... Keyframes) {
		if(Frames <= 0) {
			throw new IllegalArgumentException("Frames cannot be less than or equal to zero");
		}

		if(Framerate <= 0 || Framerate > 1000) {
			throw new IllegalArgumentException("Framerate cannot be less than or equal to zero and cannot be greater than one thousand");
		}

		if(ValueSize <= 0) {
			throw new IllegalArgumentException("ValueSize cannot be less than or equal to zero");
		}

		this.Frames = Frames;
		this.Framerate = Framerate;
		this.ValueSize = ValueSize;
		this.Keyframes = new ArrayList<>();

		if(Keyframes != null) {
			for(Keyframe Keyframe : Keyframes) {
				this.Keyframes.add(Keyframe);
			}
		}
	}

	public void addKeyframe(Keyframe Keyframe) {
		if(Keyframe == null) {
			throw new NullPointerException("Keyframe cannot be null");
		}

		if(this.getKeyframeAt(Keyframe.Frame) != null) {
			throw new IllegalArgumentException("Duplicated frame number");
		}

		if(this.Keyframes.size() > 0) {
			if(Keyframe.Frame < this.Keyframes.get(this.Keyframes.size() - 1).Frame) {
				throw new IllegalArgumentException("Frame number cannot less than previous frame number");
			}
		}

		if(Keyframe.Values.length != this.ValueSize) {
			throw new IllegalArgumentException("Keyframe's values length must equal to ValueSize");
		}

		this.Keyframes.add(Keyframe);
	}

	public void addKeyframes(Keyframe... Keyframes) {
		if(Keyframes == null) {
			throw new NullPointerException("Keyframes cannot be null");
		}

		for(Keyframe Keyframe : Keyframes) {
			this.addKeyframe(Keyframe);
		}
	}

	public void setAnimationCallback(AnimationCallback Callback) {
		if(Callback == null) {
			throw new NullPointerException("Callback cannot be null");
		}

		this.Callback = Callback;
	}

	public synchronized void start() {
		if(this.Future != null) {
			this.Future.cancel(true);
		}

		this.Progress = 0;
		this.Future = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			double[] Values = new double[this.ValueSize];
			Keyframe[] Range = this.range(this.Progress);

			for(int i = 0; i < Values.length; i++) {
				Values[i] = (this.Progress - Range[0].Frame) * (Range[1].Values[i] - Range[0].Values[i]) / (Range[1].Frame - Range[0].Frame) + Range[0].Values[i];
			}

			this.Callback.progress(Values, this.Progress);

			if(this.Progress >= this.Frames) {
				this.Future.cancel(true);
				this.Future = null;
			}

			this.Progress++;
		}, 0, 1000 / this.Framerate, TimeUnit.MILLISECONDS);
	}

	public Keyframe getKeyframeAt(int TargetFrame) { 
		for(int i = 0; i < this.Keyframes.size(); i++) {
			Keyframe Frame = this.Keyframes.get(i);

			if(Frame.Frame == TargetFrame) {
				return Frame;
			}
		}

		return null;
	}

	private Keyframe[] range(int TargetFrame) {
		if(this.Keyframes.size() > 0) {
			if(this.Keyframes.get(0).Frame != 0) {
				ArrayList<Keyframe> Frames = new ArrayList<>();
				Frames.add(new Keyframe(new double[this.ValueSize], 0));

				for(Keyframe Frame : this.Keyframes) {
					Frames.add(Frame);
				}

				this.Keyframes = Frames;
			}
		}

		for(int i = 0; i < this.Keyframes.size() - 1; i++) {
			Keyframe Frame = this.Keyframes.get(i);
			Keyframe NextFrame = this.Keyframes.get(i + 1);

			if(TargetFrame >= Frame.Frame && TargetFrame <= NextFrame.Frame) {
				return new Keyframe[] {Frame, NextFrame};
			}
		}

		return null;
	}

	public static Animation create(int Frames, int Framerate, int ValueSize, Keyframe... Keyframes) {
		return new Animation(Frames, Framerate, ValueSize, Keyframes);
	}

	public static Animation createSmoothMoveAnimation(int Frames, int Framerate, int SmoothingFrames, double SmoothingValue, double MinValue, double MaxValue, boolean In, boolean Out) {
		if(SmoothingFrames <= 0) {
			throw new IllegalArgumentException("SmoothingFrames cannot be less than or equal to zero.");
		}

		Animation Animation = new Animation(Frames, Framerate, 1);
		Animation.addKeyframe(new Keyframe(new double[] {MinValue}, 0));

		if(In) {
			Animation.addKeyframe(new Keyframe(new double[] {MinValue + SmoothingValue}, SmoothingFrames));
		}

		if(Out) {
			Animation.addKeyframe(new Keyframe(new double[] {MaxValue - SmoothingValue}, Frames - SmoothingFrames));
		}

		Animation.addKeyframe(new Keyframe(new double[] {MaxValue}, Frames));
		return Animation;
	}
}
