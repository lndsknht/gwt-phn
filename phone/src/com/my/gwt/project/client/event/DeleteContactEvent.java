package com.my.gwt.project.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteContactEvent extends GwtEvent<DeleteContactEventHandler> {
	
	public static Type<DeleteContactEventHandler> TYPE = new Type<DeleteContactEventHandler>();

	@Override
	public Type<DeleteContactEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteContactEventHandler handler) {
		handler.onDeleteContact(this);
	}
}
