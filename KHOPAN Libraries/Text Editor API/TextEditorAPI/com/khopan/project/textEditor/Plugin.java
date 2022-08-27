package com.khopan.project.textEditor;

public class Plugin {
	public final String Name;
	public final String Version;
	public final String[] Author;
	public final String Description;

	public Plugin(String Name, String Version, String[] Author, String Description) {
		this.Name = Name;
		this.Version = Version;
		this.Author = Author;
		this.Description = Description;
	}
}
