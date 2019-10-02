
package cz.it4i.parallel.runners.logging.ui;

import com.google.common.eventbus.EventBus;

public class RedirectedOutputImpl implements RedirectedOutputService {

	private static RedirectedOutputImpl singleInstance = null;

	private EventBus eventBus = null;

	private RedirectedOutputImpl() {
		eventBus = new EventBus("redirectedOutputBus");
	}

	public static RedirectedOutputImpl getInstance() {
		if (singleInstance == null) {
			singleInstance = new RedirectedOutputImpl();
		}

		return singleInstance;
	}

	@Override
	public void register(Object object) {
		eventBus.register(object);
	}

	@Override
	public void unregister(Object object) {
		eventBus.unregister(object);
	}

	@Override
	public void post(Object event) {
		this.eventBus.post(event);
	}

}
