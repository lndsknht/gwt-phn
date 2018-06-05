package com.my.gwt.project.server;

import java.util.HashMap;

import com.my.gwt.project.shared.CreateContact;
import com.my.gwt.project.shared.CreateContactResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
//TODO нужно переделать все основные экшн\резалт\хендлер классы с учётом того, что все данные в Phone хранятся в ДВУХ листах
public class CreateContactHandler implements ActionHandler<CreateContact, CreateContactResult> 
{
	private HashMap<String, String> currentContacts;
	
	public Class<CreateContact> getActionType() 
	{
		return CreateContact.class;
	}

	public synchronized CreateContactResult execute(CreateContact action, ExecutionContext context) throws DispatchException 
	{
//		currentContacts.put(action., value)
		return null;
	}

	public synchronized void rollback(CreateContact action, CreateContactResult result, ExecutionContext context) throws DispatchException 
	{
		
	}
}
