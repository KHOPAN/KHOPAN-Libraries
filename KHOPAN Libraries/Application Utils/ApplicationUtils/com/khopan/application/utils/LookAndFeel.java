package com.khopan.application.utils;

import javax.swing.UIManager;

public class LookAndFeel {
	private LookAndFeel() {}

	public static final String SYSTEM = UIManager.getSystemLookAndFeelClassName();
	public static final String WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public static final String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
}
