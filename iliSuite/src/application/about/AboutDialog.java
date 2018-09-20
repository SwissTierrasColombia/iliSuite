package application.about;

import java.io.IOException;
import java.util.ResourceBundle;

import application.util.navigation.EnumPaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class AboutDialog extends Dialog<ButtonType>{
	//ButtonType
	public AboutDialog() throws IOException {
		loadContent();
		
		// TODO botones por parametros??
		this.getDialogPane().getButtonTypes().add(ButtonType.OK);
		
		this.setResultConverter(new Callback<ButtonType, ButtonType>() {
			@Override
			public ButtonType call(ButtonType b) {
				if (b == ButtonType.OK) {
					return b;
				}

				return null;
			}
		});
	}
	
	private void loadContent() throws IOException{
		ResourceBundle resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		FXMLLoader loader = new FXMLLoader(AboutDialog.class.getResource("aboutDialog.fxml"),resourceBundle);
		loader.setController(this);
		BorderPane page;

		page = (BorderPane) loader.load();

		// TODO obtiene el recurso visual. Poner en cdigo?
		this.getDialogPane().setContent(page);
		
		
	}
}
