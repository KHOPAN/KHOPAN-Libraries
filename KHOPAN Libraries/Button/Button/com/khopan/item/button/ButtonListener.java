package com.khopan.item.button;

import java.util.EventListener;

public interface ButtonListener extends EventListener {
	public void buttonEntered(ButtonEvent Event);
	public void buttonPressed(ButtonEvent Event);
	public void buttonReleased(ButtonEvent Event);
	public void buttonClicked(ButtonEvent Event);
	public void buttonExited(ButtonEvent Event);
}
