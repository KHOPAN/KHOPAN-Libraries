package com.khopan.item.selectionBox;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.khopan.list.ItemList;
import com.khopan.math.MathUtils;
import com.khopan.theme.Colorstate;
import com.khopan.theme.Theme;
import com.khopan.theme.ThemeConstants;
import com.khopan.theme.ThemeFactory;
import com.khopan.theme.ThemeTimer;
import com.khopan.theme.Themeable;

public class SelectionBox extends JComponent implements Themeable {
	private static final long serialVersionUID = 8968368414453411514L;

	private static final Theme DEFAULT_THEME = ThemeFactory.LIGHT_THEME;
	private static final int DEFAULT_STROKE_WEIGHT = 2;
	private static final boolean DEFAULT_ENABLE_ANIMATION = true;
	private static final int DEFAULT_ANIMATION_DELAY = 200;
	private static final boolean DEFALUT_ROUNDED_BORDER = false;

	private final Listener Listener;
	private final ItemList<String> Items;

	private Theme Theme;
	private int StrokeWeight;
	private boolean EnableAnimation;
	private int AnimationDelay;
	private boolean RoundedBorder;

	private int SelectedIndex;
	private Colorstate Color;

	public SelectionBox() {
		this(SelectionBox.DEFAULT_THEME);
	}

	public SelectionBox(Theme Theme) {
		this.Listener = new Listener();
		this.Items = new ItemList<>();

		this.setTheme(Theme);
		this.StrokeWeight = SelectionBox.DEFAULT_STROKE_WEIGHT;
		this.EnableAnimation = SelectionBox.DEFAULT_ENABLE_ANIMATION;
		this.AnimationDelay = SelectionBox.DEFAULT_ANIMATION_DELAY;
		this.RoundedBorder = SelectionBox.DEFALUT_ROUNDED_BORDER;

		this.setFont(new JLabel().getFont());
		this.addMouseListener(this.Listener);
	}

	public void addItems(String... Items) {
		for(String Item : Items) {
			this.Items.add(Item);
		}
	}

	public void addItem(String Item) {
		this.addItems(Item);
	}

	@Override
	public Theme getTheme() {
		return this.Theme;
	}

	public int getStrokeWeight() {
		return this.StrokeWeight;
	}

	public boolean isEnableAnimation() {
		return this.EnableAnimation;
	}

	public int getAnimationDelay() {
		return this.AnimationDelay;
	}

	public boolean isRoundedBorder() {
		return this.RoundedBorder;
	}

	@Override
	public void setTheme(Theme Theme) {
		this.Theme = Theme;
		this.Color = this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class);
	}

	public void setStrokeWeight(int StrokeWeight) {
		this.StrokeWeight = StrokeWeight;
	}

	public void setEnableAnimation(boolean EnableAnimation) {
		this.EnableAnimation = EnableAnimation;
	}

	public void setAnimationDelay(int AnimationDelay) {
		this.AnimationDelay = AnimationDelay;
	}

	public void setRoundedBorder(boolean RoundedBorder) {
		this.RoundedBorder = RoundedBorder;
	}

	public void addSelectionBoxListener(SelectionBoxListener Listener) {
		this.listenerList.add(SelectionBoxListener.class, Listener);
	}

	public void addSelectionBoxListeners(SelectionBoxListener... Listeners) {
		for(SelectionBoxListener Listener : Listeners) {
			this.listenerList.add(SelectionBoxListener.class, Listener);
		}
	}

	public SelectionBoxListener[] getSelectionBoxListeners() {
		return this.listenerList.getListeners(SelectionBoxListener.class);
	}

	public void removeSelectionBoxListener(SelectionBoxListener Listener) {
		this.listenerList.remove(SelectionBoxListener.class, Listener);
	}

	public int getSelectedIndex() {
		return this.SelectedIndex;
	}

	public void setSelectedIndex(int SelectedIndex) {
		this.SelectedIndex = SelectedIndex;
	}

	public String getSelectedItem() {
		return this.Items.get(this.SelectedIndex);
	}

	@Override
	public void setEnabled(boolean Enabled) {
		if(Enabled != this.isEnabled()) {
			super.setEnabled(Enabled);
			this.Listener.enableUpdate();
		} else {
			super.setEnabled(Enabled);
		}
	}

	private Graphics2D Graphics2D;
	private FontMetrics Metrics;

	private int barWidth;
	private int offset;
	private int law;
	private int raw;

	@Override
	public void paint(Graphics Graphics) {
		this.Graphics2D = (Graphics2D) Graphics;
		this.Graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.Metrics = this.getFontMetrics(this.getFont());
		this.draw();
	}

	private void draw() {
		this.barWidth = this.round(this.getWidth() / ((double) this.Items.size()));

		this.Graphics2D.setColor(this.Color.getBorder());
		this.rectangle(0, 0, this.getWidth(), this.getHeight(), false, this.Color.getBackground());
		this.Graphics2D.setColor(this.Color.getBorder());
		this.innerRectangleByIndex(this.SelectedIndex);
		this.Graphics2D.setColor(this.Color.getForeground());

		for(int i = 0; i < this.Items.size(); i++) {
			this.text(this.Items.get(i), i * this.barWidth, 0, this.barWidth, this.getHeight());
		}
	}

	private int round(double Number) {
		return (int) Math.round(Number);
	}

	private void text(String Text, int x, int y, int width, int height) {
		this.Graphics2D.drawString(Text, x + (width - this.Metrics.stringWidth(Text)) / 2, y + (height / 2) + (this.Metrics.getHeight() / 4));
	}

	private void innerRectangleByIndex(int index) {
		this.innerRectangle(index * this.barWidth + this.offset, this.StrokeWeight, this.barWidth, this.getHeight() - this.StrokeWeight * 2, this.offset == 0 ? this.law(index) : this.law, this.offset == 0 ? this.raw(index) : this.raw);
	}

	public int law(int index) {
		return this.RoundedBorder ? ((index == 0) ? 10 : 0) : 0;
	}

	public int raw(int index) {
		return this.RoundedBorder ? ((index == SelectionBox.this.Items.size() - 1) ? 10 : 0) : 0;
	}

	private void innerRectangle(int x, int y, int width, int height, int leftArcSize, int rightArcSize) {
		this.Graphics2D.fillRoundRect(x, y, this.round(width * 0.75d), height, leftArcSize, leftArcSize);
		this.Graphics2D.fillRoundRect(this.round(x + (width * 0.25d)), y, this.round(width * 0.75d), height, rightArcSize, rightArcSize);
	}

	private void rectangle(int x, int y, int width, int height, boolean fill, Color fillBackground) {
		if(this.RoundedBorder) {
			this.Graphics2D.fillRoundRect(x, y, width, height, 10, 10);

			if(!fill) {
				this.Graphics2D.setColor(fillBackground);
				this.Graphics2D.fillRoundRect(x + this.StrokeWeight, y + this.StrokeWeight, width - this.StrokeWeight * 2, height - this.StrokeWeight * 2, 10, 10);
			}
		} else {
			this.Graphics2D.fillRect(x, y, width, height);

			if(!fill) {
				this.Graphics2D.setColor(fillBackground);
				this.Graphics2D.fillRect(x + this.StrokeWeight, y + this.StrokeWeight, width - this.StrokeWeight * 2, height - this.StrokeWeight * 2);
			}
		}
	}

	private class Listener extends MouseAdapter {
		public final ThemeTimer Timer;

		public boolean Run;

		public Listener() {
			this.Timer = new ThemeTimer();
			this.Run = true;
		}

		@Override
		public void mouseEntered(MouseEvent Event) {
			for(SelectionBoxListener Listener : SelectionBox.this.listenerList.getListeners(SelectionBoxListener.class)) {
				Listener.selectionBoxEntered(this.selectionBoxEvent());
			}
		}

		@Override
		public void mousePressed(MouseEvent Event) {
			int index = SelectionBox.this.round(Event.getX() / SelectionBox.this.barWidth);

			if(SelectionBox.this.isEnabled()) {
				if(index != SelectionBox.this.SelectedIndex) {
					if(SelectionBox.this.EnableAnimation) {
						this.animation(index);
					} else {
						SelectionBox.this.SelectedIndex = index;

						for(SelectionBoxListener Listener : SelectionBox.this.listenerList.getListeners(SelectionBoxListener.class)) {
							Listener.selectionBoxSelected(this.selectionBoxEvent());
						}

						SelectionBox.this.repaint();
					}
				}
			}
		}

		public void enableUpdate() {
			if(this.Run) {
				Colorstate ColorBefore = SelectionBox.this.Color;
				Colorstate Color = SelectionBox.this.isEnabled() ? SelectionBox.this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class) : SelectionBox.this.Theme.get(ThemeConstants.DISABLED_STATE, Colorstate.class);

				this.Timer.progress(SelectionBox.this.AnimationDelay, (Progress, MaxProgress, IsMax) -> {
					SelectionBox.this.Color = ThemeTimer.mapColorstate(ColorBefore, Color, Progress, MaxProgress);

					if(IsMax) {
						this.Run = true;
					}

					SelectionBox.this.repaint();
				});
			}
		}

		public void animation(int index) {
			if(this.Run) {
				int currentIndex = SelectionBox.this.SelectedIndex;
				int outMin = 0;
				int outMax = (index > currentIndex) ? (index - currentIndex) * SelectionBox.this.barWidth : -((currentIndex - index) * SelectionBox.this.barWidth);

				this.Timer.progress(SelectionBox.this.AnimationDelay, (Progress, MaxProgress, IsMax) -> {
					SelectionBox.this.offset = SelectionBox.this.round(MathUtils.map(Progress, 0, MaxProgress, outMin, outMax));
					SelectionBox.this.law = SelectionBox.this.round(MathUtils.map(Progress, 0, MaxProgress, SelectionBox.this.law(currentIndex), SelectionBox.this.law(index)));
					SelectionBox.this.raw = SelectionBox.this.round(MathUtils.map(Progress, 0, MaxProgress, SelectionBox.this.raw(currentIndex), SelectionBox.this.raw(index)));
					this.Run = false;

					if(IsMax) {
						SelectionBox.this.offset = 0;
						SelectionBox.this.law = 0;
						SelectionBox.this.raw = 0;
						SelectionBox.this.SelectedIndex = index;

						for(SelectionBoxListener Listener : SelectionBox.this.listenerList.getListeners(SelectionBoxListener.class)) {
							Listener.selectionBoxSelected(this.selectionBoxEvent());
						}

						this.Run = true;
					}

					SelectionBox.this.repaint();
				});
			}
		}

		public SelectionBoxEvent selectionBoxEvent() {
			return new SelectionBoxEvent(SelectionBox.this, SelectionBox.this.getMousePosition(), SelectionBox.this.isEnabled(), SelectionBox.this.SelectedIndex, SelectionBox.this.Items.get(SelectionBox.this.SelectedIndex));
		}
	}
}
