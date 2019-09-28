
package cz.it4i.parallel.ui;

import com.google.common.eventbus.EventBus;

public class RedirectedOutput {

	public static final EventBus eventBus = new EventBus("redirectedOutputBus");
}
