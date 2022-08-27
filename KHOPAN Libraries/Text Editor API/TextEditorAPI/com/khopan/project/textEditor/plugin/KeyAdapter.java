package com.khopan.project.textEditor.plugin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAdapter implements KeyListener {
	public KeyHandler KeyTyped;
	public KeyHandler KeyPressed;
	public KeyHandler KeyReleased;

	public KeyAdapter(KeyHandler KeyTyped, KeyHandler KeyPressed, KeyHandler KeyReleased) {
		this.KeyTyped = KeyTyped;
		this.KeyPressed = KeyPressed;
		this.KeyReleased = KeyReleased;
	}

	@Override
	public void keyTyped(KeyEvent Event) {
		if(this.KeyTyped != null) {
			this.KeyTyped.keyAction(Event);
		}
	}

	@Override
	public void keyPressed(KeyEvent Event) {
		if(this.KeyPressed != null) {
			this.KeyPressed.keyAction(Event);
		}
	}

	@Override
	public void keyReleased(KeyEvent Event) {
		if(this.KeyReleased != null) {
			this.KeyReleased.keyAction(Event);
		}
	}
}
