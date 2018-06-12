package com.my.gwt.project.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.InstanceActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

public class SimpleDispatchService extends RemoteServiceServlet implements StandardDispatchService {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4548169495771600034L;
	private Dispatch dispatch;

    public SimpleDispatchService() {

        InstanceActionHandlerRegistry registry = new DefaultActionHandlerRegistry();
        registry.addHandler(new CreateContactHandler());
        registry.addHandler(new RemoveContactHandler());
        dispatch = new SimpleDispatch(registry);
    }

    public Result execute(Action<?> action) throws DispatchException {

        try {
            return dispatch.execute(action);
        } catch ( RuntimeException e) {
            log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw e;
        }
    }
}
