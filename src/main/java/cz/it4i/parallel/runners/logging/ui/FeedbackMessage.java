
package cz.it4i.parallel.runners.logging.ui;

public class FeedbackMessage {

	private boolean windowIsOpen;
	private String jobId;

	public FeedbackMessage(boolean isOpen) {
		windowIsOpen = isOpen;
		this.jobId = "Single Job";
	}
	
	public FeedbackMessage(boolean isOpen, String jobId) {
		windowIsOpen = isOpen;
		this.jobId = jobId;
	}

	public boolean getWindowIsOpen() {
		return windowIsOpen;
	}
	
	public String getJobId() {
		return this.jobId;
	}

}
