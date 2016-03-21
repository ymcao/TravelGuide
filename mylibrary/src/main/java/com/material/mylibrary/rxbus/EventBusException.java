/**
 * 
 */
package com.material.mylibrary.rxbus;

import java.lang.reflect.InvocationTargetException;

public class EventBusException extends RuntimeException{

	private static final long serialVersionUID = 7663747274756461744L;

	protected EventBusException(InvocationTargetException cause) {
		super(cause);
	}
	
	protected EventBusException(String msg,InvocationTargetException cause) {
		if (cause.getCause() != null) {
			throw new RuntimeException(msg, cause);
		} else {
			throw new RuntimeException(msg);
		}
	}
}
