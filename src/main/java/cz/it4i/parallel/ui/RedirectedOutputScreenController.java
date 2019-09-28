
package cz.it4i.parallel.ui;

import cz.it4i.parallel.ui.EventMessage.StreamType;
import cz.it4i.swing_javafx_ui.JavaFXRoutines;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import com.google.common.eventbus.Subscribe;

public class RedirectedOutputScreenController extends AnchorPane {

	@FXML
	TextArea outputTextArea;

	@FXML
	TextArea errorTextArea;

	public RedirectedOutputScreenController() {
		JavaFXRoutines.initRootAndController("redirected-output-screen.fxml", this);
		RedirectedOutput.eventBus.register(this);
	}

	public void initialize() {
		outputTextArea.setEditable(false);
		errorTextArea.setEditable(false);
	}

	@Subscribe
	public void handleEvent(final EventMessage eventMessage) {
		if (eventMessage.getMsgcode() == StreamType.OUTPUT) {
			outputTextArea.appendText(eventMessage.getMsg());
		}
		else {
			errorTextArea.appendText(eventMessage.getMsg());
		}
	}

	public void close() {
		RedirectedOutput.eventBus.unregister(this);
	}
}
