package com.my.gwt.project.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateContactEvent extends GwtEvent<UpdateContactEventHandler> {
	public static Type<UpdateContactEventHandler> TYPE = new Type<UpdateContactEventHandler>();
	private final String id;

	public UpdateContactEvent(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
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
