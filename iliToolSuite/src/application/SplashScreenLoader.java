package application;

import java.io.IOException;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Preloader.StateChangeNotification.Type;

public class SplashScreenLoader extends Preloader {

	private Stage splashScreen;

	@Override
	public void start(Stage stage) throws Exception {
		splashScreen = stage;

		stage.initStyle(StageStyle.UNDECORATED);

		splashScreen.setScene(createScene());
		splashScreen.show();

	}

	public Scene createScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(SplashScreenLoader.class.getResource("splashScreenLoader.fxml"));

		Parent root = (Parent) loader.load();

		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		return scene;
	}

	public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
		if (stateChangeNotification.getType() == Type.BEFORE_START) {
			splashScreen.hide();
		}
	}

}
