package com.khopan.item.selectionBox;

import java.util.EventListener;

public interface SelectionBoxListener extends EventListener {
	public void selectionBoxEntered(SelectionBoxEvent Event);
	public void selectionBoxPressed(SelectionBoxEvent Event);
	public void selectionBoxReleased(SelectionBoxEvent Event);
	public void selectionBoxClicked(SelectionBoxEvent Event);
	public void selectionBoxExited(SelectionBoxEvent Event);
	public void selectionBoxSelected(SelectionBoxEvent Event);
}
