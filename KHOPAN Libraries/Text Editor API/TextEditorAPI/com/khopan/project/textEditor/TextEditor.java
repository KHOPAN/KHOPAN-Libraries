package com.khopan.project.textEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import com.khopan.application.utils.DesktopApplication;
import com.khopan.application.utils.Logo;
import com.khopan.application.utils.LookAndFeel;
import com.khopan.application.utils.data.ApplicationDataInitializer;
import com.khopan.application.utils.menuBar.ItemType;
import com.khopan.application.utils.menuBar.Menu;
import com.khopan.application.utils.menuBar.MenuBar;
import com.khopan.application.utils.menuBar.MenuBar.MenuBarBorder;
import com.khopan.application.utils.menuBar.MenuConfig;
import com.khopan.application.utils.menuBar.MenuItem;
import com.khopan.file.ExceptionHandler;
import com.khopan.list.ItemMap;
import com.khopan.math.MathUtils;
import com.khopan.project.textEditor.plugin.EventListener;
import com.khopan.project.textEditor.plugin.PluginHandler;

public class TextEditor extends DesktopApplication {
	public static final String TEXT_EDITOR_NAME = "Text Editor";
	public static final String TEXT_EDITOR_VERSION = "1.0.0";
	public static final String LOGO_TEXT = "Tx";
	public static final String AUTHOR_NAME = "KHOPAN";
	public static final String TEXT_EDITOR_VERSION_TEXT = TextEditor.TEXT_EDITOR_NAME + " " + TextEditor.TEXT_EDITOR_VERSION;
	public static final String AUTHOR_NAME_TEXT = "By " + TextEditor.AUTHOR_NAME;
	public static final String LOCAL_APP_DATA = System.getenv("LOCALAPPDATA");
	public static final String WORKING_DIRECTORY = TextEditor.LOCAL_APP_DATA + File.separator + TextEditor.TEXT_EDITOR_NAME;
	public static final File DATA_FILE = new File(TextEditor.WORKING_DIRECTORY + File.separator + "data.ted");
	public static final File PLUGIN_FOLDER = new File(TextEditor.WORKING_DIRECTORY + File.separator + "plugins");
	public static final File RUNTIME_FOLDER = new File(TextEditor.WORKING_DIRECTORY + File.separator + "runtime");

	public static final Font FONT = new Font("Consolas", Font.BOLD, 10);
	public static final Theme DEFAULT_THEME = Theme.LIGHT;

	public final ItemMap<String, Object> Properties = new ItemMap<>();

	public JFrame Frame;
	public SettingsDialog Dialog;

	public MenuConfig Config;
	public MenuBar MenuBar;

	public Menu FileMenu;
	public MenuItem NewItem;
	public MenuItem OpenItem;
	public MenuItem SaveItem;
	public MenuItem SaveAsItem;

	public Menu EditMenu;
	public MenuItem SettingsMenuItem;
	public Menu ThemeMenu;
	public MenuItem LightItem;
	public MenuItem DarkItem;

	public Menu HelpMenu;
	public MenuItem PluginManagerItem;

	public Theme WindowTheme;
	public BufferedImage Image;
	public BufferedImage LogoImage;
	public int LoadingCount;

	public PluginHandler PluginHandler;
	public PluginManager PluginManager;
	public EventListener eventListener;

	public TextEditor() {
		super();
		this.eventListener.eventRun("loadingDone");
	}

	@Override
	public void beginLoading() throws Throwable {
		this.LoadingCount = 19;
		this.WindowTheme = TextEditor.DEFAULT_THEME;

		this.LogoImage = this.getIcon(new Dimension(200, 200));
		this.progress("Initialized Loading Image");

		this.progress("Initializing Event Listener");
		this.eventListener = new EventListener();

		this.progress("Registering Events");
		this.eventListener.registerEvent("logoImageLoaded");
		this.eventListener.registerEvent("pluginManagerLoaded");
		this.eventListener.registerEvent("pluginHandlerLoaded");
		this.eventListener.registerEvent("shutdownHookAdded");
		this.eventListener.registerEvent("dataFileLoaded");
		this.eventListener.registerEvent("themeLoaded");
		/*this.eventListener.registerEvent("settingsItemLoaded");
		this.eventListener.registerEvent("editMenuLoaded");
		this.eventListener.registerEvent("saveAsItemLoaded");
		this.eventListener.registerEvent("saveItemLoaded");
		this.eventListener.registerEvent("openItemLoaded");
		this.eventListener.registerEvent("newItemLoaded");
		this.eventListener.registerEvent("fileMenuLoaded");*/
		this.eventListener.registerEvent("menuLoaded");
		this.eventListener.registerEvent("menuBarLoaded");
		this.eventListener.registerEvent("menuBarItemAdded");
		this.eventListener.registerEvent("configObjectGenerated");
		this.eventListener.registerEvent("fontLoaded");
		this.eventListener.registerEvent("backgroundLoaded");
		this.eventListener.registerEvent("foregroundLoaded");
		this.eventListener.registerEvent("borderLoaded");
		this.eventListener.registerEvent("menuBarBorderLoaded");
		this.eventListener.registerEvent("buildingFinished");
		this.eventListener.registerEvent("frameInitialized");
		this.eventListener.registerEvent("loadingDone");
		this.eventListener.eventRun("logoImageLoaded");

		this.progress("Initializing Plugin Manager");
		this.PluginManager = new PluginManager(this);
		this.eventListener.eventRun("pluginManagerLoaded");

		this.progress("Initializing Plugin Handler");
		this.PluginHandler = new PluginHandler(this);
		this.eventListener.eventRun("pluginHandlerLoaded");

		this.progress("Adding Shutdown Hook to the Runtime");
		Runtime.getRuntime().addShutdownHook(new ShutdownHandler(this));
		this.eventListener.eventRun("shutdownHookAdded");

		this.progress("Getting Data File");
		this.getData();
		this.eventListener.eventRun("dataFileLoaded");

		this.progress("Loading Theme From Data File");
		this.loadTheme();
		this.eventListener.eventRun("themeLoaded");

		this.progress("Loading Menu");
		this.PluginManagerItem = new MenuItem("Plugin Manager", ItemType.NORMAL, Event -> this.pluginManager());
		this.HelpMenu = new Menu("Help", this.PluginManagerItem);
		this.SettingsMenuItem = new MenuItem("Settings", ItemType.NORMAL, Event -> this.settings());
		this.EditMenu = new Menu("Edit", this.SettingsMenuItem);
		this.SaveAsItem = new MenuItem("Save As", ItemType.NORMAL, Event -> this.saveAsFile());
		this.SaveItem = new MenuItem("Save", ItemType.NORMAL, Event -> this.saveFile());
		this.OpenItem = new MenuItem("Open", ItemType.NORMAL, Event -> this.openFile());
		this.NewItem = new MenuItem("New", ItemType.NORMAL, Event -> this.newFile());
		this.FileMenu = new Menu("File", this.NewItem, this.OpenItem, this.SaveItem, this.SaveAsItem);
		this.eventListener.eventRun("menuLoaded");

		this.progress("Initializing Menu Bar");
		this.MenuBar = new MenuBar();
		this.eventListener.eventRun("menuBarLoaded");

		this.progress("Adding Menus to the Menu Bar");
		this.MenuBar.addMenus(this.FileMenu, this.EditMenu, this.HelpMenu);
		this.eventListener.eventRun("menuBarItemAdded");

		this.progress("Generating Config Object");
		this.Config = this.MenuBar.config();
		this.eventListener.eventRun("configObjectGenerated");

		this.progress("Initializing Font");
		this.Config.Font = TextEditor.FONT;
		this.eventListener.eventRun("fontLoaded");

		this.progress("Initializing Background");
		this.Config.Background = this.background(this.WindowTheme);
		this.eventListener.eventRun("backgroundLoaded");

		this.progress("Initializing Foreground");
		this.Config.Foreground = this.foreground(this.WindowTheme);
		this.eventListener.eventRun("foregroundLoaded");

		this.progress("Initializing Borders");
		this.Config.Border = new LineBorder(this.barBackground(this.WindowTheme), 1);
		this.eventListener.eventRun("borderLoaded");

		this.progress("Initializing Menu Bar Border");
		this.Config.MenuBarBorder = new MenuBarBorder(this.barBackground(this.WindowTheme), 2);
		this.eventListener.eventRun("menuBarBorderLoaded");

		this.progress("Building");
		this.MenuBar.build();
		this.eventListener.eventRun("buildingFinished");

		this.progress("Loading Done");
		this.loadingDone();
	}

	@Override
	public void configFrame(JFrame Frame) {
		this.Frame = Frame;
		this.Frame.setJMenuBar(this.MenuBar.get());
		this.Frame.getContentPane().setBackground(this.background(this.WindowTheme));

		this.Dialog = new SettingsDialog(this);
		this.eventListener.eventRun("frameInitialized");
	}

	@Override
	public BufferedImage getIcon(Dimension size) {
		return Logo.createRoundedNoBorderIcon(TextEditor.LOGO_TEXT, new Color(0x8A6642), Logo.BACKGROUND_AUTO_GENERATED, size);
	}

	@Override
	public Rectangle getScreenBounds() {
		return DesktopApplication.center(600, 400);
	}

	@Override
	public Rectangle getLoadingScreenBounds() {
		return DesktopApplication.center(600, 400);
	}

	@Override
	public void paintLoadingScreen(Graphics2D Graphics2D, Dimension size) {
		Graphics2D.drawImage(this.Image, 0, 0, size.width, size.height, null);
	}

	@Override
	public void configLoadingFrame(JFrame Frame) {
		Frame.setUndecorated(true);
	}

	@Override
	public String getLookAndFeelClassName() {
		return LookAndFeel.WINDOWS;
	}

	public int Count;

	public void progress(String Information) {
		this.Count++;
		this.Image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics2D Graphics2D = (Graphics2D) this.Image.getGraphics();
		int ProgressBarBorderWeight = 1;
		int ProgressBarArcSize = 15;

		Graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Graphics2D.setColor(this.background(this.WindowTheme));
		Graphics2D.fillRect(0, 0, 600, 400);
		Graphics2D.setColor(this.foreground(this.WindowTheme));
		Graphics2D.setFont(new Font("Calibri", Font.BOLD, 26));
		FontMetrics Metrics = Graphics2D.getFontMetrics();
		Graphics2D.drawString(TextEditor.TEXT_EDITOR_VERSION_TEXT, (300 - Metrics.stringWidth(TextEditor.TEXT_EDITOR_VERSION_TEXT)) / 2, 30 + Metrics.getHeight() / 2);
		Graphics2D.setFont(new Font("Calibri", Font.BOLD, 15));
		Metrics = Graphics2D.getFontMetrics();
		Graphics2D.drawString(TextEditor.AUTHOR_NAME_TEXT, (300 - Metrics.stringWidth(TextEditor.AUTHOR_NAME_TEXT)) / 2, 55 + Metrics.getHeight() / 2);
		Graphics2D.drawImage(this.LogoImage, 50, 90, null);
		Graphics2D.drawString(Information, 30, 330);
		String Progress = ((int) MathUtils.map(this.Count, 0, this.LoadingCount, 0, 100)) + "%";
		Graphics2D.drawString(Progress, 570 - Metrics.stringWidth(Progress), 330);
		Graphics2D.setColor(new Color(0xAAAAAA));
		Graphics2D.fillRoundRect(30, 350, 540, 25, ProgressBarArcSize, ProgressBarArcSize);
		Graphics2D.setColor(new Color(0x00AA00));
		Graphics2D.fillRoundRect(30 + ProgressBarBorderWeight, 350 + ProgressBarBorderWeight, (int) MathUtils.map(this.Count, 0, this.LoadingCount, 0, 540 - ProgressBarBorderWeight * 2), 25 - ProgressBarBorderWeight * 2, ProgressBarArcSize - ProgressBarArcSize / 3, ProgressBarArcSize - ProgressBarArcSize / 3);
		Graphics2D.dispose();
	}

	public void setTheme(Theme Theme) {
		if(!this.WindowTheme.equals(Theme)) {
			this.WindowTheme = Theme;
			this.Config.Background = this.background(this.WindowTheme);
			this.Config.Foreground = this.foreground(this.WindowTheme);
			this.Config.Border = new LineBorder(this.barBackground(this.WindowTheme), 1);
			this.Config.MenuBarBorder = new MenuBarBorder(this.barBackground(this.WindowTheme), 2);
			this.MenuBar.build();
			this.Frame.getContentPane().setBackground(this.background(this.WindowTheme));
			this.Frame.setJMenuBar(this.MenuBar.get());
		}
	}

	public void settings() {
		this.Dialog.show();
	}

	public void saveAsFile() {

	}

	public void saveFile() {

	}

	public void openFile() {

	}

	public void newFile() {

	}

	public void pluginManager() {

	}

	public void getData() {
		ApplicationDataInitializer.createDataFile(TextEditor.DATA_FILE, ExceptionHandler.DEFAULT_HANDLER);
	}

	public void loadTheme() {
		Theme LoadedTheme = ApplicationDataInitializer.getData(TextEditor.DATA_FILE, "theme", Theme.class, ExceptionHandler.DEFAULT_HANDLER);

		if(LoadedTheme != null) {
			this.WindowTheme = LoadedTheme;
		} else {
			this.WindowTheme = Theme.LIGHT;
		}
	}

	public Color foreground(Theme Theme) {
		switch(Theme) {
		case LIGHT:
			return new Color(0x000000);
		case DARK:
			return new Color(0xFFFFFF);
		default:
			return null;
		}
	}

	public Color foreground() {
		return this.foreground(this.WindowTheme);
	}

	public Color background(Theme Theme) {
		switch(Theme) {
		case LIGHT:
			return new Color(0xFFFFFF);
		case DARK:
			return new Color(0x1E1E1E);
		default:
			return null;
		}
	}

	public Color background() {
		return this.background(this.WindowTheme);
	}

	public Color barBackground(Theme Theme) {
		switch(Theme) {
		case LIGHT:
			return new Color(0xB3B3B3);
		case DARK:
			return new Color(0x2B2B2B);
		default:
			return null;
		}
	}

	public Color barBackground() {
		return this.barBackground(this.WindowTheme);
	}

	public void addProperty(String Name, Object Value) {
		if(this.Properties.has(Name)) {
			throw new IllegalArgumentException("Duplicated property name '" + Name + "'");
		} else {
			this.Properties.put(Name, Value);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(String Name, Class<T> Type) {
		if(!this.Properties.has(Name)) {
			throw new IllegalArgumentException("Property '" + Name + "' does not exist");
		} else {
			return (T) this.Properties.get(Name);
		}
	}

	public static enum Theme implements Serializable {
		LIGHT,
		DARK;
	}

	public static void main(String[] args) {
		DesktopApplication.init();
	}

	public static void listOfPackage(String DirectoryName, Set<String> PackageList) {
		File Directory = new File(DirectoryName);
		File[] FileList = Directory.listFiles();

		for(File File : FileList) {
			if(File.isFile()) {
				String Path = File.getPath();
				String PackageName = Path.substring(Path.indexOf("src") + 4, Path.lastIndexOf('\\'));
				PackageList.add(PackageName.replace('\\', '.'));
			} else if(File.isDirectory()) {
				TextEditor.listOfPackage(File.getAbsolutePath(), PackageList);
			}
		}
	}
}
