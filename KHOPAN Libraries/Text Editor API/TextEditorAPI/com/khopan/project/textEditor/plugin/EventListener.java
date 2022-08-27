package com.khopan.project.textEditor.plugin;

import java.util.Objects;

import com.khopan.list.ItemList;

public class EventListener {
	public ItemList<Event> Events;

	public EventListener() {
		this.Events = new ItemList<>();
	}

	public void registerEvent(String EventName) {
		this.Events.add(new Event(EventName));
	}

	public void eventRun(String EventName) {
		Event Event = this.getEvent(Objects.requireNonNull(EventName, "The Event Name Cannot Be Null."));

		if(Event != null) {
			for(Runnable Command : Event.Commands) {
				if(Command != null) {
					Command.run();
				}
			}
		} else {
			throw new IllegalArgumentException("The Event '" + EventName + "' Was An Unregistered Event.");
		}
	}

	public void onEventRun(String EventName, Runnable Command) {
		Event Event = this.getEvent(Objects.requireNonNull(EventName, "The Event Name Cannot Be Null."));

		if(Event != null) {
			Event.Commands.add(Objects.requireNonNull(Command, "The Command Cannot Be Null."));
		} else {
			throw new IllegalArgumentException("The Event '" + EventName + "' Was Not Exist.");
		}
	}

	public Event getEvent(String EventName) {
		for(int i = 0; i < this.Events.size(); i++) {
			Event Event = this.Events.get(i);

			if(Event.Name.equals(EventName)) {
				return Event;
			}
		}

		return null;
	}
}
