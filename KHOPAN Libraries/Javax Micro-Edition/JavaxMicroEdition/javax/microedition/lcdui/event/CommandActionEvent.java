/*
 * Copyright 2012 Kulikov Dmitriy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.microedition.lcdui.event;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Event;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.util.ArrayStack;

public class CommandActionEvent extends Event
{
	private static ArrayStack<CommandActionEvent> recycled = new ArrayStack();
	
	private CommandListener listener;
	private ItemCommandListener itemlistener;
	
	private Command command;
	private Displayable displayable;
	private Item item;
	
	private CommandActionEvent()
	{
	}
	
	public static Event getInstance(CommandListener listener, Command command, Displayable displayable)
	{
		CommandActionEvent instance = recycled.pop();
		
		if(instance == null)
		{
			instance = new CommandActionEvent();
		}
		
		instance.listener = listener;
		instance.command = command;
		instance.displayable = displayable;
		
		return instance;
	}
	
	public static Event getInstance(ItemCommandListener itemlistener, Command command, Item item)
	{
		CommandActionEvent instance = recycled.pop();
		
		if(instance == null)
		{
			instance = new CommandActionEvent();
		}
		
		instance.itemlistener = itemlistener;
		instance.command = command;
		instance.item = item;
		
		return instance;
	}
	
	public void process()
	{
		if(listener != null)
		{
			listener.commandAction(command, displayable);
		}
		else if(itemlistener != null)
		{
			itemlistener.commandAction(command, item);
		}
	}
	
	public void recycle()
	{
		listener = null;
		itemlistener = null;
		
		command = null;
		displayable = null;
		item = null;
		
		recycled.push(this);
	}
	
	public void enterQueue()
	{
	}
	
	public void leaveQueue()
	{
	}
	
	public boolean placeableAfter(Event event)
	{
		return true;
	}
	
//	public String toString()
//	{
//		if(listener != null)
//		{
//			return "??ommandAction(" + command + ", " + displayable + ")";
//		}
//		else if(itemlistener != null)
//		{
//			return "??ommandAction(" + command + ", " + item + ")";
//		}
//		else
//		{
//			return "??ommandAction(" + command + ")";
//		}
//	}
}