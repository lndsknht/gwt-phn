package com.my.gwt.project.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.my.gwt.project.client.presenter.Presenter;

public class AppController implements Presenter, ValueChangeHandler<String> {

	public AppController(PhoneServiceAsync rpcService, HandlerManager eventBus) {
		// TODO Auto-generated constructor stub
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		// TODO Auto-generated method stub
	}

	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
	}
}
