package com.my.gwt.project.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
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
	private Button addContactButton = new Button("+");
	private ArrayList<String> phoneNumbers = new ArrayList<String>();

	@Override
	public void onModuleLoad() {
		mainFlexTable.setText(0, 0, "Name");
		mainFlexTable.setText(0, 1, "Phone number");
		mainFlexTable.setText(0, 2, "Add");
		
		mainFlexTable.setCellPadding(6);
				
		mainFlexTable.getRowFormatter().addStyleName(0, "phonebookHeader");
		mainFlexTable.addStyleName("phonebookList");
		mainFlexTable.getCellFormatter().addStyleName(0, 1, "contactNameColumn");
		mainFlexTable.getCellFormatter().addStyleName(0, 2, "contactNumberColumn");

		//Добавляем элементы управления на панель
		addPanel.add(newContactNameTextBox);
		addPanel.add(newContactNumberTextBox);
		addPanel.add(addContactButton);
		addPanel.addStyleName("addPanel");
		
		mainPanel.add(mainFlexTable);
		mainPanel.add(addPanel);

		//Связываем основную панель со стартовой html страницей
		RootPanel.get("phonebook").add(mainPanel);
	}
	
}
