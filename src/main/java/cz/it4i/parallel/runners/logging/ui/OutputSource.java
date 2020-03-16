
package cz.it4i.parallel.runners.logging.ui;

import java.util.function.Consumer;

public class OutputSource {

	private String jobId = "Single Job";

	private Consumer<Boolean> method;

	public OutputSource(String jobId, Consumer<Boolean> method) {
		this.jobId = jobId;
		this.method = method;
	}

	public void statusOfOutputChanged(boolean windowIsOpen) {
		method.accept(windowIsOpen);
	}

	public String getJobId() {
		return this.jobId;
	}

}
