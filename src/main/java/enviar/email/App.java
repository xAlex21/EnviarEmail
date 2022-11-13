package enviar.email;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private EmailController controller;

	@Override
	public void start(Stage stage) throws Exception {

		controller = new EmailController();

		stage.setScene(new Scene(controller.getView()));
		stage.setTitle("CalculadoraFXML");
		stage.sizeToScene();
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}
