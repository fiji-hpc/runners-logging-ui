
package cz.it4i.parallel.ui;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class RedirectedOutput {

	// Type of stream:
	public enum StreamType {
			OUTPUT, ERROR
	}

	private static AtomicReference<String> output = new AtomicReference<>("Start of output redirection.\n");

	private static AtomicReference<String> error = new AtomicReference<>("Start of error redirection.\n");

	private String alreadyDisplayedOutput;

	private String alreadyDisplayedError;

	public RedirectedOutput() {
		this.alreadyDisplayedOutput = "";
		this.alreadyDisplayedError = "";
	}

	public static synchronized void appendTo(StreamType streamType,
		byte[] buffer)
	{
		if (streamType == StreamType.OUTPUT) {
			output.set(output.get() + new String(buffer));
		}
		else {
			error.set(error.get() + new String(buffer));
		}
	}

	// Get all output:
	public static synchronized String getOutput() {
		return output.get();
	}

	// Get all error:
	public static synchronized String getError() {
		return error.get();
	}

	public synchronized String getNewOutput() {
		// Get only the new text that has not already been displayed:
		String newOutput = output.get().replaceFirst(Pattern.quote(
			this.alreadyDisplayedOutput), "");
		this.alreadyDisplayedOutput = output.get();
		return newOutput;
	}

	public synchronized String getNewError() {
		// Get only the new text that has not already been displayed:
		String newError = error.get().replaceFirst(Pattern.quote(
			this.alreadyDisplayedError), "");
		this.alreadyDisplayedError = error.get();
		return newError;
	}

}
