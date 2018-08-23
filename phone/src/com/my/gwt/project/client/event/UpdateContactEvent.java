package com.my.gwt.project.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.my.gwt.project.shared.Contact;

public class UpdateContactEvent extends GwtEvent<UpdateContactEventHandler> {
	public static Type<UpdateContactEventHandler> TYPE = new Type<UpdateContactEventHandler>();
	private final Contact contact;

	public UpdateContactEvent(Contact contact) {
		this.contact = contact;
	}
	
	public Contact getId() {
		return contact;
	}

	@Override
	public Type<UpdateContactEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateContactEventHandler handler) {
		handler.onUpdateContact(this);
	}

}
