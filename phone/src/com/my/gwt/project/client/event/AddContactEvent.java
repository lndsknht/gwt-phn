package com.my.gwt.project.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.my.gwt.project.client.event.AddContactEventHandler;

public class AddContactEvent extends GwtEvent<AddContactEventHandler> {
	
	public static Type<AddContactEventHandler> TYPE = new Type<AddContactEventHandler>();

	@Override
	public Type<AddContactEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddContactEventHandler handler) {
		handler.onAddContact(this);
	}
}
