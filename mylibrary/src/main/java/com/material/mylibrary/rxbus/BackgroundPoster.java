/**
 * 
 */
package com.material.mylibrary.rxbus;

import java.util.concurrent.Callable;

public class BackgroundPoster {

	private final EventBus eventBus;

	BackgroundPoster(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void enqueue(final Object event,final EventHandler subscription) {		
		EventBus.executorService.submit(new Callable<Void>(){
			public Void call() throws Exception {
				eventBus.invokeSubscriber(event,subscription);
				return null;
            }    
		});
	}

}
