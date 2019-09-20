
package cz.it4i.parallel.ui;

public class RedirectedOutput {

	// Type of stream:
	public enum StreamType {
	  OUTPUT,
	  ERROR
	}
	
	private RedirectedOutput() {
		// Private constructor to hide the implicit public one.
	}

	private static String output = "Start of output redirection:\n";

	private static String error = "Start of error redirection:\n";

	public static void appendTo(StreamType streamType, byte[] buffer) {
		if(streamType == StreamType.OUTPUT) {
			output += new String(buffer)+"\n";
		} else {
			error += new String(buffer)+"\n";
		}
	}

	public static String getOutput() {
		return output;
	}

	public static String getError() {
		return error;
	}

}
