package com.my.gwt.project.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.my.gwt.project.client.PhoneService;
import com.my.gwt.project.shared.Contact;

@SuppressWarnings("serial")
public class PhoneServiceImpl extends RemoteServiceServlet implements PhoneService
{
	//’ранит значени€ id - им€, чтобы потом им€ можно было использовать дл€ поиска записи в contacts
	private HashMap<String, String> idsNames = Maps.newHashMap();
	//Trie - дл€ быстрого поиска контакта по подстроке-имени
	private Trie<String, Contact> contactsTrie = new PatriciaTrie<Contact>();
	//—писок доступных id. ¬ременное решение дл€ избежани€ коллизий. 
	//ѕосле удалени€ контакта, помещаем его id в список. ѕри добавлении нового контакта сначала провер€ем список
	//и если он не пуст - берЄм значени€ по очереди. ≈сли пуст - берЄм idsNames.size()
	ArrayList<String> availableIds = Lists.newArrayList();
	
	String[] sNames = new String[] { "Nedd", "Kate", "Robb", "Jon", "Bran", "Rickon", "Sansa", "Aria" };
	String[] sPhones = new String[] { "999", "888", "111", "777", "222", "333", "444", "555" };
	
	public PhoneServiceImpl() 
	{
		initContacts();
	}

	public Contact addContact(Contact contact) 
	{
		//TODO переделать генерацию uid во избежание коллизий
		String id;
		if (availableIds.size() != 0)
		{
			id = availableIds.get(0);
			availableIds.remove(0);
			availableIds.trimToSize();
		} else
		{
			id = String.valueOf(idsNames.size());
		}
		contact.setId(id);
		contactsTrie.put(contact.getName(), contact);
		idsNames.put(id, contact.getName());
		return contact;
	}

	public Contact updateContact(Contact contact)
	{
		String name = idsNames.get(contact.getId());
		contactsTrie.remove(name);
		contactsTrie.put(contact.getName(), contact);
		idsNames.remove(contact.getId());
		idsNames.put(contact.getId(), contact.getName());
		return contact;
	}

	public Contact getContact(String id) 
	{
		String name = idsNames.get(id);
		return contactsTrie.get(name);
	}

	public Boolean deleteContact(String id)
	{
		String name = idsNames.get(id);
		contactsTrie.remove(name);
		idsNames.remove(id);
		availableIds.add(id);
		return true;
	}

	public SortedMap<String, Contact> getContacts() 
	{
		return contactsTrie;
	}

	public SortedMap<String, Contact> deleteContacts(ArrayList<String> ids)
	{
		for (int i = 0; i < ids.size(); i++)
		{
			String id = ids.get(i);
			String name = idsNames.get(id);
			contactsTrie.remove(name);
			availableIds.add(id);
			idsNames.remove(id);
		}
		return contactsTrie;
	}

	public SortedMap<String, Contact> getAssumptions(String name) 
	{
		return contactsTrie.prefixMap(name);
	}

	public Boolean containsDuplicate(Contact contact)
	{
		return contactsTrie.values().contains(contact);
	}
	
	/**
	 * »нициализировать список контактов тестовыми значени€ми 
	 **/
	private void initContacts()
	{
		for (int i = 0; i < sNames.length; i++)
		{
			String id = String.valueOf(i);
			Contact contact = new Contact();
			contact.setId(id);
			contact.setName(sNames[i]);
			contact.setPhoneNumber(sPhones[i]);
			contactsTrie.put(id, contact);
			idsNames.put(id, contact.getName());
		}
	}
}
