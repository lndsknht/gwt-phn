package com.my.gwt.project.client.presenter;

import java.util.ArrayList;
import java.util.SortedMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.my.gwt.project.client.PhoneServiceAsync;
import com.my.gwt.project.client.event.AddContactEvent;
import com.my.gwt.project.client.event.SearchContactEvent;
import com.my.gwt.project.shared.Contact;

public class ContactsListPresenter {
	
	public interface View {
		HasClickHandlers getAddButton();
		HasClickHandlers getDeleteButton();
		HasClickHandlers getSearchButton();
		String getSearchingText();
		int getClickedRow(ClickEvent event);
		ArrayList<String> getSelectedRows();
		void setData(SortedMap<String, Contact> data);
	}
	
	private SortedMap<String, Contact> contacts;
	private final PhoneServiceAsync rpcService; 
	private final HandlerManager eventBus;
	private final View view;
	
	public ContactsListPresenter(PhoneServiceAsync rpcService, HandlerManager eventBus, View view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
	}

	public void bind() {
		view.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddContactEvent());
			}
		});
		
		view.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteSelectedContacts();
			}
		});
		
		view.getSearchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new SearchContactEvent(view.getSearchingText()));
			}
		});
	}
	
	private void fetchContacts() {
		
		rpcService.getContacts(new AsyncCallback<SortedMap<String,Contact>>() {
			
			public void onSuccess(SortedMap<String, Contact> result) {
				contacts = result;
				view.setData(result);
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Ошибка получения данных!");
			}
		});
	}
	
	private void deleteSelectedContacts() {
		ArrayList<String> ids = view.getSelectedRows();
		rpcService.deleteContacts(ids, new AsyncCallback<SortedMap<String,Contact>>() {
			
			public void onSuccess(SortedMap<String, Contact> result) {
				contacts = result;
				view.setData(result);
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Ошибка при удалении выделенных контактов!");
			}
		});
	}
	
	private void getAssumptions(String name)
	{
		rpcService.getAssumptions(name, new AsyncCallback<SortedMap<String,Contact>>() {
			
			public void onSuccess(SortedMap<String, Contact> result) {
				view.setData(result);
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("Ошибка при попытке поиска по имени!");
			}
		});
		//TODO валидация имени - минимум 3 символа
	}

	//TODO придумать, как очищать поле поиска (доп кнопка очистки?)
}
