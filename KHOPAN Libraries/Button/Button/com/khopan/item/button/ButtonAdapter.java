package com.khopan.item.button;

public abstract class ButtonAdapter implements ButtonListener {
	@Override
	public void buttonEntered(ButtonEvent Event) {}

	@Override
	public void buttonPressed(ButtonEvent Event) {}

	@Override
	public void buttonReleased(ButtonEvent Event) {}

	@Override
	public void buttonClicked(ButtonEvent Event) {}

	@Override
	public void buttonExited(ButtonEvent Event) {}
}
