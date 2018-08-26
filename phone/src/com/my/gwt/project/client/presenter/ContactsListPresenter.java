package com.my.gwt.project.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.my.gwt.project.client.event.EditContactEvent;
import com.my.gwt.project.shared.Contact;

public class ContactsListPresenter implements Presenter 
{
	public interface View 
	{
		HasClickHandlers getAddButton();
		HasClickHandlers getDeleteButton();
		HasClickHandlers getSearchButton();
		HasClickHandlers getClearSearchButton();
		HasClickHandlers getContactsList();
		HasValue<String> getSearchTextBox();
		String getClickedCellText(ClickEvent event);
		ArrayList<String> getSelectedRowsIds();
		int getClickedRow(ClickEvent event);
		void setData(HashMap<String, Contact> contacts);
		Widget asWidget();
	}
	
	private HashMap<String, Contact> contacts;
	private final PhoneServiceAsync rpcService; 
	private final HandlerManager eventBus;
	private final View view;
	
	public ContactsListPresenter(PhoneServiceAsync rpcService, HandlerManager eventBus, View view) 
	{
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
	}

	public void bind() 
	{
		view.getAddButton().addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				eventBus.fireEvent(new AddContactEvent());
			}
		});
		
		view.getDeleteButton().addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) 
			{
				deleteSelectedContacts();
			}
		});
		
		view.getSearchButton().addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				getAssumptions(view.getSearchTextBox().getValue());
			}
		});
		
		view.getContactsList().addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				int row = view.getClickedRow(event);
				if (row >= 0) 
				{
					String id = view.getClickedCellText(event);
					eventBus.fireEvent(new EditContactEvent(String.valueOf(id)));
				}
			}
		});
		
		view.getClearSearchButton().addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				view.getSearchTextBox().setValue("");
				view.setData(contacts);
			}
		});
	}
	
	private void fetchContacts() 
	{
		rpcService.getContacts(new AsyncCallback<HashMap<String,Contact>>() 
		{ 
			public void onSuccess(HashMap<String,Contact> result) 
			{
				contacts = result;
				view.setData(result);
			}

			public void onFailure(Throwable caught)
			{
				Window.alert("Error fetching contacts in LP!");
			};
			
		});
	}
	
	private void deleteSelectedContacts() 
	{
		ArrayList<String> selectedRows = view.getSelectedRowsIds();
		
		if (selectedRows.size() > 0)
		{
			rpcService.deleteContacts(selectedRows, new AsyncCallback<HashMap<String,Contact>>() 
			{
				public void onSuccess(HashMap<String, Contact> result) 
				{
					view.setData(result);
				}
			
				public void onFailure(Throwable caught) 
				{
					Window.alert("Error deleteing contacts in LP!");
				}
			});
		}
	}
	
	private void getAssumptions(String name) 
	{
		rpcService.getAssumptions(name, new AsyncCallback<HashMap<String,Contact>>() 
		{
			public void onSuccess(HashMap<String, Contact> result) 
			{
				view.setData(result);
			}
			
			public void onFailure(Throwable caught) 
			{
				Window.alert("Error searching by name in LP!");
			}
		});
	}

	public void go(HasWidgets container) 
	{
		bind();
		container.clear();
		container.add(view.asWidget());
		fetchContacts();
	}
}
