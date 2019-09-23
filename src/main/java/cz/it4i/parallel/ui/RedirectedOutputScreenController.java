
package cz.it4i.parallel.ui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cz.it4i.swing_javafx_ui.JavaFXRoutines;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class RedirectedOutputScreenController extends AnchorPane {

	@FXML
	TextArea outputTextArea;

	@FXML
	TextArea errorTextArea;

	private ScheduledExecutorService executor;

	private RedirectedOutput redirectedOutput;

	public RedirectedOutputScreenController() {
		JavaFXRoutines.initRootAndController("redirected-output-screen.fxml", this);
		redirectedOutput = new RedirectedOutput();
		updateOutputAndErrorPeriodically();
	}

	public void initialize() {
		outputTextArea.setEditable(false);
		errorTextArea.setEditable(false);
	}

	private synchronized void updateOutputAndErrorPeriodically() {
		Runnable updateStandardOutputAndErrorsRunnable = () -> {
			JavaFXRoutines.runOnFxThread(() -> {
				// Append any new output:
				String newOutput = redirectedOutput.getNewOutput();
				if (!newOutput.isEmpty()) {
					outputTextArea.appendText(newOutput);
				}

				// Append any new error:
				String newError = redirectedOutput.getNewError();
				if (!newError.isEmpty()) {
					errorTextArea.appendText(newError);
				}

			});
		};

		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(updateStandardOutputAndErrorsRunnable, 0, 3,
			TimeUnit.SECONDS);
	}

	public void close() {
		executor.shutdown();
	}
}
