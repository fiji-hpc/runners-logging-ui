
package cz.it4i.parallel.ui;

import java.util.Arrays;

public class RedirectedOutput {

	private RedirectedOutput() {
		// Private constructor to hide the implicit public one.
	}

	private static String output = "Start of output redirection:\n";

	private static String error = "Start of error redirection:\n";

	public static void write(byte[] buffer) {
		output += Arrays.toString(buffer);
	}

	public static String getOutput() {
		return output;
	}

	public static String getError() {
		return error;
	}

}
