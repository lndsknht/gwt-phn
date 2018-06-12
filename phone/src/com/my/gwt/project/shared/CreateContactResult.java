package com.my.gwt.project.shared;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class CreateContactResult implements Result 
{
	private String addedName;
	private String addedPhone;
	private ArrayList<String> currentName;
	private ArrayList<String> currentPhone;
	
	 /** For serialization only. */
	CreateContactResult() 
	{
	}
	
	public CreateContactResult(String addedName, String addedPhone, ArrayList<String> currentNames, ArrayList<String> currentPhones) 
	{
		this.addedName = addedName;
		this.addedPhone = addedPhone;
		this.currentName = currentNames;
		this.currentPhone = currentPhones;
	}

	public ArrayList<String> getCurrentName() 
	{
		return currentName;
	}
	
	public ArrayList<String> getCurrentPhone()
	{
		return currentPhone;
	}

	public String getAddedName() {
		return addedName;
	}

	public String getAddedPhone() {
		return addedPhone;
	}
}
