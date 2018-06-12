package com.my.gwt.project.shared;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class RemoveContactResult implements Result 
{
	private String removedName;
	private Integer removedNamePosition;
	private ArrayList<String> currentNames;
	private ArrayList<String> currentPhones;
	
	 /** For serialization only. */
	public RemoveContactResult()
	{
	}
	
	public RemoveContactResult(String removedName, Integer removedNamePosition, ArrayList<String> currentNames, ArrayList<String> currentPhones) {
		this.removedName = removedName;
		this.currentNames = currentNames;
		this.currentPhones = currentPhones;
	}

	public String getRemovedName() 
	{
		return removedName;
	}
	
	public Integer getRemovedNamePosition()
	{
		return removedNamePosition;
	}

	public ArrayList<String> getCurrentNames() 
	{
		return currentNames;
	}

	public ArrayList<String> getCurrentPhones() 
	{
		return currentPhones;
	}
}
