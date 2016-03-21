/**
 * 
 */
package com.material.mylibrary.rxbus;

import java.util.Map;
import java.util.Set;

public class EventBusUtils {

	/**
	 * 查找Producer
	 * @param listener
	 * @return
	 */
	public static Map<Class<?>, EventProducer> findAllProducers(Object listener) {
		return AnnotatedFinder.findAllProducers(listener);
	}
	
	/**
	 * 查找Subscriber
	 * @param listener
	 * @return
	 */
    public static Map<Class<?>, Set<EventHandler>> findAllSubscribers(Object listener) {
        return AnnotatedFinder.findAllSubscribers(listener);
      }
}
