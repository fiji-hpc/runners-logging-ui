
package cz.it4i.parallel.runners.logging.ui;

import com.google.common.eventbus.Subscribe;

import cz.it4i.parallel.runners.RedirectingOutputService.OutputType;
import cz.it4i.parallel.ui.EventMessage;
import cz.it4i.parallel.ui.FeedbackMessage;
import cz.it4i.swing_javafx_ui.JavaFXRoutines;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class RedirectedOutputScreenController extends AnchorPane {

	@FXML
	TextArea outputTextArea;

	@FXML
	TextArea errorTextArea;

	private RedirectedOutputService redirectedOutput;


	public RedirectedOutputScreenController(
		RedirectedOutputService redirectedOutput)
	{
		this.redirectedOutput = redirectedOutput;
		JavaFXRoutines.initRootAndController("redirected-output-screen.fxml", this);
		redirectedOutput.register(this);
		redirectedOutput.post(new FeedbackMessage());
	}

	public void initialize() {
		outputTextArea.setEditable(false);
		errorTextArea.setEditable(false);
	}

	@Subscribe
	public void handleEvent(final EventMessage eventMessage) {
		if (eventMessage.getMsgcode() == OutputType.OUTPUT) {
			outputTextArea.appendText(eventMessage.getMsg());
		}
		else {
			errorTextArea.appendText(eventMessage.getMsg());
		}
	}

	public void close() {
		redirectedOutput.unregister(this);
	}
}
