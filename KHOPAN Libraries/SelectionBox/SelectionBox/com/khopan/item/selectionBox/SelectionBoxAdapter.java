package com.khopan.item.selectionBox;

public abstract class SelectionBoxAdapter implements SelectionBoxListener {
	@Override
	public void selectionBoxEntered(SelectionBoxEvent Event) {}

	@Override
	public void selectionBoxPressed(SelectionBoxEvent Event) {}

	@Override
	public void selectionBoxReleased(SelectionBoxEvent Event) {}

	@Override
	public void selectionBoxClicked(SelectionBoxEvent Event) {}

	@Override
	public void selectionBoxExited(SelectionBoxEvent Event) {}

	@Override
	public void selectionBoxSelected(SelectionBoxEvent Event) {}
}
