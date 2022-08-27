package com.khopan.item.slider;

import java.util.EventListener;

public interface SliderListener extends EventListener {
	public void sliderEntered(SliderEvent Event);
	public void sliderPressed(SliderEvent Event);
	public void sliderReleased(SliderEvent Event);
	public void sliderClicked(SliderEvent Event);
	public void sliderExited(SliderEvent Event);
	public void sliderThumbEntered(SliderEvent Event);
	public void sliderThumbPressed(SliderEvent Event);
	public void sliderThumbReleased(SliderEvent Event);
	public void sliderThumbClicked(SliderEvent Event);
	public void sliderThumbExited(SliderEvent Event);
	public void sliderSlided(SliderEvent Event);
}
