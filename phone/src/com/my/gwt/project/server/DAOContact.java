package com.my.gwt.project.server;

import static com.my.gwt.project.server.FieldValidator.isValidName;
import static com.my.gwt.project.server.FieldValidator.isValidNumber;

import java.util.HashSet;

public class DAOContact {
	
	private static HashSet<ContactInfo> contacts = new HashSet<>();

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
	private static void addContact(String contactName, String phoneNumber) 
	{
		String noramlizedNum = normalizeNum(phoneNumber);
		if (isValidName(contactName) && isValidNumber(noramlizedNum))
		{
			contacts.add(new ContactInfo(contactName, noramlizedNum));
		}
		else
		{
			throw new IllegalArgumentException(String.format("������� � ������ %s � ������� %s �� ����� ���� ��������!", contactName, phoneNumber));
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
	private static String normalizeNum(String phoneNumber)
	{
		return phoneNumber.replaceAll("[^0-9]", "");		
	}
}
