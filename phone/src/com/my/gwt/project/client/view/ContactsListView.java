package com.my.gwt.project.client.view;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.my.gwt.project.shared.Contact;
import com.my.gwt.project.client.presenter.*;

public class ContactsListView extends Composite implements ContactsListPresenter.View {
	private final Button addButton;
	private final Button deleteButton;
	private final Button searchButton;
	private final TextBox searchingField;
	private FlexTable contactsNamesTable;
	private FlexTable contactsPhonesTable;
	private final FlexTable contentTable;

	public ContactsListView() {
	    DecoratorPanel contentTableDecorator = new DecoratorPanel();
	    initWidget(contentTableDecorator);
	    contentTableDecorator.setWidth("100%");
	    contentTableDecorator.setWidth("18em");

	    contentTable = new FlexTable();
	    contentTable.setWidth("100%");
	    contentTable.getCellFormatter().addStyleName(0, 0, "contacts-ListContainer");
	    contentTable.getCellFormatter().setWidth(0, 0, "100%");
	    contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);
	    
	    HorizontalPanel addDelHorizPanel = new HorizontalPanel();
	    addDelHorizPanel.setBorderWidth(0);
	    addDelHorizPanel.setSpacing(0);
	    addDelHorizPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
	    addButton = new Button("Add");
	    addDelHorizPanel.add(addButton);
	    deleteButton = new Button("Delete");
	    addDelHorizPanel.add(deleteButton);
	    contentTable.getCellFormatter().addStyleName(0, 0, "contacts-ListMenu");
	    contentTable.setWidget(0, 0, addDelHorizPanel);
	    
	    HorizontalPanel searchHorizPanel = new HorizontalPanel();
	    searchHorizPanel.setBorderWidth(0);
	    searchHorizPanel.setSpacing(0);
	    searchHorizPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
	    searchingField = new TextBox();
	    searchHorizPanel.add(searchingField);
	    searchButton = new Button("Search!");
	    searchHorizPanel.add(searchButton);
	    contentTable.getCellFormatter().addStyleName(0, 0, "contacts-ListMenu");
	    contentTable.setWidget(1, 0, searchHorizPanel);
	    
	    contactsNamesTable = new FlexTable();
	    contactsNamesTable.setCellSpacing(0);
	    contactsNamesTable.setCellPadding(0);
	    contactsNamesTable.setWidth("100%");
	    contactsNamesTable.addStyleName("contacts-ListContents");
	    contactsNamesTable.getColumnFormatter().setWidth(0, "15px");
	    contentTable.setWidget(2, 0, contactsNamesTable);
	    
	    contactsPhonesTable = new FlexTable();
	    contactsPhonesTable.setCellSpacing(0);
	    contactsPhonesTable.setCellPadding(0);
	    contactsPhonesTable.setWidth("100%");
	    contactsPhonesTable.addStyleName("contacts-ListContents");
	    contactsPhonesTable.getColumnFormatter().setWidth(0, "15px");
	    contentTable.setWidget(2, 1, contactsPhonesTable);
	    
	    contentTableDecorator.add(contentTable);
	}

	public HasClickHandlers getAddButton() {
		return addButton;
	}

	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}

	public HasClickHandlers getSearchButton() {
		return searchButton;
	}

	public HasClickHandlers getNamesList() {
		return contactsNamesTable;
	}
	
	public HasClickHandlers getPhonesList() {
		return contactsPhonesTable;
	}
	
	public String getSearchingText()
	{
		return searchingField.getValue();
	}

	public void setData(SortedMap<String, Contact> data) {
		contactsNamesTable.removeAllRows();
		contactsPhonesTable.removeAllRows();

		for (Entry<String, Contact> entry : data.entrySet()) {
			int positionID = Integer.valueOf(entry.getKey());
			contactsNamesTable.setWidget(positionID, 0, new CheckBox());
			contactsNamesTable.setText(positionID, 1, entry.getValue().getName());
			contactsPhonesTable.setText(positionID, 2, entry.getValue().getPhoneNumber());
		}
	}

	public int getClickedRow(ClickEvent event) {
		int selectedRow = -1;
		HTMLTable.Cell cell = contactsNamesTable.getCellForEvent(event);

		if (cell != null) {
			//не реагировать на нажатия, если происходит выбор чекбокса
			if (cell.getCellIndex() > 0) {
				selectedRow = cell.getRowIndex();
			}
		}
		return selectedRow;
	}

	public ArrayList<String> getSelectedRows() {
		ArrayList<String> selectedRows = new ArrayList<String>();
		for (Integer i = 0; i < contactsNamesTable.getRowCount(); ++i) {
			CheckBox checkBox = (CheckBox) contactsNamesTable.getWidget(i, 0);
			if (checkBox.getValue()) {
				selectedRows.add(i.toString());
			}
		}
		return selectedRows;
	}
}
