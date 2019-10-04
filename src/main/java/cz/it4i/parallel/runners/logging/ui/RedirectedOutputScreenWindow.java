
package cz.it4i.parallel.runners.logging.ui;

import java.util.concurrent.atomic.AtomicReference;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import cz.it4i.parallel.runners.RedirectingOutputService;
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

	@Parameter
	private RedirectingOutputService redirectingOutputService;

	private Window owner = null;

	private Stage stage;

	private RedirectedOutputScreenController controller;

	private static AtomicReference<Boolean> aWindowIsAlreadyOpen =
		new AtomicReference<>(false);

	public void setOwner(Window parentWindow) {
		this.owner = parentWindow;
	}

	public void openWindow() {
		// Create controller:
		this.controller = new RedirectedOutputScreenController(
			(RedirectedOutputService) redirectingOutputService);

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

		stage.show();
	}

	@Override
	public void run() {
		if (!(redirectingOutputService instanceof RedirectedOutputService)) {
			JavaFXRoutines.runOnFxThread(() -> SimpleDialog.showWarning(
				"Cannot open",
				"There is registered another type RedirectingOutputService!"));
		}
		if (!aWindowIsAlreadyOpen.get()) {
			RedirectedOutputScreenWindow redirectedOutputScreenWindow =
				new RedirectedOutputScreenWindow();
			redirectingOutputService.context().inject(redirectedOutputScreenWindow);
			JavaFXRoutines.runOnFxThread(redirectedOutputScreenWindow::openWindow);
			aWindowIsAlreadyOpen.set(true);
		}
		else {
			JavaFXRoutines.runOnFxThread(() -> SimpleDialog.showInformation(
				"A redirected output window is already open!",
				"There can be only one redirected ouput window open."));
		}
	}
	
	public boolean windowIsAlreadyOpen() {
		return aWindowIsAlreadyOpen.get();
	}

	private void finalizeOnStageClose() {
		// On close dispose executor:
		this.stage.setOnCloseRequest((WindowEvent we) -> {
			this.controller.close();
			aWindowIsAlreadyOpen.set(false);
		});

	}
}
