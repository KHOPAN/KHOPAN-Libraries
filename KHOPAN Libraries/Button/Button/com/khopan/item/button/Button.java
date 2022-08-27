package com.khopan.item.button;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.khopan.math.MathUtils;
import com.khopan.theme.Colorstate;
import com.khopan.theme.Theme;
import com.khopan.theme.ThemeConstants;
import com.khopan.theme.ThemeFactory;
import com.khopan.theme.ThemeTimer;
import com.khopan.theme.Themeable;

public class Button extends JComponent implements Themeable {
	private static final long serialVersionUID = 7425203485852594821L;

	private static final Theme DEFAULT_THEME = ThemeFactory.LIGHT_THEME;
	private static final ButtonType DEFAULT_BUTTON_TYPE = ButtonType.PUSH_BUTTON;
	private static final String DEFAULT_TEXT = "Button";
	private static final boolean DEFAULT_ENABLE_ANIMATION = true;
	private static final int DEFAULT_ANIMATION_DELAY = 100;
	private static final boolean DEFALUT_ROUNDED_BORDER = false;

	private Colorstate Color;
	private boolean IsOn;
	private String OnText;
	private String OffText;
	private int SwitchOffset;

	private Theme Theme;
	private ButtonType Type;
	private String Text;
	private boolean EnableAnimation;
	private int AnimationDelay;
	private boolean RoundedBorder;

	private final Listener Listener;

	public Button(ButtonType Type) {
		this(Button.DEFAULT_TEXT, Button.DEFAULT_THEME, Type);
	}

	public Button(Theme Theme) {
		this(Button.DEFAULT_TEXT, Theme, Button.DEFAULT_BUTTON_TYPE);
	}

	public Button(Theme Theme, ButtonType Type) {
		this(Button.DEFAULT_TEXT, Theme, Type);
	}

	public Button(String Text) {
		this(Text, Button.DEFAULT_THEME, Button.DEFAULT_BUTTON_TYPE);
	}

	public Button(String Text, ButtonType Type) {
		this(Text, Button.DEFAULT_THEME, Type);
	}

	public Button(String Text, Theme Theme) {
		this(Text, Theme, Button.DEFAULT_BUTTON_TYPE);
	}

	public Button(String Text, Theme Theme, ButtonType Type) {
		this.Listener = new Listener();

		this.setTheme(Theme);
		this.Type = Type;
		this.Text = Text;
		this.EnableAnimation = Button.DEFAULT_ENABLE_ANIMATION;
		this.AnimationDelay = Button.DEFAULT_ANIMATION_DELAY;
		this.RoundedBorder = Button.DEFALUT_ROUNDED_BORDER;

		this.OnText = this.Text;
		this.OffText = this.Text;
		this.setFont(new JLabel().getFont());
		this.addMouseListener(this.Listener);
	}

	@Override
	public Theme getTheme() {
		return this.Theme;
	}

	public ButtonType getType() {
		return this.Type;
	}

	public String getText() {
		return this.Text;
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
		this.Color = this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
	}

	public void setType(ButtonType Type) {
		this.Type = Type;
	}

	public void setText(String Text) {
		this.Text = Text;
		this.OnText = this.Text;
		this.OffText = this.Text;
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

	public void addButtonListener(ButtonListener Listener) {
		this.listenerList.add(ButtonListener.class, Listener);
	}

	public void addButtonListeners(ButtonListener... Listeners) {
		for(ButtonListener Listener : Listeners) {
			this.listenerList.add(ButtonListener.class, Listener);
		}
	}

	public ButtonListener[] getButtonListeners() {
		return this.listenerList.getListeners(ButtonListener.class);
	}

	public void removeButtonListener(ButtonListener Listener) {
		this.listenerList.remove(ButtonListener.class, Listener);
	}

	public void addToggleButtonListener(ToggleButtonListener Listener) {
		this.listenerList.add(ToggleButtonListener.class, Listener);
	}

	public void addToggleButtonListeners(ToggleButtonListener... Listeners) {
		for(ToggleButtonListener Listener : Listeners) {
			this.listenerList.add(ToggleButtonListener.class, Listener);
		}
	}

	public ToggleButtonListener[] getToggleButtonListeners() {
		return this.listenerList.getListeners(ToggleButtonListener.class);
	}

	public void removeToggleButtonListener(ToggleButtonListener Listener) {
		this.listenerList.remove(ToggleButtonListener.class, Listener);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void enable() {
		super.enable();
		this.Listener.enable();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void disable() {
		super.disable();
		this.Listener.disable();
	}

	public void setLabeledText(String OnText, String OffText) {
		this.OnText = OnText;
		this.OffText = OffText;
	}

	public String getOnText() {
		return this.OnText;
	}

	public String getOffText() {
		return this.OffText;
	}

	public void setOnText(String OnText) {
		this.OnText = OnText;
	}

	public void setOffText(String OffText) {
		this.OffText = OffText;
	}

	private Graphics2D Graphics2D;
	private FontMetrics Metrics;

	@Override
	public void paint(Graphics Graphics) {
		Graphics2D = (Graphics2D) Graphics;
		Graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Metrics = this.getFontMetrics(this.getFont());

		if(this.Type.equals(ButtonType.PUSH_BUTTON) || this.Type.equals(ButtonType.TOGGLE_BUTTON) || this.Type.equals(ButtonType.LABELED_TOGGLE_BUTTON)) {
			Graphics2D.setColor(Color.getBorder());
			this.rectangle(0, 0, this.getWidth(), this.getHeight());
			Graphics2D.setColor(Color.getBackground());
			this.rectangle(this.Color.getBorderThickness(), this.Color.getBorderThickness(), this.getWidth() - this.Color.getBorderThickness() * 2, this.getHeight() - this.Color.getBorderThickness() * 2);

			Graphics2D.setColor(Color.getForeground());

			if(this.Type.equals(ButtonType.LABELED_TOGGLE_BUTTON)) {
				String Text = this.IsOn ? this.OnText : this.OffText;

				Graphics2D.drawString(Text, (this.getWidth() - this.Metrics.stringWidth(Text)) / 2, this.getHeight() / 2 + this.Metrics.getHeight() / 4);
			} else {
				Graphics2D.drawString(this.Text, (this.getWidth() - this.Metrics.stringWidth(this.Text)) / 2, this.getHeight() / 2 + this.Metrics.getHeight() / 4);
			}
		} else if(this.Type.equals(ButtonType.SWITCH) || this.Type.equals(ButtonType.LABELED_SWITCH)) {
			Graphics2D.setColor(this.Color.getBackground());
			this.rectangle(0, 0, this.getWidth(), this.getHeight());

			boolean IsLabeld = this.Type.equals(ButtonType.LABELED_SWITCH);
			String On = IsLabeld ? this.OnText : "ON";
			String Off = IsLabeld ? this.OffText : "OFF";

			Graphics2D.setColor(this.Color.getForeground());
			Graphics2D.drawString(On, (this.getWidth() / 2 - this.Metrics.stringWidth(On)) / 2, this.getHeight() / 2 + this.Metrics.getHeight() / 4);
			Graphics2D.drawString(Off, this.getWidth() / 2 + (this.getWidth() / 2 - this.Metrics.stringWidth(Off)) / 2, this.getHeight() / 2 + this.Metrics.getHeight() / 4);

			Graphics2D.setColor(this.Theme.get(ThemeConstants.DISABLED_STATE, Colorstate.class).getBackground());
			this.rectangle(this.SwitchOffset, 0, this.getWidth() / 2, this.getHeight());
		}
	}

	private void rectangle(int x, int y, int width, int height) {
		if(this.RoundedBorder) {
			this.Graphics2D.fillRoundRect(x, y, width, height, 10, 10);
		} else {
			this.Graphics2D.fillRect(x, y, width, height);
		}
	}

	private class Listener extends MouseAdapter {
		private final ThemeTimer Timer;

		private boolean IsPressing;

		public Listener() {
			this.Timer = new ThemeTimer();
		}

		@Override
		public void mouseEntered(MouseEvent Event) {
			if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
				for(ButtonListener Listener : Button.this.listenerList.getListeners(ButtonListener.class)) {
					Listener.buttonEntered(this.buttonEvent());
				}
			} else {
				for(ToggleButtonListener Listener : Button.this.listenerList.getListeners(ToggleButtonListener.class)) {
					Listener.toggleButtonEntered(this.toggleButtonEvent());
				}
			}

			if(Button.this.isEnabled()) {
				if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
					this.changeColor(ThemeConstants.ENTERED_STATE);
				} else if(Button.this.Type.equals(ButtonType.TOGGLE_BUTTON) || Button.this.Type.equals(ButtonType.LABELED_TOGGLE_BUTTON)) {
					if(!Button.this.IsOn) {
						this.changeColor(ThemeConstants.ENTERED_STATE);
					}
				} else if(Button.this.Type.equals(ButtonType.SWITCH) || Button.this.Type.equals(ButtonType.LABELED_SWITCH)) {
					this.changeColor(ThemeConstants.ENTERED_STATE);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent Event) {
			if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
				for(ButtonListener Listener : Button.this.listenerList.getListeners(ButtonListener.class)) {
					Listener.buttonPressed(this.buttonEvent());
				}
			} else {
				for(ToggleButtonListener Listener : Button.this.listenerList.getListeners(ToggleButtonListener.class)) {
					Listener.toggleButtonPressed(this.toggleButtonEvent());
				}
			}

			this.IsPressing = true;

			if(Button.this.isEnabled()) {
				if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
					this.changeColor(ThemeConstants.PRESSED_STATE);
				} else if(Button.this.Type.equals(ButtonType.TOGGLE_BUTTON) || Button.this.Type.equals(ButtonType.LABELED_TOGGLE_BUTTON)) {
					if(!Button.this.IsOn) {
						this.changeColor(ThemeConstants.PRESSED_STATE);
					}
				} else if(Button.this.Type.equals(ButtonType.SWITCH) || Button.this.Type.equals(ButtonType.LABELED_SWITCH)) {
					this.move();
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent Event) {
			if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
				for(ButtonListener Listener : Button.this.listenerList.getListeners(ButtonListener.class)) {
					Listener.buttonReleased(this.buttonEvent());
				}
			} else {
				for(ToggleButtonListener Listener : Button.this.listenerList.getListeners(ToggleButtonListener.class)) {
					Listener.toggleButtonReleased(this.toggleButtonEvent());
				}
			}

			this.IsPressing = false;

			if(Button.this.isEnabled()) {
				if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
					this.changeColor(this.getCurrentColor());
				} else if(Button.this.Type.equals(ButtonType.TOGGLE_BUTTON) || Button.this.Type.equals(ButtonType.LABELED_TOGGLE_BUTTON)) {
					if(Button.this.IsOn) {
						this.changeColor(this.getCurrentColor());
						Button.this.IsOn = false;

						for(ToggleButtonListener Listener : Button.this.listenerList.getListeners(ToggleButtonListener.class)) {
							Listener.toggleButtonOff(this.toggleButtonEvent());
						}
					} else {
						Button.this.IsOn = true;

						for(ToggleButtonListener Listener : Button.this.listenerList.getListeners(ToggleButtonListener.class)) {
							Listener.toggleButtonOn(this.toggleButtonEvent());
						}
					}

					if(Button.this.Type.equals(ButtonType.LABELED_TOGGLE_BUTTON)) {
						Button.this.repaint();
					}
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent Event) {
			if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
				for(ButtonListener Listener : Button.this.listenerList.getListeners(ButtonListener.class)) {
					Listener.buttonClicked(this.buttonEvent());
				}
			} else {
				for(ToggleButtonListener Listener : Button.this.listenerList.getListeners(ToggleButtonListener.class)) {
					Listener.toggleButtonClicked(this.toggleButtonEvent());
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent Event) {
			if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
				for(ButtonListener Listener : Button.this.listenerList.getListeners(ButtonListener.class)) {
					Listener.buttonExited(this.buttonEvent());
				}
			} else {
				for(ToggleButtonListener Listener : Button.this.listenerList.getListeners(ToggleButtonListener.class)) {
					Listener.toggleButtonExited(this.toggleButtonEvent());
				}
			}

			if(Button.this.isEnabled()) {
				if(Button.this.Type.equals(ButtonType.PUSH_BUTTON)) {
					if(!this.IsPressing) {
						this.changeColor(ThemeConstants.NORMAL_STATE);
					}
				} else if(Button.this.Type.equals(ButtonType.TOGGLE_BUTTON) || Button.this.Type.equals(ButtonType.LABELED_TOGGLE_BUTTON)) {
					if(!this.IsPressing) {
						if(!Button.this.IsOn) {
							this.changeColor(ThemeConstants.NORMAL_STATE);
						}
					}
				}
			}
		}

		public void enable() {
			if(Button.this.EnableAnimation) {
				this.animation(this.getCurrentColor());
			} else {
				Button.this.Color = this.getCurrentColor();
			}
		}

		public void disable() {
			this.changeColor(ThemeConstants.DISABLED_STATE);
		}

		private Colorstate getCurrentColor() {
			return this.isMouseEntered() ? Button.this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class) : Button.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
		}

		private boolean isMouseEntered() {
			return Button.this.getMousePosition() != null;
		}

		private void changeColor(String Key) {
			this.changeColor(Button.this.Theme.get(Key, Colorstate.class));
		}

		private void changeColor(Colorstate Color) {
			if(Button.this.EnableAnimation) {
				this.animation(Color);
			} else {
				Button.this.Color = Color;
			}
		}

		private boolean Right;

		private void move() {
			int Width = Button.this.getWidth() / 2;
			this.Timer.progress(Button.this.AnimationDelay, (Progress, MaxProgress, IsMax) -> {
				Button.this.SwitchOffset = (int) MathUtils.map(Progress, 0, MaxProgress, Right ? 0 : Width, Right ? Width : 0);
				Button.this.repaint();
			});

			this.Right = !this.Right;
		}

		private void animation(Colorstate To) {
			Colorstate ButtonState = Button.this.Color;
			this.Timer.progress(Button.this.AnimationDelay, (Progress, MaxProgress, IsMax) -> {
				Button.this.Color = ThemeTimer.mapColorstate(ButtonState, To, Progress, MaxProgress);
				Button.this.repaint();
			});
		}

		private ButtonEvent buttonEvent() {
			return new ButtonEvent(Button.this, Button.this.getMousePosition(), Button.this.isEnabled());
		}

		private ToggleButtonEvent toggleButtonEvent() {
			return new ToggleButtonEvent(Button.this, Button.this.getMousePosition(), Button.this.isEnabled(), Button.this.IsOn);
		}
	}
}
