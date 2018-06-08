package com.my.gwt.project.shared;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class CreateContactResult implements Result 
{
	private ArrayList<String> addedName;
	private ArrayList<String> addedPhone;
	private ArrayList<String> currentName;
	private ArrayList<String> currentPhone;
	
	 /** For serialization only. */
	CreateContactResult() 
	{
	}
	
	public CreateContactResult(ArrayList<String> addedName, ArrayList<String> addedPhone, ArrayList<String> currentNames, ArrayList<String> currentPhones) 
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

	public ArrayList<String> getName() {
		return addedName;
	}

	public ArrayList<String> getPhone() {
		return addedPhone;
	}
}
