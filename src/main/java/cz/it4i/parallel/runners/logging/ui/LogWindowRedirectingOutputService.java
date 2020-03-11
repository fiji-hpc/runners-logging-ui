/*******************************************************************************
 * IT4Innovations - National Supercomputing Center
 * Copyright (c) 2017 - 2019 All Right Reserved, https://www.it4i.cz
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE', which is part of this project.
 ******************************************************************************/

package cz.it4i.parallel.runners.logging.ui;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;
import org.scijava.service.Service;

@Plugin(type = Service.class, priority = Priority.HIGH)
public class LogWindowRedirectingOutputService extends
	BaseRedirectingOutputService implements RedirectedOutputService
{

	private EventBus eventBus = new EventBus("redirectedOutputBus");

	private PEventBusAdapter adapter = new PEventBusAdapter();

	@Override
	public void startAcceptOutput() {
		RedirectedOutputScreenWindow redirectedOutputScreenWindow =
			new RedirectedOutputScreenWindow();
		this.getContext().inject(redirectedOutputScreenWindow);
		if (!redirectedOutputScreenWindow.windowIsAlreadyOpen()) {
			redirectedOutputScreenWindow.run();
		}
	}

	@Override
	public void writeOutput(String output, OutputType outputType, String jobId) {
		eventBus.post(new EventMessage(outputType, output, jobId));
	}

	@Override
	public void writeOutput(String output, OutputType outputType) {
		eventBus.post(new EventMessage(outputType, output, "Single Job"));
	}

	@Override
	public void registerOutputSource(OutputSource outputSource) {
		if (outputSources.isEmpty()) {
			eventBus.register(adapter);
		}
		super.registerOutputSource(outputSource);
	}

	@Override
	public void unregisterOutputSource(OutputSource outputSource) {
		super.unregisterOutputSource(outputSource);
		if (outputSources.isEmpty()) {
			eventBus.unregister(adapter);
		}
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
		eventBus.post(event);
	}

	private class PEventBusAdapter {

		public PEventBusAdapter() {
			super();
		}

		/**
		 * @param deadEvent - internally not used
		 */
		@Subscribe
		public void handleNotListening(DeadEvent deadEvent) {
			outputSources.forEach(s -> s.statusOfOutputChanged(false));
		}

		/**
		 * @param message - internally not used
		 */
		@Subscribe
		public void handleListening(FeedbackMessage message) {
			outputSources.forEach(s -> s.statusOfOutputChanged(message
				.getWindowIsOpen()));
		}

	}
}
