package com.khopan.item.button;

import java.util.EventListener;

public interface ToggleButtonListener extends EventListener {
	public void toggleButtonEntered(ToggleButtonEvent Event);
	public void toggleButtonPressed(ToggleButtonEvent Event);
	public void toggleButtonReleased(ToggleButtonEvent Event);
	public void toggleButtonClicked(ToggleButtonEvent Event);
	public void toggleButtonOn(ToggleButtonEvent Event);
	public void toggleButtonOff(ToggleButtonEvent Event);
	public void toggleButtonExited(ToggleButtonEvent Event);
}
