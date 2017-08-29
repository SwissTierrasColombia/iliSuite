package application.dialog;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import application.util.navigation.EnumPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpDialog extends Dialog<Boolean> implements Initializable {

	@FXML
	private WebView wv_page;
	
	public HelpDialog() throws IOException {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		FXMLLoader loader = new FXMLLoader(HelpDialog.class.getResource("helpDialog.fxml"), resourceBundle);
		
		loader.setController(this);

		BorderPane page = (BorderPane) loader.load();
		this.getDialogPane().setContent(page);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		final WebEngine engine = wv_page.getEngine();
		
		File file = new File("./help/html/index.html");
		URL url;
		try {
			url = file.toURI().toURL();
			engine.load(url.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
