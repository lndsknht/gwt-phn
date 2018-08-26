package com.my.gwt.project.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.my.gwt.project.client.PhoneService;
import com.my.gwt.project.shared.Contact;

@SuppressWarnings("serial")
public class PhoneServiceImpl extends RemoteServiceServlet implements PhoneService
{
	//Trie - для быстрого поиска контакта по подстроке-имени
	private Trie<String, Contact> contactsTrie = new PatriciaTrie<Contact>();

	String[] sNames = new String[] { "Nedd", "Kate", "Robb", "Jon", "Bran", "Rickon", "Sansa", "Aria" };
	String[] sPhones = new String[] { "999", "888", "111", "777", "222", "333", "444", "555" };
	
	public PhoneServiceImpl() 
	{
		initContacts();
	}

	public Contact addContact(Contact contact) 
	{
		String id = contact.toString();
		contact.setId(id);
		contactsTrie.put(id, contact);
		return contact;
	}

	public Contact updateContact(String oldId, Contact contact)
	{
		contactsTrie.remove(oldId);
		contactsTrie.put(contact.getId(), contact);
		return contact;
	}

	public Contact getContact(String id) 
	{
		return contactsTrie.get(id);
	}
	
	public HashMap<String, Contact> getContacts() 
	{
		return Maps.newHashMap(contactsTrie);
	}

	public Boolean deleteContact(String id)
	{
		contactsTrie.remove(id);
		return true;
	}

	public HashMap<String, Contact> deleteContacts(ArrayList<String> ids)
	{
		for (int i = 0; i < ids.size(); i++)
		{
			String id = ids.get(i);
			contactsTrie.remove(id);
		}
		return Maps.newHashMap(contactsTrie);
	}

	public HashMap<String, Contact> getAssumptions(String name) 
	{
		return Maps.newHashMap(contactsTrie.prefixMap(name));
	}

	public Boolean containsDuplicate(Contact contact)
	{
		return contactsTrie.containsKey(contact.toString());
	}
	
	/**
	 * Инициализировать список контактов тестовыми значениями 
	 */
	private void initContacts()
	{
		for (int i = 0; i < sNames.length; i++)
		{
			Contact contact = new Contact();
			contact.setName(sNames[i]);
			contact.setPhoneNumber(sPhones[i]);
			String id = contact.toString();
			contact.setId(id);
			contactsTrie.put(id, contact);
		}
	}
}
