package com.my.gwt.project.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CancelEditContactEvent extends GwtEvent<CancelEditContactEventHandler> {
	
	public static Type<CancelEditContactEventHandler> TYPE = new Type<CancelEditContactEventHandler>();
	
	@Override
	public Type<CancelEditContactEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CancelEditContactEventHandler handler) {
		handler.onCancelEditContact(this);
	}

}
