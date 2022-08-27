package com.khopan.project.textEditor.plugin;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptEngine;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openjdk.nashorn.api.scripting.NashornScriptEngine;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;
import org.reflections.Reflections;

import com.khopan.file.ExceptionHandler;
import com.khopan.file.FileUtils;
import com.khopan.project.textEditor.Plugin;
import com.khopan.project.textEditor.TextEditor;
import com.khopan.project.textEditor.ZipHandler;
import com.khopan.project.textEditor.plugin.api.IPluginInitializer;

public class PluginHandler {
	public final ClassLoader PluginHandlerLoader;

	public boolean Run;

	public PluginHandler(TextEditor Editor) {
		this.PluginHandlerLoader = this.getClass().getClassLoader();
		PluginProvider.Editor = Editor;

		Set<String> Array = new HashSet<>();
		TextEditor.listOfPackage("src/", Array);

		for(String Package : Array) {
			Reflections Reflections = new Reflections(Package);
			Set<Class<? extends IPluginInitializer>> Classes = Reflections.getSubTypesOf(IPluginInitializer.class);

			for(Class<?> Class : Classes) {
				try {
					IPluginInitializer Initialzer = (IPluginInitializer) Class.getDeclaredConstructor().newInstance();
					Initialzer.init(null);
				} catch(Throwable Errors) {
					Errors.printStackTrace();
				}
			}
		}

		this.createNashornEngine();

		if(!TextEditor.PLUGIN_FOLDER.exists()) {
			TextEditor.PLUGIN_FOLDER.mkdirs();
		}

		File[] Plugins = TextEditor.PLUGIN_FOLDER.listFiles();

		for(File PluginFile : Plugins) {
			if(FileUtils.isExtensionPresent(PluginFile, "plf")) {
				this.loadRuntimeFolder();
				this.loadPlugin(PluginFile);
			}
		}
	}

	public void loadRuntimeFolder() {
		if(!this.Run) {
			if(!TextEditor.RUNTIME_FOLDER.exists()) {
				TextEditor.RUNTIME_FOLDER.mkdirs();
			}

			this.Run = true;
		}
	}

	public void loadPlugin(File PluginFile) {
		String Directory = TextEditor.RUNTIME_FOLDER.getAbsolutePath() + File.separator + FileUtils.changeExtension(PluginFile, "").getName();
		ZipHandler.decompressZipDirectory(Directory, PluginFile.getAbsolutePath());

		String Name = null;
		String Version = null;
		String[] Author = null;
		String Description = "";
		String Language = "Javascript".toLowerCase();
		File MainFile = null;
		String Main = null;

		File pluginJsonFile = new File(Directory + File.separator + "plugin.json");

		if(pluginJsonFile.exists()) {
			JSONObject Object = new JSONObject(FileUtils.readFile(pluginJsonFile, ExceptionHandler.DEFAULT_HANDLER));

			if(Object.has("name") && Object.get("name") instanceof String NameString) {
				Name = NameString;
			}

			if(Object.has("version") && Object.get("version") instanceof String VersionString) {
				Version = VersionString;
			}

			if(Object.has("author")) {
				Object AuthorObject = Object.get("author");

				if(AuthorObject instanceof String AuthorString) {
					Author = new String[] {AuthorString};
				} else if(AuthorObject instanceof JSONArray Array) {
					ArrayList<String> Authors = new ArrayList<>();

					for(int i = 0; i < Array.length(); i++) {
						if(Array.get(i) instanceof String AuthorString) {
							Authors.add(AuthorString);
						}
					}

					Author = Authors.toArray(new String[Authors.size()]);
				}
			}

			if(Object.has("description") && Object.get("description") instanceof String DescriptionString) {
				Description = DescriptionString;
			}

			if(Object.has("language") && Object.get("language") instanceof String LanguageString) {
				LanguageString = LanguageString.toLowerCase();

				if(
						LanguageString.equals("java") ||
						LanguageString.equals("js") ||
						LanguageString.equals("javascript")
						) {
					Language = LanguageString;
				}
			}

			if(Object.has("main") && Object.get("main") instanceof String String) {
				if(Language.equals("java")) {
					Main = String;
				} else if(Language.equals("javascript")) {
					MainFile = new File(Directory + File.separator + String);

					if(!MainFile.exists()) {
						MainFile = new File(String);
					}

					if(!MainFile.exists()) {
						throw new IllegalArgumentException("Could not find the main file");
					}
				}
			}
		}

		try {
			if(Language.equals("java")) {
				URLClassLoader Loader = new URLClassLoader(new URL[] {PluginFile.toURI().toURL()}, this.PluginHandlerLoader);//URLClassLoader.newInstance(new URL[] {PluginFile.toURI().toURL()});
				Class<?> Class = Loader.loadClass(Main);
				ArrayList<Class<?>> InterfaceList = new ArrayList<>();

				for(Class<?> Interface : Class.getInterfaces()) {
					InterfaceList.add(Interface);
				}

				if(InterfaceList.contains(IPluginInitializer.class)) {
					IPluginInitializer Initializer = (IPluginInitializer) Class.getDeclaredConstructor().newInstance();
					Initializer.init(PluginProvider.getLocalTextEditor());
					Loader.close();
				} else {
					Loader.close();
					throw new IllegalArgumentException("'main' must implement interface of 'com.khopan.project.textEditor.plugin.api.PluginInitializer'");
				}
			} else if(Language.equals("javascript")) {
				this.Engine.eval(new FileReader(MainFile));
			}

			PluginProvider.Editor.PluginManager.addPlugin(new Plugin(Name, Version, Author, Description));
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	public NashornScriptEngineFactory Factory;
	public NashornScriptEngine Engine;

	public void createNashornEngine() {
		try {
			this.Factory = new NashornScriptEngineFactory();
			this.Engine = (NashornScriptEngine) this.Factory.getScriptEngine();
			this.Engine.put("Menu", new MenuHandler());
			this.Engine.put("Utils", new Utils());
			this.Engine.eval("var PluginProvider=com.khopan.project.textEditor.plugin.PluginProvider;var Theme=Java.type(\"com.khopan.project.textEditor.TextEditor.Theme\"),ItemType=Java.type(\"com.khopan.application.utils.menuBar.ItemType\"),Color=Java.type(\"java.awt.Color\");function setTheme(a){PluginProvider.getLocalTextEditor().setTheme(a)}");
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	public void executeFunction(String Function) {
		try {
			if(PluginHandler.hasFunction(this.Engine, Function)) {
				this.Engine.invokeFunction(Function);
			}
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	public static boolean hasFunction(ScriptEngine Engine, String Function) {
		return Engine.get(Function) instanceof ScriptObjectMirror;
	}
}
