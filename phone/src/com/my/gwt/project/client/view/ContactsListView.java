package com.my.gwt.project.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.my.gwt.project.shared.Contact;
import com.my.gwt.project.client.presenter.*;

public class ContactsListView extends Composite implements ContactsListPresenter.View {
	private final Button addButton;
	private final Button deleteButton;
	private final Button searchButton;
	private final Button clearSearchButton;
	private final TextBox searchingField;
	private FlexTable namesTable;
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
		clearSearchButton = new Button("X");
		searchHorizPanel.add(clearSearchButton);
		searchButton = new Button("Search!");
		searchHorizPanel.add(searchButton);
		contentTable.getCellFormatter().addStyleName(0, 0, "contacts-ListMenu");
		contentTable.setWidget(1, 0, searchHorizPanel);

		namesTable = new FlexTable();
		namesTable.setCellSpacing(0);
		namesTable.setCellPadding(0);
		namesTable.setWidth("100%");
		namesTable.addStyleName("contacts-ListContents");
		namesTable.getColumnFormatter().setWidth(0, "15px");
		contentTable.setWidget(2, 0, namesTable);

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

	public HasClickHandlers getClearSearchButton() {
		return clearSearchButton;
	}

	public HasClickHandlers getContactsList() {
		return namesTable;
	}

	public HasValue<String> getSearchTextBox() {
		return searchingField;
	}

	public void setData(HashMap<String, Contact> data) {
		namesTable.removeAllRows();
		
		int position = 0;
		for (Entry<String, Contact> entry : data.entrySet()) {
			namesTable.setWidget(position, 0, new CheckBox());
			namesTable.setText(position, 1, entry.getKey());
			position++;
		}
	}

	public int getClickedRow(ClickEvent event) {
		int selectedRow = -1;
		HTMLTable.Cell cell = namesTable.getCellForEvent(event);

		if (cell != null) {
			// не реагировать на нажатия, если происходит выбор чекбокса
			if (cell.getCellIndex() > 0) {
				selectedRow = cell.getRowIndex();
			}
		}
		return selectedRow;
	}

	public String getClickedCellText(ClickEvent event) {
		String cellText = "";
		HTMLTable.Cell cell = namesTable.getCellForEvent(event);

		if (cell != null) {
			// не реагировать на нажатия, если происходит выбор чекбокса
			if (cell.getCellIndex() > 0) {
				cellText = cell.getElement().getInnerText();
			}
		}
		return cellText;
	}

	public ArrayList<String> getSelectedRowsIds() {
		ArrayList<String> selectedIds = new ArrayList<String>();
		for (int i = 0; i < namesTable.getRowCount(); ++i) {
			CheckBox checkBox = (CheckBox) namesTable.getWidget(i, 0);
			if (checkBox.getValue()) {
				String cellText = namesTable.getText(i, 1);//getWidget(i, 1).getElement().getInnerText();
				selectedIds.add(cellText);
			}
		}
		return selectedIds;
	}
}
