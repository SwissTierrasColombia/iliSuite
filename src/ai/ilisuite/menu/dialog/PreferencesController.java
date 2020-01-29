package ai.ilisuite.menu.dialog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.ilisuite.application.data.Config;
import ai.ilisuite.view.util.navigation.EnumPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
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
	
	@FXML
	TextField txt_logDirPath;
	
	@FXML
	Button btn_logDirBrowse;
	
	private ResourceBundle resourceBundle;
	
	public PreferencesController()  throws IOException {
		resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
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
		
		txt_logDirPath.setText(config.getLogDir());
		
		txt_logDirPath.setDisable(!config.isLogEnabled());
		
		this.setResultConverter(new Callback<ButtonType, Boolean>() {
			@Override
			public Boolean call(ButtonType b) {
				if (b == ButtonType.OK) {
					config.setTraceEnabled(chk_traceEnable.isSelected());
					
					if(grp_language.getSelectedToggle() != null) {
						config.setLanguage(grp_language.getSelectedToggle().getUserData().toString());
					}
					
					config.setLogEnabled(chk_logEnabled.isSelected());
					// TODO Check if directory exist
					if(chk_logEnabled.isSelected()) {
						config.setLogDir(txt_logDirPath.getText());
					}
					try {
						config.saveToFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}
	
	public void onClick_btn_logDirBrowse(ActionEvent event) throws IOException {
		Node eventSource = (Node) event.getSource();
		Window ownerWindow = eventSource.getScene().getWindow();

		DirectoryChooser directoryChooser = new DirectoryChooser();

		directoryChooser.setTitle(resourceBundle.getString("dialog.preferences.title"));

		File dir = new File(txt_logDirPath.getText());
		
		if(dir.isDirectory() && dir.exists()) {
			directoryChooser.setInitialDirectory(dir);
		}
		
		File selectedDirectory = directoryChooser.showDialog(ownerWindow);

		if (selectedDirectory != null) {
			txt_logDirPath.setText(selectedDirectory.getAbsolutePath());
		}
	}
	
	@FXML
	void onAction_chk_logEnabled(ActionEvent e){
		txt_logDirPath.setDisable(!chk_logEnabled.isSelected());
		btn_logDirBrowse.setDisable(!chk_logEnabled.isSelected());
	}
}
