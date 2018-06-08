package com.my.gwt.project.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.my.gwt.project.shared.CreateContact;
import com.my.gwt.project.shared.CreateContactResult;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

/**
 * �������� ����� PhoneBook
 * 
 * */
public class Phone implements EntryPoint {
	private final DispatchAsync dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel searchPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private FlexTable mainFlexTable = new FlexTable();
	private TextBox nameTextBox = new TextBox();
	private TextBox numberTextBox = new TextBox();
	private TextBox searchTextBox = new TextBox();
	private Button addContactButton = new Button("+");
	private Button searchButton = new Button("Search");
	
	private static List<String> names = new ArrayList<>();
	private static List<String> phoneNumbers = new ArrayList<>();
	private static boolean lineIsNum = false;

	@Override
	public void onModuleLoad() {
		mainFlexTable.setText(0, 0, "Name");
		mainFlexTable.setText(0, 1, "Phone number");
		mainFlexTable.setText(0, 2, "Remove");
		
		mainFlexTable.setCellPadding(6);
		mainFlexTable.getRowFormatter().addStyleName(0, "phonebookHeader");
		mainFlexTable.addStyleName("phonebookList");
		mainFlexTable.getCellFormatter().addStyleName(0, 0, "contactNameColumn");
		mainFlexTable.getCellFormatter().addStyleName(0, 1, "contactNumberColumn");
		mainFlexTable.getCellFormatter().addStyleName(0, 2, "removeColumn");

		//��������� �������� ���������� �� ������
		addPanel.add(nameTextBox);
		addPanel.add(numberTextBox);
		addPanel.add(addContactButton);
		addPanel.addStyleName("addPanel");
		
		searchPanel.add(searchTextBox);
		searchPanel.add(searchButton);
		
		mainPanel.add(mainFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(searchPanel);

		//��������� �������� ������ �� ��������� html ���������
		RootPanel.get("phonebook").add(mainPanel);
		
		searchTextBox.setFocus(true);
		
		addContactButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addContact();
			}
		});

		nameTextBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {

				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addContact();
				}
			}
		});
		
		searchTextBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (names.size() != 0) {
						updateTableContent(searchTextBox.getText());
						} else {
							Window.alert("� ������ ��������� ����������� ������!");
							return;
						}
				}
			}
		});
		
		searchTextBox.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE)
				{
					if (names.size() != 0) {
						showAllContacts();
					}
				}
			}
		});
		
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (names.size() != 0) {
					updateTableContent(searchTextBox.getText());
				} else {
					Window.alert("� ������ ��������� ����������� ������!");
					return;
				}
			}
		});
	}
	
	private void showAllContacts()
	{
		mainFlexTable.removeAllRows();
		mainFlexTable.setText(0, 0, "Name");
		mainFlexTable.setText(0, 1, "Phone number");
		mainFlexTable.setText(0, 2, "Remove");		
		
		mainFlexTable.setCellPadding(6);
		mainFlexTable.getRowFormatter().addStyleName(0, "phonebookHeader");
		mainFlexTable.addStyleName("phonebookList");
		mainFlexTable.getCellFormatter().addStyleName(0, 0, "contactNameColumn");
		mainFlexTable.getCellFormatter().addStyleName(0, 1, "contactNumberColumn");
		mainFlexTable.getCellFormatter().addStyleName(0, 2, "removeColumn");
		
		for (int i = 0; i < names.size(); i++)
		{
			renderChangesOnFlexTable(names.get(i), phoneNumbers.get(i));
		}
	}
	
	/**
	 * �������� ���������� ������ ��������� � ����������� �� ��������� ����������
	 * */
	private void updateTableContent(String searchingLine) {
		HashMap<String, String> foundedMatches = guess(searchingLine);
		if (foundedMatches.size() == 0)
		{
			return;
		}
		for (int i = 1; i <= mainFlexTable.getRowCount(); i++)
		{
			mainFlexTable.removeRow(i);
		}
		
		Iterator<?> iterator = foundedMatches.entrySet().iterator();
		while (iterator.hasNext())
		{
			Map.Entry<String, String> pair = (Entry) iterator.next();
			renderChangesOnFlexTable((String)pair.getValue(), (String)pair.getKey());
		}
	}

	private void addContact() {
		String rawNumber = numberTextBox.getText();
		String contactName = nameTextBox.getText();
		
		String noramlizedNumber = normalizeNum(rawNumber);
		if (FieldValidator.isValidName(contactName) && FieldValidator.isValidNumber(noramlizedNumber)) {
			if (!phoneNumbers.contains(noramlizedNumber)) {
//				names.add(contactName);
//				phoneNumbers.add(noramlizedNumber);
				dispatchAsync.execute(new CreateContact(contactName, noramlizedNumber), new AsyncCallback<CreateContactResult>() {
					public void onFailure(Throwable e ) {
		                Window.alert( "Error: " + e.getMessage() );
		            }

					public void onSuccess(CreateContactResult result) {
						Window.alert("Contact successfully added to the list!");
					}
				});
			} else {
				Window.alert("������� � ����� ������� ��� ����������!");
				return;
			}
		} else {
			Window.alert("������� � ������ ������ � ������� �� ����� ���� ��������!");
			return;
		}
		
		nameTextBox.setText("");
		numberTextBox.setText("");
		renderChangesOnFlexTable(contactName, noramlizedNumber);
	}
	
	private void renderChangesOnFlexTable(String contactName, String noramlizedNumber)
	{
		int rowsNum = mainFlexTable.getRowCount();
		mainFlexTable.setText(rowsNum, 0, contactName);
		mainFlexTable.setText(rowsNum, 1, noramlizedNumber);
//		mainFlexTable.setWidget(rowsNum, 2, new Label());
		
//		mainFlexTable.getCellFormatter().addStyleName(rowsNum, 1, "contactNameColumn");
//		mainFlexTable.getCellFormatter().addStyleName(rowsNum, 2, "contactNumberColumn");
//		mainFlexTable.getCellFormatter().addStyleName(rowsNum, 3, "removeColumn");

		Button removeContactButton = new Button("x");
		removeContactButton.addStyleDependentName("removeColumn");
		removeContactButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				int removedIndex = names.indexOf(contactName);
				names.remove(removedIndex);
				phoneNumbers.remove(removedIndex);
				mainFlexTable.removeRow(removedIndex + 1);
			}
		});
		mainFlexTable.setWidget(rowsNum, 2, removeContactButton);
	}
	
	/**
	 * ������� ��� ������ ������� �� ������, ������� ������ �����
	 * @param phoneNumber ����� ��� ��������������
	 * @return ����������������� �����
	 */
	private static String normalizeNum(String phoneNumber) {
		if (phoneNumber == null)
			return "";
		return phoneNumber.replaceAll("[^0-9]", "");
	}

	/**
	 * ����� ��� �����������, �������� �� ������ ������ �����
	 * @param textToCheck ����������� ������
	 * @return true - ���� ������ �����, false - ���� ������ ������� ������ ��
	 *         ���� ��� �� �� ���� � ����
	 */
	private static boolean isNumber(String textToCheck) {
		return textToCheck.matches("\\d+");
	}

	private static String[] getEntries(String numOrName) {
		lineIsNum = isNumber(numOrName);
		Stream<String> stream = lineIsNum ? phoneNumbers.stream() : names.stream();
		return stream.filter(strings -> strings.contains(numOrName)).toArray(String[]::new);
	}

	/**
	 * �������� ��������� ���������� � ���� ���� (�����-���)
	 * @return ���� � ���������� ���������� (�����-���)
	 * */
	private static HashMap<String, String> guess(String numOrName) {
		String[] entries = getEntries(numOrName);
		HashMap<String, String> foundedLines = new HashMap<>();
		if (entries.length == 0)
		{
			Window.alert("�� ������� ������ �� �������");
			return foundedLines;
		}

		if (lineIsNum) {
			for (int i = 0; i < entries.length; i++) 
			{
				foundedLines.put(entries[i], names.get(phoneNumbers.indexOf(entries[i])));
			}
		} else {
			for (int i = 0; i < entries.length; i++) 
			{
				foundedLines.put(phoneNumbers.get(names.indexOf(entries[i])), entries[i]);
			}
		}
		return foundedLines;
	}
}
