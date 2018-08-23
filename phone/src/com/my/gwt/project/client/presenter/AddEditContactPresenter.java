package com.my.gwt.project.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.my.gwt.project.client.PhoneServiceAsync;
import com.my.gwt.project.client.event.AddContactEvent;
import com.my.gwt.project.client.event.CancelEditContactEvent;
import com.my.gwt.project.client.event.UpdateContactEvent;
import com.my.gwt.project.shared.Contact;

public class AddEditContactPresenter implements Presenter
{
	public interface View 
	{
	    HasClickHandlers getSaveButton();
	    HasClickHandlers getCancelButton();
	    HasClickHandlers getDeleteButton();
		HasValue<String> getNameTextBox();
		HasValue<String> getPhoneTextBox();
		Widget asWidget();
	}
	
	private Contact contact;
	private final PhoneServiceAsync rpcService;
	private final HandlerManager eventBus; 
	private final View view;
	private String id;

	public AddEditContactPresenter(PhoneServiceAsync rpcService, HandlerManager eventBus, View view) 
	{
		this.contact = new Contact();
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		this.id = "-1";
		bind();
	}
	
	public AddEditContactPresenter(PhoneServiceAsync rpcService, HandlerManager eventBus, View view, String id) 
	{
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		this.id = id;
		bind();
		
		rpcService.getContact(id, new AsyncCallback<Contact>() 
		{
			public void onSuccess(Contact result) 
			{
				contact = result;
				AddEditContactPresenter.this.view.getNameTextBox().setValue(contact.getName());
				AddEditContactPresenter.this.view.getPhoneTextBox().setValue(contact.getPhoneNumber());
			}
			
			public void onFailure(Throwable caught) 
			{
				Window.alert("Error editing contact in AEP!");
			}
		});
	}

	public void go(HasWidgets container) 
	{
		bind();
		container.clear();
		container.add(view.asWidget());
	}

	private void bind() 
	{
		view.getSaveButton().addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
				saveContact();
			}
		});
		
		view.getCancelButton().addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				eventBus.fireEvent(new CancelEditContactEvent());
			}
		});
	}
	
	private void saveContact() 
	{
		String name = view.getNameTextBox().getValue();
		String phoneNumber = view.getPhoneTextBox().getValue();
		contact.setName(name);
		contact.setPhoneNumber(phoneNumber);
		checkDuplicate(contact);
		if (id.equals("-1"))
		{
			rpcService.addContact(contact, new AsyncCallback<Contact>() 
			{
				public void onSuccess(Contact result) 
				{
					eventBus.fireEvent(new UpdateContactEvent(contact));
				}
				
				public void onFailure(Throwable caught) 
				{
					Window.alert("Error adding contact in AEP!");
				}
			});
		} else
		{
			rpcService.updateContact(contact, new AsyncCallback<Contact>() 
			{
				public void onSuccess(Contact result) 
				{
					eventBus.fireEvent(new UpdateContactEvent(contact));
				}
				
				public void onFailure(Throwable caught) 
				{
					Window.alert("Error editing contact in AEP!");
				}
			});
		}
	}
	
	private void checkDuplicate(Contact contact)
	{
		rpcService.containsDuplicate(contact, new AsyncCallback<Boolean>() 
		{
			public void onSuccess(Boolean result) 
			{
				if (result) 
				{
					Window.alert("You can not add contact with such name/phone!"
							+ " Change name/phone and try again!");
				}
			}
			
			public void onFailure(Throwable caught) 
			{
				Window.alert("Error checking unique contact in AEP!");
			}
		});
	}
}
