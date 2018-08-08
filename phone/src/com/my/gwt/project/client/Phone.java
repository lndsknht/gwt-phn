package com.my.gwt.project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Основной класс PhoneBook
 * 
 * */
public class Phone implements EntryPoint {

	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel searchPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private FlexTable mainFlexTable = new FlexTable();
	private TextBox newContactNameTextBox = new TextBox();
	private TextBox newContactNumberTextBox = new TextBox();
	private TextBox searchTextBox = new TextBox();
	private Button addContactButton = new Button("+");
	private Button searchButton = new Button("Search");

	public void onModuleLoad() {
		mainFlexTable.setText(0, 0, "Name");
		mainFlexTable.setText(0, 1, "Phone number");
		mainFlexTable.setText(0, 2, "Add");
		
		mainFlexTable.setCellPadding(6);
				
		mainFlexTable.getRowFormatter().addStyleName(0, "phonebookHeader");
		mainFlexTable.addStyleName("phonebookList");
		mainFlexTable.getCellFormatter().addStyleName(0, 0, "contactNameColumn");
		mainFlexTable.getCellFormatter().addStyleName(0, 1, "contactNumberColumn");
		mainFlexTable.getCellFormatter().addStyleName(0, 2, "addColumn");

		//Добавляем элементы управления на панель
		addPanel.add(newContactNameTextBox);
		addPanel.add(newContactNumberTextBox);
		addPanel.add(addContactButton);
		addPanel.addStyleName("addPanel");
		
		searchPanel.add(searchTextBox);
		searchPanel.add(searchButton);
		
		mainPanel.add(mainFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(searchPanel);

		//Связываем основную панель со стартовой html страницей
		RootPanel.get("phonebook").add(mainPanel);
		
		searchTextBox.setFocus(true);
		
		addContactButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				addContact();
			}
		});

		newContactNameTextBox.addKeyDownHandler(new KeyDownHandler() {

			public void onKeyDown(KeyDownEvent event) {

				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					
					addContact();
				}
			}
		});
	}

	private void addContact() {
		// TODO Auto-generated method stub
	}
}
