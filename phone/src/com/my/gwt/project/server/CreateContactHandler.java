package com.my.gwt.project.server;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.my.gwt.project.shared.CreateContact;
import com.my.gwt.project.shared.CreateContactResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class CreateContactHandler implements ActionHandler<CreateContact, CreateContactResult> 
{
	private ArrayList<String> currentNames;
	private ArrayList<String> currentPhones;
	
	public Class<CreateContact> getActionType() 
	{
		return CreateContact.class;
	}

	public synchronized CreateContactResult execute(CreateContact action, ExecutionContext context) throws DispatchException 
	{
		currentNames.add(action.getName());
		currentPhones.add(action.getPhone());
		return new CreateContactResult(Lists.newArrayList(action.getName()), Lists.newArrayList(action.getPhone()), currentNames, currentPhones);
	}

	public synchronized void rollback(CreateContact action, CreateContactResult result, ExecutionContext context) throws DispatchException 
	{
		currentNames.removeAll(Lists.newArrayList(action.getName()));
		currentPhones.removeAll(Lists.newArrayList(action.getPhone()));
	}
}
