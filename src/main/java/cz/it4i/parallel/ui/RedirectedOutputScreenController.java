
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

	public RedirectedOutputScreenController() {
		JavaFXRoutines.initRootAndController("redirected-output-screen.fxml", this);
		update();
	}

	public void initialize() {
		outputTextArea.setEditable(false);
		errorTextArea.setEditable(false);
	}

	private void update() {
		Runnable updateStandardOutputAndErrorsRunnable = () -> {
			JavaFXRoutines.runOnFxThread(() -> outputTextArea.setText(RedirectedOutput
				.getOutput()));
			JavaFXRoutines.runOnFxThread(() -> errorTextArea.setText(RedirectedOutput
				.getError()));
		};

		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(updateStandardOutputAndErrorsRunnable, 0, 3,
			TimeUnit.SECONDS);
	}

	public void close() {
		executor.shutdown();
	}
}
