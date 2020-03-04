
package cz.it4i.parallel.runners.logging.ui;

import cz.it4i.parallel.runners.logging.ui.RedirectingOutputService.OutputType;

public class EventMessage {

//Type of stream:
	public enum StreamType {
			OUTPUT, ERROR
	}

	private final OutputType msgcode;
	private final String msg;
	private final String jobId;

	public EventMessage(OutputType msgcode, String msg, String jobId) {
		this.msgcode = msgcode;
		this.msg = msg;
		this.jobId = jobId;
	}

	public OutputType getMsgcode() {
		return msgcode;
	}

	public String getMsg() {
		return msg;
	}

	public String getJobId() {
		return jobId;
	}
}
