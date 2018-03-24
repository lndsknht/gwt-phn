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
	 * —оздать и добавить контакт в систему
	 * @param contactName им€ контакта
	 * @param phoneNumber номер контакта в свободной форме
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
			throw new IllegalArgumentException(String.format(" онтакт с именем %s и номером %s не может быть добавлен!", contactName, noramlizedNum));
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
	private String normalizeNum(String phoneNumber)
	{
		return phoneNumber.replaceAll("[^0-9]", "");		
	}
}
