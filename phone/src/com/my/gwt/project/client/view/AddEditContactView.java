package com.my.gwt.project.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddEditContactView extends Composite {
	private final TextBox name;
	private final TextBox phoneNumber;
	private final FlexTable contactsTable;
	private final Button saveButton;
	private final Button cancelButton;
	private final Button deleteButton;

	public AddEditContactView() {
	    DecoratorPanel contentDetailsDecorator = new DecoratorPanel();
	    contentDetailsDecorator.setWidth("18em");
	    initWidget(contentDetailsDecorator);

	    VerticalPanel contentContactsPanel = new VerticalPanel();
	    contentContactsPanel.setWidth("100%");

	    contactsTable = new FlexTable();
	    contactsTable.setCellSpacing(0);
	    contactsTable.setWidth("100%");
	    contactsTable.addStyleName("contacts-ListContainer");
	    contactsTable.getColumnFormatter().addStyleName(1, "add-contact-input");
	    name = new TextBox();
	    phoneNumber = new TextBox();
	    initContactsTable();
	    contentContactsPanel.add(contactsTable);
	    
	    HorizontalPanel menuPanel = new HorizontalPanel();
	    saveButton = new Button("Save");
	    cancelButton = new Button("Cancel");
	    deleteButton = new Button("Delete");
	    menuPanel.add(saveButton);
	    menuPanel.add(cancelButton);
	    menuPanel.add(deleteButton);
	    contentContactsPanel.add(menuPanel);
	    contentDetailsDecorator.add(contentContactsPanel);
	  }

	private void initContactsTable() {
		contactsTable.setWidget(0, 0, new Label("Name"));
		contactsTable.setWidget(0, 1, name);
		contactsTable.setWidget(1, 0, new Label("Phone number"));
		contactsTable.setWidget(1, 1, phoneNumber);
		name.setFocus(true);
	}

	public HasValue<String> getName() {
		return name;
	}

	public HasValue<String> getPhoneNumber() {
		return phoneNumber;
	}

	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}
	
	public HasClickHandlers getDeleteButton() {
		return deleteButton;
	}
}
