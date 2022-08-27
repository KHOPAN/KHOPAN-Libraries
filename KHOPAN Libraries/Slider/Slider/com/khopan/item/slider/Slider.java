package com.khopan.item.slider;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JComponent;

import com.khopan.math.MathUtils;
import com.khopan.theme.Colorstate;
import com.khopan.theme.Theme;
import com.khopan.theme.ThemeConstants;
import com.khopan.theme.ThemeFactory;
import com.khopan.theme.ThemeTimer;
import com.khopan.theme.Themeable;

@SuppressWarnings("serial")
public class Slider extends JComponent implements Serializable, Themeable {
	private static final Theme DEFAULT_THEME = ThemeFactory.LIGHT_THEME;
	private static final boolean DEFAULT_ENABLE_ANIMATION = true;
	private static final int DEFAULT_ANIMATION_DELAY = 100;
	private static final int DEFAULT_MINIMUM = 0;
	private static final int DEFAULT_MAXIMUM = 100;
	private static final int DEFAULT_VALUE = Slider.DEFAULT_MAXIMUM / 2;
	private static final int DEFAULT_THUMB_WIDTH = 12;
	private static final int DEFAULT_TRACK_HEIGHT = 3;
	private static final boolean DEFAULT_ROUNDED_BORDER = false;

	private final Listener Listener;
	private final BlankListener BlankListener;
	private final Blank Blank;

	private Theme Theme;
	private boolean EnableAnimation;
	private int AnimationDelay;
	private int Minimum;
	private int Maximum;
	private int Value;
	private int ThumbWidth;
	private int TrackHeight;
	private boolean RoundedBorder;

	private Colorstate Color;

	public Slider() {
		this(Slider.DEFAULT_THEME, Slider.DEFAULT_MINIMUM, Slider.DEFAULT_MAXIMUM, Slider.DEFAULT_VALUE);
	}

	public Slider(Theme Theme) {
		this(Theme, Slider.DEFAULT_MINIMUM, Slider.DEFAULT_MAXIMUM, Slider.DEFAULT_VALUE);
	}

	public Slider(int Minimum, int Maximum, int Value) {
		this(Slider.DEFAULT_THEME, Minimum, Maximum, Value);
	}

	public Slider(Theme Theme, int Minimum, int Maximum, int Value) {
		this.Listener = new Listener();
		this.BlankListener = new BlankListener();
		this.Blank = new Blank();
		this.setTheme(Theme);
		this.EnableAnimation = Slider.DEFAULT_ENABLE_ANIMATION;
		this.AnimationDelay = Slider.DEFAULT_ANIMATION_DELAY;
		this.Minimum = Minimum;
		this.Maximum = Maximum;
		this.Value = Value;
		this.ThumbWidth = Slider.DEFAULT_THUMB_WIDTH;
		this.TrackHeight = Slider.DEFAULT_TRACK_HEIGHT;
		this.RoundedBorder = Slider.DEFAULT_ROUNDED_BORDER;
		this.Blank.addMouseListener(this.BlankListener);
		this.Blank.addMouseMotionListener(this.BlankListener);
		this.addMouseListener(this.Listener);
		this.setLayout(null);
		this.add(this.Blank);
	}

	@Override
	public void setTheme(Theme Theme) {
		this.Theme = Theme;
		this.Color = this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
	}

	public boolean isEnableAnimation() {
		return this.EnableAnimation;
	}

	public int getAnimationDelay() {
		return this.AnimationDelay;
	}

	public void setEnableAnimation(boolean EnableAnimation) {
		this.EnableAnimation = EnableAnimation;
	}

	public void setAnimationDelay(int AnimationDelay) {
		this.AnimationDelay = AnimationDelay;
	}

	public void setMinimum(int Minimum) {
		this.Minimum = Minimum;
	}

	public void setMaximum(int Maximum) {
		this.Maximum = Maximum;
	}

	public void setValue(int Value) {
		this.Value = Value;
	}

	public void setThumbWidth(int ThumbWidth) {
		this.ThumbWidth = ThumbWidth;
	}

	public void setTrackHeight(int TrackHeight) {
		this.TrackHeight = TrackHeight;
	}

	public void setRoundedBorder(boolean RoundedBorder) {
		this.RoundedBorder = RoundedBorder;
	}

	@Override
	public Theme getTheme() {
		return this.Theme;
	}

	public int getMinimum() {
		return this.Minimum;
	}

	public int getMaximum() {
		return this.Maximum;
	}

	public int getValue() {
		return this.Value;
	}

	public int getThumbWidth() {
		return this.ThumbWidth;
	}

	public int getTrackHeight() {
		return this.TrackHeight;
	}

	public boolean isRoundedBorder() {
		return this.RoundedBorder;
	}

	public void addSliderListener(SliderListener Listener) {
		this.listenerList.add(SliderListener.class, Listener);
	}

	public void addSliderListenerListeners(SliderListener... Listeners) {
		for(SliderListener Listener : Listeners) {
			this.listenerList.add(SliderListener.class, Listener);
		}
	}

	public SliderListener[] getSliderListeners() {
		return this.listenerList.getListeners(SliderListener.class);
	}

	public void removeSliderListener(SliderListener Listener) {
		this.listenerList.remove(SliderListener.class, Listener);
	}

	private Graphics2D Graphics2D;

	@Override
	public void paint(Graphics Graphics) {
		Graphics2D = (Graphics2D) Graphics;
		Graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int ty = (this.getHeight() - this.TrackHeight) / 2;
		Colorstate ecs = this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class);
		Colorstate ncs = this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
		int bt = ncs.getBorderThickness();

		Graphics2D.setColor(ecs.getBorder());
		this.rectangle(0, ty, this.getWidth(), this.TrackHeight);
		Graphics2D.setColor(ecs.getBackground());
		this.rectangle(bt, ty + bt, this.getWidth() - bt * 2, this.TrackHeight - bt * 2);

		int tx = (int) MathUtils.map(this.Value, this.Minimum, this.Maximum, 0, this.getWidth() - this.ThumbWidth);
		Rectangle Thumb = new Rectangle(tx, 0, this.ThumbWidth, this.getHeight());

		Graphics2D.setColor(this.Color.getBorder());
		this.rectangle(Thumb.x, Thumb.y, Thumb.width, Thumb.height);
		this.setBlankBounds(Thumb);
		Graphics2D.setColor(this.Color.getBackground());
		this.rectangle(tx + this.Color.getBorderThickness(), this.Color.getBorderThickness(), this.ThumbWidth - this.Color.getBorderThickness() * 2, this.getHeight() - this.Color.getBorderThickness() * 2);
	}

	private void rectangle(int x, int y, int width, int height) {
		if(this.RoundedBorder) {
			Graphics2D.fillRoundRect(x, y, width, height, 10, 10);
		} else {
			Graphics2D.fillRect(x, y, width, height);
		}
	}

	private void setBlankBounds(Rectangle Bounds) {
		if(
				this.Blank.getX() != Bounds.x ||
				this.Blank.getY() != Bounds.y ||
				this.Blank.getWidth() != Bounds.width ||
				this.Blank.getHeight() != Bounds.height
				) {
			this.Blank.setBounds(Bounds);
		}
	}

	private class Listener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderEntered(Slider.this.sliderEvent());
			}
		}

		@Override
		public void mousePressed(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderPressed(Slider.this.sliderEvent());
			}

			Slider.this.Value = (int) MathUtils.protectedMap(Event.getX() - Slider.this.ThumbWidth / 2, 0, Slider.this.getWidth() - Slider.this.ThumbWidth, Slider.this.Minimum, Slider.this.Maximum);

			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderSlided(Slider.this.sliderEvent());
			}

			Slider.this.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderReleased(Slider.this.sliderEvent());
			}
		}

		@Override
		public void mouseClicked(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderClicked(Slider.this.sliderEvent());
			}
		}

		@Override
		public void mouseExited(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderExited(Slider.this.sliderEvent());
			}
		}
	}

	private class BlankListener extends MouseAdapter {
		public final ThemeTimer Timer;

		private Point MousePressed;
		private boolean IsPressed;

		public BlankListener() {
			this.Timer = new ThemeTimer();
		}

		@Override
		public void mouseEntered(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderThumbEntered(Slider.this.sliderEvent());
			}

			if(Slider.this.isEnabled() && !this.IsPressed) {
				this.changeColor(ThemeConstants.ENTERED_STATE);
			}
		}

		@Override
		public void mousePressed(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderThumbPressed(Slider.this.sliderEvent());
			}

			if(Slider.this.isEnabled()) {
				this.changeColor(ThemeConstants.PRESSED_STATE);
			}

			this.MousePressed = Event.getPoint();
			this.IsPressed = true;
		}

		@Override
		public void mouseReleased(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderThumbReleased(Slider.this.sliderEvent());
			}

			if(Slider.this.isEnabled()) {
				this.changeColor(this.getCurrentColor());
			}

			this.IsPressed = false;
		}

		@Override
		public void mouseClicked(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderThumbClicked(Slider.this.sliderEvent());
			}
		}

		@Override
		public void mouseExited(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderThumbExited(Slider.this.sliderEvent());
			}

			if(Slider.this.isEnabled() && !this.IsPressed) {
				this.changeColor(ThemeConstants.NORMAL_STATE);
			}
		}

		@Override
		public void mouseDragged(MouseEvent Event) {
			for(SliderListener Listener : Slider.this.listenerList.getListeners(SliderListener.class)) {
				Listener.sliderSlided(Slider.this.sliderEvent());
			}

			Slider.this.Value = (int) MathUtils.protectedMap((Slider.this.Blank.getX() + Event.getX()) - this.MousePressed.x, 0, Slider.this.getWidth() - Slider.this.ThumbWidth, Slider.this.Minimum, Slider.this.Maximum);
			Slider.this.repaint();
		}

		private Colorstate getCurrentColor() {
			return this.isMouseEntered() ? Slider.this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class) : Slider.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
		}

		private boolean isMouseEntered() {
			return Slider.this.Blank.getMousePosition() != null;
		}

		private void changeColor(String Key) {
			this.changeColor(Slider.this.Theme.get(Key, Colorstate.class));
		}

		private void changeColor(Colorstate Color) {
			if(Slider.this.EnableAnimation) {
				this.animation(Color);
			} else {
				Slider.this.Color = Color;
			}
		}

		private void animation(Colorstate To) {
			Colorstate SliderState = Slider.this.Color;
			this.Timer.progress(Slider.this.AnimationDelay, (Progress, MaxProgress, IsMax) -> {
				Slider.this.Color = ThemeTimer.mapColorstate(SliderState, To, Progress, MaxProgress);
				Slider.this.repaint();
			});
		}

		private int limit(int Value, int Minimum, int Maximum) {
			if(Value < Minimum) {
				return Minimum;
			}

			if(Value > Maximum) {
				return Maximum;
			}

			return Value;
		}
	}

	private SliderEvent sliderEvent() {
		return new SliderEvent(this, this.getMousePosition(), this.isEnabled(), this.Value);
	}

	private class Blank extends Component {}
}
