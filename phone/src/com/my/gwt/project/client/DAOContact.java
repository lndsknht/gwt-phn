package com.my.gwt.project.client;

import java.util.HashSet;

import static com.my.gwt.project.shared.FieldValidator.isValidName;
import static com.my.gwt.project.shared.FieldValidator.isValidNumber;

public class DAOContact {
	
	private HashSet<ContactInfo> contacts = new HashSet<>();

	private HashSet<ContactInfo> getContacts() 
	{
		return contacts;
	}

	/**
	 * ������� � �������� ������� � �������
	 * @param contactName ��� ��������
	 * @param phoneNumber ����� �������� � ��������� �����
	 * @throws IllegalArgumentException
	 * */
	private void addContact(String contactName, String phoneNumber) 
	{
		String noramlizedNum = normalizeNum(phoneNumber);
		if (isValidName(contactName) && isValidNumber(noramlizedNum))
		{
			contacts.add(new ContactInfo(contactName, noramlizedNum));
		}
		else
		{
			throw new IllegalArgumentException(String.format("������� � ������ %s � ������� %s �� ����� ���� ��������!", contactName, noramlizedNum));
		}
	} 
	
	private ContactInfo getContact(String nameOrNumber)
	{
		
		return null;		
	}
	
	/**
	 * ������� ��� ������ ������� �� ������, ������� ������ �����
	 * @param phoneNumber ����� ��� ��������������
	 * @return ����������������� �����
	 * */
	private String normalizeNum(String phoneNumber)
	{
		return phoneNumber.replaceAll("[^0-9]", "");		
	}
}
