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
	 * —оздать и добавить контакт в систему
	 * @param contactName им€ контакта
	 * @param phoneNumber номер контакта в свободной форме
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
			throw new IllegalArgumentException(String.format(" онтакт с именем %s и номером %s не может быть добавлен!", contactName, phoneNumber));
		}
	} 
	
	private ContactInfo getContact(String nameOrNumber)
	{
		return null;		
	}
	
	/**
	 * ”далить все лишние символы из номера, оставив только цифры
	 * @param phoneNumber номер дл€ редактировани€
	 * @return отредактированный номер
	 * */
	private static String normalizeNum(String phoneNumber)
	{
		return phoneNumber.replaceAll("[^0-9]", "");		
	}
}
