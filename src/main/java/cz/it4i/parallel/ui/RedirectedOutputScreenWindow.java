
package cz.it4i.parallel.ui;

import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

import cz.it4i.swing_javafx_ui.JavaFXRoutines;
import cz.it4i.swing_javafx_ui.SimpleDialog;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

@Plugin(type = Command.class,
	menuPath = "Plugins>Scijava parallel>Redirected Output Viewer")
public class RedirectedOutputScreenWindow implements Command {

	private Window owner = null;

	private Stage stage;

	private RedirectedOutputScreenController controller;

	private static boolean aWindowIsAlreadyOpen = false;

	public void setOwner(Window parentWindow) {
		this.owner = parentWindow;
	}

	public void openWindow() {
		// Create controller:
		this.controller = new RedirectedOutputScreenController();

		final Scene formScene = new Scene(this.controller);
		stage = new Stage();
		// This window should not be modal:
		stage.initModality(Modality.NONE);
		stage.setResizable(true);
		stage.setTitle("Redirected Output");
		stage.setScene(formScene);
		stage.initOwner(owner);

		this.controller.initialize();

		// Remember to stop the standard output and error update on closing the
		// stage:
		finalizeOnStageClose();

		stage.showAndWait();
	}

	@Override
	public void run() {
		if (!aWindowIsAlreadyOpen) {
			RedirectedOutputScreenWindow redirectedOutputScreenWindow =
				new RedirectedOutputScreenWindow();
			JavaFXRoutines.runOnFxThread(redirectedOutputScreenWindow::openWindow);
			aWindowIsAlreadyOpen = true;
		}
		else {
			JavaFXRoutines.runOnFxThread(() -> SimpleDialog.showInformation(
				"A redirected output window is already open!",
				"There can be only one redirected ouput window open."));
		}
	}

	private void finalizeOnStageClose() {
		// On close dispose executor:
		this.stage.setOnCloseRequest((WindowEvent we) -> {
			this.controller.close();
			aWindowIsAlreadyOpen = false;
		});

	}
}
