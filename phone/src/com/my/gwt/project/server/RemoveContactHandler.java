package com.my.gwt.project.server;

import java.util.ArrayList;

import com.my.gwt.project.shared.RemoveContact;
import com.my.gwt.project.shared.RemoveContactResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class RemoveContactHandler implements ActionHandler<RemoveContact, RemoveContactResult>
{
	private ArrayList<String> currentNames;
	private ArrayList<String> currentPhones;
	
	public Class<RemoveContact> getActionType() 
	{
		return RemoveContact.class;
	}

	public RemoveContactResult execute(RemoveContact action, ExecutionContext context) throws DispatchException 
	{
		int position = currentNames.indexOf(action.getName());
		currentNames.remove(position);
		currentPhones.remove(position);
		return new RemoveContactResult(action.getName(), position, currentNames, currentPhones);
	}

	public void rollback(RemoveContact action, RemoveContactResult result, ExecutionContext context) throws DispatchException 
	{
		currentNames.add(action.getName());
		currentPhones.add(action.getPhone());
	}
}
