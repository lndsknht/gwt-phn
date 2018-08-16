package com.my.gwt.project.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SearchContactEvent extends GwtEvent<SearchContactEventHandler> {
	public static Type<SearchContactEventHandler> TYPE = new Type<SearchContactEventHandler>();
	private final String name;
	
	public SearchContactEvent(String searchingName) {
		name = searchingName;
	}

	public String getName() {
		return name;
	}

	@Override
	public Type<SearchContactEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SearchContactEventHandler handler) {
		handler.onSearchContact(this);
	}

}
