package com.my.gwt.project.client.presenter;

import java.util.Set;

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
import com.my.gwt.project.client.event.CancelEditContactEvent;
import com.my.gwt.project.client.event.DeleteContactEvent;
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
	private Set<String> contactIds;

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
				Window.alert("Error editing contact!");
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
		this.view.getSaveButton().addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
				String name = view.getNameTextBox().getValue();
				String phoneNumber = view.getPhoneTextBox().getValue();
				contact.setName(name);
				contact.setPhoneNumber(phoneNumber);
				if (validNameAndNumber(contact))
				{
					saveContact();
				}
				else
				{
					Window.alert("Enter correct name-phone values and try again!");
				}
			}
		});
		
		this.view.getCancelButton().addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				eventBus.fireEvent(new CancelEditContactEvent());
			}
		});
		
		this.view.getDeleteButton().addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				deleteContact(id);
			}
		});
	}
	
	private void saveContact() 
	{
		if (id.equals("-1"))
		{
			if (!hasDuplicate(contact))
			{
				rpcService.addContact(contact, new AsyncCallback<Contact>() 
				{
					public void onSuccess(Contact result) 
					{
						eventBus.fireEvent(new UpdateContactEvent(contact));
					}
					
					public void onFailure(Throwable caught) 
					{
						Window.alert("Error adding contact!");
					}
				});
			}
			else
			{
				Window.alert("Enter correct name-phone values and try again!");
			}
		} 
		else
		{
			contact.setId(contact.toString());
			rpcService.updateContact(id, contact, new AsyncCallback<Contact>() 
			{
				public void onSuccess(Contact result) 
				{
					eventBus.fireEvent(new UpdateContactEvent(contact));
				}
				
				public void onFailure(Throwable caught) 
				{
					Window.alert("Error editing contact!");
				}
			});
		}
	}
	
	private boolean hasDuplicate(Contact contact)
	{
		rpcService.getContactsIds(new AsyncCallback<Set<String>>() 
		{
			public void onSuccess(Set<String> result) 
			{
				contactIds = result;
			}
			
			public void onFailure(Throwable caught) 
			{
				Window.alert("Error checking duplicate!");
			}
		});
		
		return contactIds.contains(contact.toString());
	}
	
	private void deleteContact(String id)
	{
		if (!id.equals("-1"))
		{
			rpcService.deleteContact(id, new AsyncCallback<Boolean>() 
			{
				public void onSuccess(Boolean result) 
				{
					eventBus.fireEvent(new DeleteContactEvent());
				}
				
				public void onFailure(Throwable caught) 
				{
					Window.alert("Error during deleting contact from card!");
				}
			});
		}
	}
	
	private boolean validNameAndNumber(Contact contact)
	{
		String name = contact.getName();
		String phoneNumber = contact.getPhoneNumber();
		return name.length() >= 2 && name.matches("[a-zA-Z]+") && phoneNumber.length() >= 2;
	}
}
