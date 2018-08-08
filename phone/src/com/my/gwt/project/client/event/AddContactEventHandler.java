package com.my.gwt.project.client.event;

import com.my.gwt.project.client.event.AddContactEvent;
import com.google.gwt.event.shared.EventHandler;

public interface AddContactEventHandler extends EventHandler{
	void onAddContact(AddContactEvent event);
}
