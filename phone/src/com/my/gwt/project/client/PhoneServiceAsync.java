package com.my.gwt.project.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.my.gwt.project.shared.Contact;

public interface PhoneServiceAsync {

	void addContact(Contact contact, AsyncCallback<Contact> callback);
	void updateContact(Contact contact, AsyncCallback<Contact> callback);
	void deleteContact(String id, AsyncCallback<Boolean> callback);
	void deleteContacts(ArrayList<String> ids, AsyncCallback<ArrayList<Contact>> callback);
	void getContact(String id, AsyncCallback<Contact> callback);
	void getContacts(AsyncCallback<HashMap<String, Contact>> callback);
}
