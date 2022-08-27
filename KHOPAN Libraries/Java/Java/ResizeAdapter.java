

import java.awt.*;
import java.awt.event.*;

public class ResizeAdapter implements MouseListener, MouseMotionListener {
	private Component Component;

	private int ClickX;
	private int ClickY;

	private int ComponentWidth;
	private int ComponentHeight;
	private int ComponentX;
	private int ComponentY;

	private int TriggerRage = 5;

	@Override
	public void mouseDragged(MouseEvent Event) {
		Component = Event.getComponent();

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)) {
			Component.setLocation(Component.getX(), (Component.getY() + Event.getY()) - ClickY);
			Component.setSize(Component.getWidth(), (ComponentY + ComponentHeight) - Component.getY());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)) {
			Component.setSize(Event.getX(), Component.getHeight());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR)) {
			Component.setSize(Component.getWidth(), Event.getY());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)) {
			Component.setLocation((Component.getX() + Event.getX()) - ClickX, Component.getY());
			Component.setSize((ComponentX + ComponentWidth) - Component.getX(), Component.getHeight());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR)) {
			Component.setLocation((Component.getX() + Event.getX()) - ClickX, (Component.getY() + Event.getY()) - ClickY);
			Component.setSize((ComponentX + ComponentWidth) - Component.getX(), (ComponentY + ComponentHeight) - Component.getY());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR)) {
			Component.setLocation(Component.getX(), (Component.getY() + Event.getY()) - ClickY);
			Component.setSize(Event.getX(), (ComponentY + ComponentHeight) - Component.getY());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR)) {
			Component.setSize(Event.getX(), Event.getY());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR)) {
			Component.setLocation((Component.getX() + Event.getX()) - ClickX, Component.getY());
			Component.setSize((ComponentX + ComponentWidth) - Component.getX(), Event.getY());
		}

		if(Component.getCursor() == Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)) {
			Component.setLocation((Event.getX() + Component.getX()) - ClickX, (Event.getY() + Component.getY()) - ClickY);
		}

		if(Component.getWidth() < 10) {
			Component.setSize(10, Component.getHeight());
		}

		if(Component.getHeight() < 10) {
			Component.setSize(Component.getWidth(), 10);
		}

		if(Component.getX() < 0) {
			Component.setLocation(0, Component.getY());
		}

		if(Component.getY() < 0) {
			Component.setLocation(Component.getX(), 0);
		}
	}

	@Override
	public void mouseMoved(MouseEvent Event) {
		Component = Event.getComponent();
		Component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		if(Event.getY() <= TriggerRage) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
		}

		if(Event.getX() >= Component.getWidth() - TriggerRage) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		}

		if(Event.getY() >= Component.getHeight() - TriggerRage) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		}

		if(Event.getX() <= TriggerRage) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		}

		if((Event.getX() <= TriggerRage) && (Event.getY() <= TriggerRage)) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
		}

		if((Event.getY() <= TriggerRage) && (Event.getX() >= Component.getWidth() - TriggerRage)) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
		}

		if((Event.getX() >= Component.getWidth() - TriggerRage) && (Event.getY() >= Component.getHeight() - TriggerRage)) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
		}

		if((Event.getY() >= Component.getHeight() - TriggerRage) && (Event.getX() <= TriggerRage)) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
		}

		if((Event.getX() >= TriggerRage) && (Event.getY() >= TriggerRage) && (Event.getX() <= Component.getWidth() - TriggerRage) && (Event.getY() <= Component.getHeight() - TriggerRage)) {
			Component.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
	}

	@Override
	public void mouseClicked(MouseEvent Event) {
		Component = Event.getComponent();
	}

	@Override
	public void mousePressed(MouseEvent Event) {
		Component = Event.getComponent();

		ClickX = Event.getX();
		ClickY = Event.getY();

		ComponentWidth = Component.getWidth();
		ComponentHeight = Component.getHeight();
		ComponentX = Component.getX();
		ComponentY = Component.getY();
	}

	@Override
	public void mouseReleased(MouseEvent Event) {
		Component = Event.getComponent();
	}

	@Override
	public void mouseEntered(MouseEvent Event) {
		Component = Event.getComponent();
	}

	@Override
	public void mouseExited(MouseEvent Event) {
		Component = Event.getComponent();
	}
}
