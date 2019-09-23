
package cz.it4i.parallel.ui;

import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class RedirectedOutput {

	// Type of stream:
	public enum StreamType {
			OUTPUT, ERROR
	}

	private static ReentrantLock lock = new ReentrantLock();

	private static String output = "Start of output redirection.\n";

	private static String error = "Start of error redirection.\n";

	private String alreadyDisplayedOutput;

	private String alreadyDisplayedError;

	public RedirectedOutput() {
		this.alreadyDisplayedOutput = "";
		this.alreadyDisplayedError = "";
	}

	public static void appendTo(StreamType streamType, byte[] buffer,
		int length)
	{
		lock.lock();
		if (streamType == StreamType.OUTPUT) {
			output += new String(buffer, 0, length);
		}
		else {
			error += new String(buffer, 0, length);
		}
		lock.unlock();
	}

	public String getNewOutput() {
		lock.lock();
		// Get only the new text that has not already been displayed:
		String newOutput = output.replaceFirst(Pattern.quote(
			this.alreadyDisplayedOutput), "");
		this.alreadyDisplayedOutput = output;
		lock.unlock();
		return newOutput;
	}

	public String getNewError() {
		lock.lock();
		// Get only the new text that has not already been displayed:
		String newError = error.replaceFirst(Pattern.quote(
			this.alreadyDisplayedError), "");
		this.alreadyDisplayedError = error;
		lock.unlock();
		return newError;
	}

}
