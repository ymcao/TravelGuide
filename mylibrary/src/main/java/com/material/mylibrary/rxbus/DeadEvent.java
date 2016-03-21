/**
 * 
 */
package com.material.mylibrary.rxbus;

/**
 * EventBus不处理DeadEvent
 *
 */
public class DeadEvent {

	public final Object source;
	public final Object event;

	public DeadEvent(Object source, Object event) {
		this.source = source;
		this.event = event;
	}
}
