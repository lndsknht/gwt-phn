package com.my.gwt.project.client;

import com.my.gwt.project.client.ContactInfo;

public class ContactInfo implements Comparable<ContactInfo>
{	
	private String contactName;
	private String phoneNumber;
	
	public ContactInfo(String contactName, String phoneNumber) 
	{
		super();
		this.contactName = contactName;
		this.phoneNumber = phoneNumber;
	}

	private String getContactName() 
	{
		return contactName;
	}

	private void setContactName(String contactName) 
	{
		this.contactName = contactName;
	}

	private String getPhoneNumber() 
	{
		return phoneNumber;
	}

	private void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int compareTo(ContactInfo o) 
	{
		ContactInfo contact = (ContactInfo) o;
        int result = contactName.compareTo(contact.contactName);
        if(result != 0)
        {
            return result;
        }
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof ContactInfo)
		{
			ContactInfo cont = (ContactInfo) o;	
			return cont.contactName.equals(contactName) && cont.phoneNumber.equals(phoneNumber);
		}			
		return false;
	}
}
