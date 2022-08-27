package com.khopan.item.comboBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

import com.khopan.math.MathUtils;
import com.khopan.theme.Colorstate;
import com.khopan.theme.Theme;
import com.khopan.theme.ThemeConstants;
import com.khopan.theme.ThemeFactory;
import com.khopan.theme.ThemeTimer;
import com.khopan.theme.Themeable;

public class ComboBox<Type> extends JComboBox<Type> implements Serializable, Themeable {
	private static final long serialVersionUID = -8983033601172163427L;

	private static final Theme DEFAULT_THEME = ThemeFactory.LIGHT_THEME;
	private static final boolean DEFAULT_ENABLE_ANIMATION = true;
	private static final int DEFAULT_ANIMATION_DELAY = 100;

	private int ArrowSize;
	private Colorstate Color;
	private Theme Theme;
	private boolean EnableAnimation;
	private int AnimationDelay;
	private int Progress;

	private final Listener Listener;
	private final ComboBoxUI ComboBoxUI;
	private final Renderer Renderer;

	public ComboBox() {
		this(DEFAULT_THEME);
	}

	public ComboBox(Theme Theme) {
		this.ArrowSize = 25;
		this.Listener = new Listener();
		this.ComboBoxUI = new ComboBoxUI();
		this.Renderer = new Renderer();
		this.setTheme(Theme);
		this.Color = this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
		this.EnableAnimation = DEFAULT_ENABLE_ANIMATION;
		this.AnimationDelay = DEFAULT_ANIMATION_DELAY;
		this.setFocusable(false);
		this.setUI(this.ComboBoxUI);
		this.setRenderer(this.Renderer);
		this.addMouseListener(this.Listener);
		this.addItemListener(this.Listener);
	}

	public void addComboBoxListeners(ComboBoxListener Listener) {
		this.listenerList.add(ComboBoxListener.class, Listener);
	}

	public void addComboBoxListeners(ComboBoxListener... Listeners) {
		for(ComboBoxListener Listener : Listeners) {
			this.listenerList.add(ComboBoxListener.class, Listener);
		}
	}

	public ComboBoxListener[] getComboBoxListeners() {
		return this.listenerList.getListeners(ComboBoxListener.class);
	}

	public void removeComboBoxListener(ComboBoxListener Listener) {
		this.listenerList.remove(ComboBoxListener.class, Listener);
	}

	public void setEnableAnimation(boolean EnableAnimation) {
		this.EnableAnimation = EnableAnimation;
	}

	public boolean isEnableAnimation() {
		return this.EnableAnimation;
	}

	public void setAnimationDelay(int AnimationDelay) {
		this.AnimationDelay = AnimationDelay;
	}

	public int getAnimationDelay() {
		return this.AnimationDelay;
	}

	public int getArrowSize() {
		return this.ArrowSize;
	}

	public void setArrowSize(int ArrowSize) {
		this.ArrowSize = ArrowSize;
	}

	@SuppressWarnings("unchecked")
	public void addItems(Type... Items) {
		for(Type Item : Items) {
			this.addItem(Item);
		}
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
		this.hidePopup();
		this.Listener.disable();
	}

	private Graphics2D Graphics2D;
	private FontMetrics Metrics;
	private String Text;

	@Override
	public void paint(Graphics Graphics) {
		Graphics2D = (Graphics2D) Graphics;
		Graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D.setColor(this.Color.getBorder());
		Graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
		Graphics2D.setColor(this.Color.getBackground());
		Graphics2D.fillRect(this.Color.getBorderThickness(), this.Color.getBorderThickness(), this.getWidth() - this.Color.getBorderThickness() * 2, this.getHeight() - this.Color.getBorderThickness() * 2);
		Graphics2D.setColor(this.Color.getBorder());
		Graphics2D.fillRect(this.getWidth() - this.ArrowSize, this.Color.getBorderThickness(), this.Color.getBorderThickness(), this.getHeight() - this.Color.getBorderThickness() * 2);

		Metrics = this.getFontMetrics(this.getFont());
		Text = this.getSelectedItem().toString();

		Graphics2D.setColor(this.Color.getForeground());
		Graphics2D.setFont(this.getFont());
		Graphics2D.drawString(Text, 10, this.getHeight() / 2 + Metrics.getHeight() / 4);

		int quarter = this.ArrowSize / 4;
		int cx = this.getWidth() - this.ArrowSize + this.Color.getBorderThickness() + (int) Math.floor((this.ArrowSize - this.Color.getBorderThickness() * 2) / 2d);
		int cy = (int) Math.floor((this.getHeight() - this.Color.getBorderThickness() * 2) / 2d) + this.Color.getBorderThickness();
		int f = quarter / 4;

		Graphics2D.drawPolygon(new int[] {cx - quarter, cx + quarter, cx}, new int[] {cy - quarter + f, cy - quarter + f, cy + quarter / 2 + f}, 3);
	}

	private class Listener extends MouseAdapter implements ItemListener, AdjustmentListener {
		public final ThemeTimer Timer;

		public Listener() {
			this.Timer = new ThemeTimer();
		}

		@Override
		public void mouseEntered(MouseEvent Event) {
			for(ComboBoxListener Listener : ComboBox.this.listenerList.getListeners(ComboBoxListener.class)) {
				Listener.comboBoxEntered(ComboBox.this.comboBoxEvent());
			}

			if(ComboBox.this.isEnabled()) {
				this.changeColor(ComboBox.this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class));
			}
		}

		@Override
		public void mousePressed(MouseEvent Event) {
			for(ComboBoxListener Listener : ComboBox.this.listenerList.getListeners(ComboBoxListener.class)) {
				Listener.comboBoxPressed(ComboBox.this.comboBoxEvent());
			}

			if(ComboBox.this.isEnabled()) {
				this.changeColor(ComboBox.this.Theme.get(ThemeConstants.PRESSED_STATE, Colorstate.class));
			}
		}

		@Override
		public void mouseReleased(MouseEvent Event) {
			for(ComboBoxListener Listener : ComboBox.this.listenerList.getListeners(ComboBoxListener.class)) {
				Listener.comboBoxReleased(ComboBox.this.comboBoxEvent());
			}

			if(ComboBox.this.isEnabled()) {
				this.changeColor(ComboBox.this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class));
			}
		}

		@Override
		public void mouseExited(MouseEvent Event) {
			for(ComboBoxListener Listener : ComboBox.this.listenerList.getListeners(ComboBoxListener.class)) {
				Listener.comboBoxExited(ComboBox.this.comboBoxEvent());
			}

			if(ComboBox.this.isEnabled()) {
				this.changeColor(ComboBox.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class));
			}
		}

		public void enable() {
			if(ComboBox.this.EnableAnimation) {
				this.animation(this.getCurrentColor());
			} else {
				ComboBox.this.Color = this.getCurrentColor();
			}
		}

		public void disable() {
			this.changeColor(ThemeConstants.DISABLED_STATE);
		}

		private Colorstate getCurrentColor() {
			return this.isMouseEntered() ? ComboBox.this.Theme.get(ThemeConstants.ENTERED_STATE, Colorstate.class) : ComboBox.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
		}

		private boolean isMouseEntered() {
			return ComboBox.this.getMousePosition() != null;
		}

		private void changeColor(String Key) {
			this.changeColor(ComboBox.this.Theme.get(Key, Colorstate.class));
		}

		private void changeColor(Colorstate Color) {
			if(ComboBox.this.EnableAnimation) {
				this.animation(Color);
			} else {
				ComboBox.this.Color = Color;
			}
		}

		private void animation(Colorstate To) {
			Colorstate ComboBoxState = ComboBox.this.Color;
			this.Timer.progress(ComboBox.this.AnimationDelay, (Progress, MaxProgress, IsMax) -> {
				ComboBox.this.Color = ThemeTimer.mapColorstate(ComboBoxState, To, Progress, MaxProgress);
				ComboBox.this.repaint();
			});
		}

		@Override
		public void itemStateChanged(ItemEvent Event) {
			for(ComboBoxListener Listener : ComboBox.this.listenerList.getListeners(ComboBoxListener.class)) {
				Listener.comboBoxSelected(ComboBox.this.comboBoxEvent());
			}
		}

		@Override
		public void adjustmentValueChanged(AdjustmentEvent Event) {
			for(ComboBoxListener Listener : ComboBox.this.listenerList.getListeners(ComboBoxListener.class)) {
				Listener.comboBoxScrolled(ComboBox.this.comboBoxEvent());
			}

			ComboBox.this.Progress = (int) MathUtils.map(Event.getAdjustable().getValue(), Event.getAdjustable().getMinimum(), Event.getAdjustable().getMaximum(), 0, 100);
		}
	}

	private class Renderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 7906729509894492903L;

		public Renderer() {

		}

		@Override
		public void paint(Graphics Graphics) {
			Colorstate Normal = ComboBox.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
			this.setBackground(Normal.getBackground());
			this.setForeground(Normal.getForeground());
			super.paint(Graphics);
		}

		private Color Background;

		@Override
		public Component getListCellRendererComponent(JList<?> List, Object Value, int Index, boolean IsSelected, boolean HasFocus) {
			this.Background = IsSelected ? ComboBox.this.Theme.get(ThemeConstants.DISABLED_STATE, Colorstate.class).getBackground() : ComboBox.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class).getBackground();

			return super.getListCellRendererComponent(List, Value, Index, IsSelected, HasFocus);
		}

		@Override
		public Color getBackground() {
			return this.Background;
		}
	}

	private class ComboBoxUI extends BasicComboBoxUI {
		public ComboBoxUI() {

		}

		private BasicComboPopup Popup;
		private JScrollPane Pane;

		@SuppressWarnings("unchecked")
		@Override
		protected ComboPopup createPopup() {
			Colorstate Normal = ComboBox.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);

			this.Popup = new BasicComboPopup((JComboBox<Object>) ComboBox.this);
			this.Popup.setBorder(new LineBorder(Normal.getBorder(), Normal.getBorderThickness()));

			this.Pane = (JScrollPane) this.Popup.getComponent(0);
			this.Pane.getVerticalScrollBar().setUI(new ScrollBarUI());
			this.Pane.getVerticalScrollBar().addAdjustmentListener(ComboBox.this.Listener);

			return this.Popup;
		}

		private JButton Arrow;

		@Override
		protected JButton createArrowButton() {
			this.Arrow = super.createArrowButton();
			this.Arrow.addMouseListener(ComboBox.this.Listener);

			return this.Arrow;
		}
	}

	private class ScrollBarUI extends BasicScrollBarUI {
		@Override
		protected void configureScrollBarColors() {
			this.thumbColor = new Color(0x4D4D4D);
			this.trackColor = ComboBox.this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class).getBackground().darker();
		}

		private JButton IncreaseButton;
		private JButton DecreaseButton;

		@Override
		protected JButton createIncreaseButton(int Orientation) {
			this.IncreaseButton = super.createIncreaseButton(Orientation);
			this.IncreaseButton.addMouseListener(new ButtonAnimation(IncreaseButton));

			return this.IncreaseButton;
		}

		@Override
		protected JButton createDecreaseButton(int Orientation) {
			this.DecreaseButton = super.createIncreaseButton(Orientation);
			this.DecreaseButton.addMouseListener(new ButtonAnimation(DecreaseButton));

			return this.DecreaseButton;
		}
	}

	private class ButtonAnimation extends MouseAdapter {
		public final ThemeTimer Timer;
		public final JButton Button;
		public final Theme Theme;

		public Colorstate Color;

		public ButtonAnimation(JButton Button) {
			this.Timer = new ThemeTimer();
			this.Button = Button;
			this.Theme = ComboBox.this.Theme;
			this.Color = this.dark(this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class));
			this.update();
		}

		@Override
		public void mouseEntered(MouseEvent Event) {
			if(ComboBox.this.isEnabled()) {
				this.changeColor(ThemeConstants.ENTERED_STATE);
			}
		}

		@Override
		public void mousePressed(MouseEvent Event) {
			if(ComboBox.this.isEnabled()) {
				this.changeColor(ThemeConstants.PRESSED_STATE);
			}
		}

		@Override
		public void mouseReleased(MouseEvent Event) {
			if(ComboBox.this.isEnabled()) {
				this.changeColor(ThemeConstants.ENTERED_STATE);
			}
		}

		@Override
		public void mouseExited(MouseEvent Event) {
			if(ComboBox.this.isEnabled()) {
				this.changeColor(ThemeConstants.NORMAL_STATE);
			}
		}

		private void changeColor(String Key) {
			this.changeColor(this.Theme.get(Key, Colorstate.class));
		}

		private void changeColor(Colorstate Color) {
			if(ComboBox.this.EnableAnimation) {
				this.animation(Color);
			} else {
				ComboBox.this.Color = Color;
			}
		}

		private void animation(Colorstate To) {
			Colorstate ButtonState = this.Color;
			this.Timer.progress(ComboBox.this.AnimationDelay, (Progress, MaxProgress, IsMax) -> {
				this.Color = ThemeTimer.mapColorstate(ButtonState, this.dark(To), Progress, MaxProgress);
				this.update();
			});
		}

		private Colorstate dark(Colorstate Color) {
			return new Colorstate(Color.getForeground().darker(), Color.getBackground().darker(), Color.getBorder().darker(), Color.getBorderThickness());
		}

		private void update() {
			this.Button.setForeground(this.Color.getForeground());
			this.Button.setBackground(this.Color.getBackground());
			this.Button.setBorder(new LineBorder(this.Color.getBorder()));
		}
	}

	@Override
	public void setTheme(Theme Theme) {
		this.Theme = Theme;
		this.Color = this.Theme.get(ThemeConstants.NORMAL_STATE, Colorstate.class);
	}

	@Override
	public Theme getTheme() {
		return this.Theme;
	}

	private ComboBoxEvent comboBoxEvent() {
		return new ComboBoxEvent(this, this.getSelectedItem(), this.getMousePosition(), this.Progress);
	}
}
