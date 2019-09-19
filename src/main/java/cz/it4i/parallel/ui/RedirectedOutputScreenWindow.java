
package cz.it4i.parallel.ui;

import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

import cz.it4i.swing_javafx_ui.JavaFXRoutines;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

@Plugin(type = Command.class,
	menuPath = "Plugins>Scijava parallel>Redirected Output Viewer")
public class RedirectedOutputScreenWindow implements Command {

	private Window owner;

	public void setOwner(Window parentWindow) {
		this.owner = parentWindow;
	}

	public void openWindow() {
		// Create controller:
		RedirectedOutputScreenController controller =
			new RedirectedOutputScreenController();

		final Scene formScene = new Scene(controller);
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.setTitle("Redirected Output");
		stage.setScene(formScene);
		stage.initOwner(owner);

		controller.initialize();
		
		stage.showAndWait();
	}

	@Override
	public void run() {
		RedirectedOutputScreenWindow redirectedOutputScreenWindow =
			new RedirectedOutputScreenWindow();
		JavaFXRoutines.runOnFxThread(redirectedOutputScreenWindow::openWindow);
	}
}
