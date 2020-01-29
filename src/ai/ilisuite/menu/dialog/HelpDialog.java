package ai.ilisuite.menu.dialog;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.ilisuite.view.util.navigation.EnumPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpDialog extends Dialog<Boolean> implements Initializable {

	@FXML
	private WebView wv_page;
	
	@FXML
	private Button btnHome;
	
	WebEngine engine;
	
	public HelpDialog() throws IOException {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		FXMLLoader loader = new FXMLLoader(HelpDialog.class.getResource("helpDialog.fxml"), resourceBundle);
		
		loader.setController(this);

		BorderPane page = (BorderPane) loader.load();
		this.getDialogPane().setContent(page);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.getDialogPane().getButtonTypes().setAll(ButtonType.CLOSE);
		engine = wv_page.getEngine();
		
		File file = new File("./help/html/index.html");
		URL url;
		try {
			url = file.toURI().toURL();
			//engine.
			engine.load(url.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void goBack() {

		engine.executeScript("history.back()");
	}

	public void goForward() {
		engine.executeScript("history.forward()");
	}
	
	@FXML
	public void onClickBtnHome(ActionEvent event) throws IOException {
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
