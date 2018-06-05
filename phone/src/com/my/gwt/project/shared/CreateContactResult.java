package com.my.gwt.project.shared;

import java.util.HashMap;

import net.customware.gwt.dispatch.shared.Result;

public class CreateContactResult implements Result 
{
//	private HashMap<String, String> contactToAdd;
	private String name;
	private String phone;
	private HashMap<String, String> currentContacts;
	
	 /** For serialization only. */
	CreateContactResult() 
	{
	}
	
	public CreateContactResult(String name, String phone, HashMap<String, String> currentContacts) 
	{
		this.name = name;
		this.phone = phone;
		this.currentContacts = currentContacts;
	}

	public HashMap<String, String> getCurrentContacts() 
	{
		return currentContacts;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}
	
	
}
