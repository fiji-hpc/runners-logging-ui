
package cz.it4i.parallel.ui;

import java.util.regex.Pattern;

public class RedirectedOutput {

	// Type of stream:
	public enum StreamType {
			OUTPUT, ERROR
	}

	private static String output = "Start of output redirection:\n";

	private static String error = "Start of error redirection:\n";

	private String alreadyDisplayedOutput;

	private String alreadyDisplayedError;

	public RedirectedOutput() {
		this.alreadyDisplayedOutput = "";
		this.alreadyDisplayedError = "";
	}

	public synchronized static void appendTo(StreamType streamType, byte[] buffer) {
		if (streamType == StreamType.OUTPUT) {
			output += new String(buffer) + "\n";
		}
		else {
			error += new String(buffer) + "\n";
		}
	}

	// Get all output:
	public synchronized static String getOutput() {
		return output;
	}

	// Get all error:
	public synchronized static String getError() {
		return error;
	}

	public synchronized String getNewOutput() {
		// Get only the new text that has not already been displayed:
		String newOutput = output.replaceFirst(Pattern.quote(
			this.alreadyDisplayedOutput), "");
		this.alreadyDisplayedOutput = output;
		return newOutput;
	}

	public synchronized String getNewError() {
		// Get only the new text that has not already been displayed:
		String newError = error.replaceFirst(Pattern.quote(
			this.alreadyDisplayedError), "");
		this.alreadyDisplayedError = error;
		return newError;
	}

}
