package com.khopan.theme;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.khopan.math.MathUtils;

public class ThemeTimer {
	private final ScheduledExecutorService Service;

	public ThemeTimer() {
		this.Service = Executors.newSingleThreadScheduledExecutor();
	}

	private Executor Executor;
	private ScheduledFuture<?> Future;
	private int Progress;
	private int MaxProgress;

	public void progress(int TimeMs, Executor Executor) {
		if(this.Future != null) {
			this.Future.cancel(false);
			this.Executor.execute(this.MaxProgress, this.MaxProgress, true);
			this.Executor = null;
			this.Future = null;
		}

		this.Progress = 0;
		this.MaxProgress = TimeMs;
		this.Executor = Executor;
		this.Future = this.Service.scheduleAtFixedRate(() -> {
			this.Executor.execute(this.Progress, TimeMs, this.Progress == TimeMs);

			if(this.Progress >= TimeMs) {
				this.Future.cancel(false);
				this.Executor = null;
				this.Future = null;
			}

			this.Progress++;
		}, 0, 1, TimeUnit.MILLISECONDS);
	}

	public static Colorstate mapColorstate(Colorstate From, Colorstate To, int Progress, int MaxProgress) {
		return new Colorstate(
				ThemeTimer.mapColor(From.getForeground(), To.getForeground(), Progress, MaxProgress),
				ThemeTimer.mapColor(From.getBackground(), To.getBackground(), Progress, MaxProgress),
				ThemeTimer.mapColor(From.getBorder(), To.getBorder(), Progress, MaxProgress),
				(int) MathUtils.map(Progress, 0, MaxProgress, From.getBorderThickness(), To.getBorderThickness())
				);
	}

	public static Color mapColor(Color From, Color To, int Progress, int MaxProgress) {
		return new Color(
				(int) MathUtils.map(Progress, 0, MaxProgress, From.getRed(), To.getRed()),
				(int) MathUtils.map(Progress, 0, MaxProgress, From.getGreen(), To.getGreen()),
				(int) MathUtils.map(Progress, 0, MaxProgress, From.getBlue(), To.getBlue())
				);
	}
}
