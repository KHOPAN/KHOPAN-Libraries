package com.khopan.application.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public abstract class DesktopApplication {
	public static final String DEFAULT_TITLE = "Desktop Application";
	public static final Dimension DEFAULT_ICON_SIZE = new Dimension(64, 64);
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

	protected final BufferedImage Icon;

	private boolean CanCallLoadingDoneMethod;
	private Runnable LoadingDoneCallback;
	private long LoadingTime;
	private long Time;
	private ScheduledFuture<?> Future;
	private BufferedImage Image;

	protected String[] args;

	public DesktopApplication() {
		String LookAndFeelClassName = this.getLookAndFeelClassName();

		if(LookAndFeelClassName == null) {
			LookAndFeelClassName = LookAndFeel.WINDOWS;
		}

		try {
			UIManager.setLookAndFeel(LookAndFeelClassName);
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}

		this.Icon = this.getIconImage(DesktopApplication.DEFAULT_ICON_SIZE);
		this.LoadingTime = -1L;

		boolean BeginLoading = false;

		try {
			Rectangle LoadingScreenBounds = this.getLoadingScreenBounds();
			Rectangle ScreenBounds = Objects.requireNonNull(this.getScreenBounds(), "The method 'DesktopApplication.getScreenBounds();' cannot return a null value.");

			if(LoadingScreenBounds != null) {
				JFrame Frame = new JFrame();
				Frame.setTitle(DesktopApplication.DEFAULT_TITLE);
				Frame.setIconImage(this.Icon);
				Frame.setBounds(LoadingScreenBounds);
				Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				this.configLoadingFrame(Frame);
				JPanel Panel = new JPanel() {
					private static final long serialVersionUID = -2957017652832695030L;

					@Override
					protected void paintComponent(Graphics Graphics) {
						Graphics.drawImage(DesktopApplication.this.Image, 0, 0, this.getWidth(), this.getHeight(), null);
					}
				};

				Frame.setLayout(new BorderLayout());
				Frame.add(Panel, BorderLayout.CENTER);

				this.Future = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
					this.Image = new BufferedImage(Frame.getWidth(), Frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics2D Graphics2D = (Graphics2D) this.Image.getGraphics();

					Graphics2D.setColor(new Color(0x000000));
					Graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					this.paintLoadingScreen(Graphics2D, Frame.getSize());
					Graphics2D.dispose();
					Panel.repaint();
				}, 0, 1000 / 60, TimeUnit.MILLISECONDS);

				this.Time = 0L;
				this.LoadingDoneCallback = () -> {
					this.LoadingTime = System.currentTimeMillis() - this.Time;
					this.Future.cancel(false);
					Frame.dispose();
					JFrame NewFrame = new JFrame();
					NewFrame.setTitle(DesktopApplication.DEFAULT_TITLE);
					NewFrame.setIconImage(this.Icon);
					NewFrame.setBounds(ScreenBounds);
					NewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					this.configFrame(NewFrame);
					NewFrame.setVisible(true);
					this.CanCallLoadingDoneMethod = false;
				};

				Frame.setVisible(true);
				BeginLoading = true;
			} else {
				this.Time = 0L;
				this.LoadingDoneCallback = () -> {
					this.LoadingTime = System.currentTimeMillis() - this.Time;
					JFrame Frame = new JFrame();
					Frame.setTitle(DesktopApplication.DEFAULT_TITLE);
					Frame.setIconImage(this.Icon);
					Frame.setBounds(ScreenBounds);
					Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					this.configFrame(Frame);
					Frame.setVisible(true);
					this.CanCallLoadingDoneMethod = false;
				};

				BeginLoading = true;
			}

			this.CanCallLoadingDoneMethod = true;

			if(BeginLoading) {
				this.Time = System.currentTimeMillis();
				this.beginLoading();
			}
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	public abstract void beginLoading() throws Throwable;
	public abstract void configFrame(JFrame Frame);
	public abstract BufferedImage getIcon(Dimension size);
	public abstract Rectangle getScreenBounds();
	public abstract Rectangle getLoadingScreenBounds();
	public abstract void paintLoadingScreen(Graphics2D Graphics2D, Dimension size);
	public abstract void configLoadingFrame(JFrame Frame);
	public abstract String getLookAndFeelClassName();

	public final void loadingDone() {
		if(this.CanCallLoadingDoneMethod) {
			this.LoadingDoneCallback.run();
		}
	}

	public long getLoadingTime() {
		return this.LoadingTime;
	}

	public static void init(String... args) {
		try {
			boolean Found = false;
			String Main = null;

			for(StackTraceElement Element : Thread.currentThread().getStackTrace()) {
				String Name = Element.getClassName();

				if(Found) {
					Main = Name;
					break;
				} else if(Element.getMethodName().equals("init")) {
					Found = true;
				}
			}

			if(Main == null) {
				throw new RuntimeException("Error: unable to determine Desktop Application class.");
			} else {
				Class<?> MainClass = Class.forName(Main, false, Thread.currentThread().getContextClassLoader());
				DesktopApplication Application = (DesktopApplication) MainClass.getDeclaredConstructor().newInstance();
				Application.args = args;
			}
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	public BufferedImage getIconImage(Dimension size) {
		return DesktopApplication.getIconImage(this, size);
	}

	public static BufferedImage getIconImage(DesktopApplication Application, Dimension size) {
		return Objects.requireNonNull(Application.getIcon(size), "Icon image must not be null.");
	}

	public static Rectangle center(int width, int height) {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		return new Rectangle((size.width - width) / 2, (size.height - height) / 2, width, height);
	}

	public static Rectangle center(Dimension size) {
		return DesktopApplication.center(size.width, size.height);
	}

	private class ImageDisplay extends JComponent {
		private static final long serialVersionUID = 6892017539534628650L;

		public BufferedImage Image;

		public ImageDisplay() {

		}

		public Graphics2D Graphics2D;

		@Override
		public void paint(Graphics Graphics) {
			this.Graphics2D = (Graphics2D) Graphics;
			this.Graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			this.Graphics2D.drawImage(this.Image, 0, 0, this.getWidth(), this.getHeight(), null);
			this.Image.flush();
		}
	}
}
