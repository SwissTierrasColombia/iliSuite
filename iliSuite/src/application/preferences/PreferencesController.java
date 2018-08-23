package application.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.data.Config;
import application.dialog.ModelDirDialog;
import application.util.navigation.EnumPaths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class PreferencesController extends Dialog<Boolean> implements Initializable {
	
	Button btnOk;
	
	@FXML
	ToggleGroup grp_language;
	
	@FXML
	CheckBox chk_traceEnable;
	
	@FXML
	CheckBox chk_logEnabled;
	
	@FXML
	RadioButton radio_languageEs;
	
	@FXML
	RadioButton radio_languageEn;
	
	@FXML
	RadioButton radio_languageDe;
	
	public PreferencesController()  throws IOException {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		FXMLLoader loader = new FXMLLoader(PreferencesController.class.getResource("preferences.fxml"), resourceBundle);

		loader.setController(this);

		BorderPane page = (BorderPane) loader.load();
		this.getDialogPane().setContent(page);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Config config = Config.getInstance();
		
		this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		this.getDialogPane().getButtonTypes().add(ButtonType.OK);
		
		btnOk = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
		
		// load
		chk_traceEnable.setSelected(config.isTraceEnabled());
		
		String language = config.getLanguage();
		
		if(language != null) {
			if(language.contains(radio_languageEn.getUserData().toString())) {
				radio_languageEn.setSelected(true);
			} else if(language.contains(radio_languageEs.getUserData().toString())) {
				radio_languageEs.setSelected(true);
			} else if(language.contains(radio_languageDe.getUserData().toString())) {
				radio_languageDe.setSelected(true);
			}
		}
		
		chk_logEnabled.setSelected(config.isLogEnabled());
		
		this.setResultConverter(new Callback<ButtonType, Boolean>() {
			@Override
			public Boolean call(ButtonType b) {
				if (b == ButtonType.OK) {
					
					config.setTraceEnabled(chk_traceEnable.isSelected());
					
					config.setLogEnabled(chk_logEnabled.isSelected());
					
					if(grp_language.getSelectedToggle() != null) {
						config.setLanguage(grp_language.getSelectedToggle().getUserData().toString());
					}
					
					try {
						Config.saveConfig(new File(".config.properties"), config);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}
}
