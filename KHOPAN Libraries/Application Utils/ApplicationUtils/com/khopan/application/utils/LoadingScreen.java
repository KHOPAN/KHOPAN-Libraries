package com.khopan.application.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

public abstract class LoadingScreen {
	CallbackHandler<?> Handler;
	JFrame Frame;

	public LoadingScreen() {

	}

	public void loadingDone() {
		if(Handler != null) {
			Handler.callback(null);
		}
	}

	public JFrame getFrame() {
		return this.Frame;
	}

	public abstract Rectangle getBounds(Dimension size);
	public abstract boolean isUndecorated();
	public abstract void paintLoadingScreen(Graphics2D Graphics2D, Dimension size);
	public abstract void beginLoading();
	public abstract void configFrame(JFrame Frame);
}
