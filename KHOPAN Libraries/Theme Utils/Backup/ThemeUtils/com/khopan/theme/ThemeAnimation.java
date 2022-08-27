package com.khopan.theme;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.khopan.math.MathUtils;

public class ThemeAnimation {
	private final ScheduledExecutorService Service;

	public ThemeAnimation() {
		this.Service = Executors.newSingleThreadScheduledExecutor();
	}

	private ScheduledFuture<?> Future;
	private int Progress;

	public void animateChangingColor(int TimeMs, ColorState Before, ColorState After, Executor<ColorState> Executor) {
		if(this.Future != null) {
			this.Future.cancel(false);
			this.Future = null;
		}

		this.Progress = 0;
		this.Future = this.Service.scheduleAtFixedRate(() -> {
			Executor.execute(this.progressColorState(Before, After, this.Progress, TimeMs));

			if(this.Progress >= TimeMs) {
				this.Future.cancel(false);
				this.Future = null;
			}

			this.Progress++;
		}, 0, 1, TimeUnit.MILLISECONDS);
	}

	private ColorState progressColorState(ColorState From, ColorState To, int Progress, int MaxProgress) {
		return new ColorState(
				this.progressColor(From.getForeground(), To.getForeground(), Progress, MaxProgress),
				this.progressColor(From.getBackground(), To.getBackground(), Progress, MaxProgress),
				this.progressColor(From.getBorder(), To.getBorder(), Progress, MaxProgress)
				);
	}

	private Color progressColor(Color From, Color To, int Progress, int MaxProgress) {
		return new Color(
				(int) MathUtils.map(Progress, 0, MaxProgress, From.getRed(), To.getRed()),
				(int) MathUtils.map(Progress, 0, MaxProgress, From.getGreen(), To.getGreen()),
				(int) MathUtils.map(Progress, 0, MaxProgress, From.getBlue(), To.getBlue())
				);
	}
}
