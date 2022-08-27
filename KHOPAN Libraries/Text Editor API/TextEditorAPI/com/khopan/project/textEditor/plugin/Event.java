package com.khopan.project.textEditor.plugin;

import java.util.ArrayList;

public class Event {
	public String Name;
	public final ArrayList<Runnable> Commands;

	public Event(String Name) {
		this.Name = Name;
		this.Commands = new ArrayList<>();
	}
}
