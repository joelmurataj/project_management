package com.project.utility;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class CustomExceptionHandler extends ExceptionHandlerWrapper{

	private ExceptionHandler wrapped;
	 
	  
	  public CustomExceptionHandler(ExceptionHandler wrapped) {
	    this.wrapped = wrapped;
	  }
	 
	  @Override
	  public ExceptionHandler getWrapped() {
	    return wrapped;
	  }

	  @Override
	  public void handle() throws FacesException {
		  final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
	        while (i.hasNext()) {
	            ExceptionQueuedEvent event = i.next();
	            ExceptionQueuedEventContext context =
	                    (ExceptionQueuedEventContext) event.getSource();
	 
	            // get the exception from context
	            Throwable t = context.getException();
	 
	            final FacesContext fc = FacesContext.getCurrentInstance();
	            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
	            final NavigationHandler nav = fc.getApplication().getNavigationHandler();
	 
	            //here you do what ever you want with exception
	            try {
	 
	                //redirect error page
	                requestMap.put("exceptionMessage", t.getMessage());
	                nav.handleNavigation(fc, null, "/error.xhtml");
	                fc.renderResponse();
	 
	                // remove the comment below if you want to report the error in a jsf error message
	                //JsfUtil.addErrorMessage(t.getMessage());
	 
	            } finally {
	                //remove it from queue
	                i.remove();
	            }
	        }
	        //parent hanle
	        getWrapped().handle();
		  }
}
