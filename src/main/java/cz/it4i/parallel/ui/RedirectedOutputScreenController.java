
package cz.it4i.parallel.ui;

import cz.it4i.swing_javafx_ui.CloseableControl;
import cz.it4i.swing_javafx_ui.JavaFXRoutines;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class RedirectedOutputScreenController extends Pane implements
	CloseableControl
{

	@FXML
	TextArea outputTextArea;

	@FXML
	TextArea errorTextArea;

	public RedirectedOutputScreenController() {
		JavaFXRoutines.initRootAndController("redirected-output-screen.fxml", this);
	}

	@Override
	public void close() {
		// do nothing
	}
}
