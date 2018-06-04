package com.my.gwt.project.server;

import com.my.gwt.project.shared.AddContactAct;
import com.my.gwt.project.shared.AddContactActResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class AddContactActHandler implements ActionHandler<AddContactAct, AddContactActResult> {

	@Override
	public AddContactActResult execute(AddContactAct arg0, ExecutionContext arg1) throws DispatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<AddContactAct> getActionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rollback(AddContactAct arg0, AddContactActResult arg1, ExecutionContext arg2) throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
