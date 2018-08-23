package com.my.gwt.project.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.my.gwt.project.client.event.AddContactEvent;
import com.my.gwt.project.client.event.AddContactEventHandler;
import com.my.gwt.project.client.event.CancelEditContactEvent;
import com.my.gwt.project.client.event.CancelEditContactEventHandler;
import com.my.gwt.project.client.event.DeleteContactEvent;
import com.my.gwt.project.client.event.DeleteContactEventHandler;
import com.my.gwt.project.client.event.EditContactEvent;
import com.my.gwt.project.client.event.EditContactEventHandler;
import com.my.gwt.project.client.event.UpdateContactEvent;
import com.my.gwt.project.client.event.UpdateContactEventHandler;
import com.my.gwt.project.client.presenter.AddEditContactPresenter;
import com.my.gwt.project.client.presenter.ContactsListPresenter;
import com.my.gwt.project.client.presenter.Presenter;
import com.my.gwt.project.client.view.AddEditContactView;
import com.my.gwt.project.client.view.ContactsListView;

public class AppController implements Presenter, ValueChangeHandler<String> 
{
	private final HandlerManager eventBus;
	private final PhoneServiceAsync rpcService;
	private HasWidgets container;
	
	public AppController(PhoneServiceAsync rpcService, HandlerManager eventBus) 
	{
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		bind();
	}

	private void bind() 
	{
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(AddContactEvent.TYPE, new AddContactEventHandler()
		{
			public void onAddContact(AddContactEvent event) 
			{
				addContact();
			}
		});
		
		eventBus.addHandler(EditContactEvent.TYPE, new EditContactEventHandler() 
		{
			public void onEditContact(EditContactEvent event) 
			{
				editContact(event.getId());
			}
		});
		
		eventBus.addHandler(UpdateContactEvent.TYPE, new UpdateContactEventHandler() 
		{
			public void onUpdateContact(UpdateContactEvent event) 
			{
				updateContact();
			}
		});
		
		eventBus.addHandler(CancelEditContactEvent.TYPE, new CancelEditContactEventHandler() 
		{
			public void onCancelEditContact(CancelEditContactEvent event) 
			{
				cancelEditContact();
			}
		});
		
		eventBus.addHandler(DeleteContactEvent.TYPE, new DeleteContactEventHandler() 
		{
			public void onDeleteContact(DeleteContactEvent event) 
			{
				deleteContact();
			}
		});
	}
	
	private void addContact()
	{
		History.newItem("add");
	}
	
	private void editContact(String id)
	{
		History.newItem("edit", false);
		Presenter presenter = new AddEditContactPresenter(rpcService, eventBus, new AddEditContactView(), id);
		presenter.go(container);
	}
	
	private void updateContact()
	{
		History.newItem("list");
	}
	
	private void cancelEditContact()
	{
		History.newItem("list");
	}
	
	private void deleteContact()
	{
		History.newItem("list");
	}

	public void onValueChange(ValueChangeEvent<String> event) 
	{
		String historyToken = event.getValue();
		
		if (historyToken != null) 
		{
			Presenter presenter = null;
			if (historyToken.equals("list"))
			{
				presenter = new ContactsListPresenter(rpcService, eventBus, new ContactsListView());
			}
			else if (historyToken.equals("add"))
			{
				presenter = new AddEditContactPresenter(rpcService, eventBus, new AddEditContactView());
			} else if (historyToken.equals("edit"))
			{
				presenter = new AddEditContactPresenter(rpcService, eventBus, new AddEditContactView());
			}
			
			if (presenter != null)
			{
				presenter.go(container);
			}
		}
	}

	public void go(HasWidgets container) 
	{
		this.container = container;
		
		if (History.getToken().equals(""))
		{
			History.newItem("list");
		}
		else 
		{
			History.fireCurrentHistoryState();
		}
	}
}
