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
	private boolean hasDuplicate = false;

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
		this.view.getSaveButton().addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
				checkNameNumAndDuplicate();
				if (!hasDuplicate) 
				{
					saveContact();
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
			contact.setId(contact.toString());
			rpcService.updateContact(id, contact, new AsyncCallback<Contact>() 
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
					hasDuplicate = true;
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
	
	private void checkNameNumAndDuplicate()
	{
		String name = view.getNameTextBox().getValue();
		String phoneNumber = view.getPhoneTextBox().getValue();
		contact.setName(name);
		contact.setPhoneNumber(phoneNumber);
		if (validNameAndNumber(contact))
		{
			checkDuplicate(contact);
		}
		else
		{
			Window.alert("Name/number is/are incorrect!");
			return;
		}
	}
}
