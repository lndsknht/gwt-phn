package com.my.gwt.project.shared;

import net.customware.gwt.dispatch.shared.Action;

public class CreateContact implements Action<CreateContactResult> 
{
	private String name;
	private String phone;
	
	 /** For serialization only. */
	CreateContact()
	{
	}
	
	public CreateContact(String name, String phone)
	{
		this.name = name;
		this.phone = phone;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
//	private ArrayList<String> name;
//	private ArrayList<String> phone;
//	
//	 /** For serialization only. */
//	CreateContact()
//	{
//	}
//	
//	public CreateContact(ArrayList<String> name, ArrayList<String> phone)
//	{
//		this.name = name;
//		this.phone = phone;
//	}
//	
//	public ArrayList<String> getName()
//	{
//		return name;
//	}
//	
//	public ArrayList<String> getPhone()
//	{
//		return phone;
//	}
}
