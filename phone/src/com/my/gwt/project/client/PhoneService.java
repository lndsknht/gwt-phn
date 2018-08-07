package com.my.gwt.project.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.my.gwt.project.shared.Contact;

@RemoteServiceRelativePath("phoneService")
public interface PhoneService extends RemoteService {

	Contact addContact(Contact contact);
	Contact updateContact(Contact contact);
	Contact getContact(String id);
	Boolean deleteContact(String id);
	HashMap<String, Contact> getContacts();
	ArrayList<Contact> deleteContacts(ArrayList<String> ids);
}
