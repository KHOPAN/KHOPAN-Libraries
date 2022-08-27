package com.khopan.item.slider;

public abstract class SliderAdapter implements SliderListener {
	@Override
	public void sliderEntered(SliderEvent Event) {}

	@Override
	public void sliderPressed(SliderEvent Event) {}

	@Override
	public void sliderReleased(SliderEvent Event) {}

	@Override
	public void sliderClicked(SliderEvent Event) {}

	@Override
	public void sliderExited(SliderEvent Event) {}

	@Override
	public void sliderThumbEntered(SliderEvent Event) {}

	@Override
	public void sliderThumbPressed(SliderEvent Event) {}

	@Override
	public void sliderThumbReleased(SliderEvent Event) {}

	@Override
	public void sliderThumbClicked(SliderEvent Event) {}

	@Override
	public void sliderThumbExited(SliderEvent Event) {}

	@Override
	public void sliderSlided(SliderEvent Event) {}
}
