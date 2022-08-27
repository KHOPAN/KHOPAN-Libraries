package com.khopan.item.comboBox;

import java.util.EventListener;

public interface ComboBoxListener extends EventListener {
	public void comboBoxEntered(ComboBoxEvent Event);
	public void comboBoxPressed(ComboBoxEvent Event);
	public void comboBoxReleased(ComboBoxEvent Event);
	public void comboBoxExited(ComboBoxEvent Event);
	public void comboBoxSelected(ComboBoxEvent Event);
	public void comboBoxScrolled(ComboBoxEvent Event);
}
