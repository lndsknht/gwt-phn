package com.my.gwt.project.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.my.gwt.project.client.PhoneService;
import com.my.gwt.project.shared.Contact;

@SuppressWarnings("serial")
public class PhoneServiceImpl extends RemoteServiceServlet implements PhoneService {
	
	//������ �������� id - ���, ����� ����� ��� ����� ���� ������������ ��� ������ ������ � contacts
	private HashMap<String, String> idsNames = Maps.newHashMap();
	//Trie - ��� �������� ������ �������� �� ���������-�����
	private Trie<String, Contact> contactsTrie = new PatriciaTrie<Contact>();
	//������ ��������� id. ��������� ������� ��� ��������� ��������. 
	//����� �������� ��������, �������� ��� id � ������. ��� ���������� ������ �������� ������� ��������� ������
	//� ���� �� �� ���� - ���� �������� �� �������. ���� ���� - ���� idsNames.size()
	ArrayList<String> availableIds = Lists.newArrayList();

	public Contact addContact(Contact contact) {
		//TODO ���������� ��������� uuid �� ��������� ��������
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

	public Contact updateContact(Contact contact) {
		String name = idsNames.get(contact.getId());
		contactsTrie.remove(name);
		contactsTrie.put(contact.getName(), contact);
		idsNames.remove(contact.getId());
		idsNames.put(contact.getId(), contact.getName());
		return contact;
	}

	public Contact getContact(String id) {
		String name = idsNames.get(id);
		return contactsTrie.get(name);
	}

	public Boolean deleteContact(String id) {
		
		return null;
	}

	public HashMap<String, Contact> getContacts() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Contact> deleteContacts(ArrayList<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}


}