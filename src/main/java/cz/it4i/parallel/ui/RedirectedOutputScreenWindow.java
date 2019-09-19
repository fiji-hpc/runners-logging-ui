
package cz.it4i.parallel.ui;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

class RedirectedOutputScreenWindow {

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

		stage.showAndWait();
	}
}
