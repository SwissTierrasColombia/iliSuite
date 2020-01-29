package ai.ilisuite.menu.dialog;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import org.interlis2.validator.Main;

import ai.ilisuite.impl.DbDescription;
import ai.ilisuite.impl.ImplFactory;
import ai.ilisuite.util.plugin.PluginsLoader;
import ai.ilisuite.view.util.navigation.EnumPaths;

public class AboutDialog extends Dialog<ButtonType> implements Initializable {
	//ButtonType
	
	@FXML
	TextArea txt_version;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String ls = System.getProperty("line.separator");
		
		String vtxt = arg1.getString("general.versionText");
		String templateAppDescription = "%s " + ls + vtxt + " %s" + ls + ls;
		
		Map<String, ImplFactory> lstPlugin = PluginsLoader.getPlugins();

		String iliSuiteInfo = String.format(templateAppDescription,
				arg1.getString("general.appName"), arg1.getString("general.version"));
		
		String umlEditor = String.format(templateAppDescription,
				arg1.getString("general.umlEditorName"), arg1.getString("general.umlEditorVersion"));
		
		String iliValidatorInfo = 
				String.format(templateAppDescription, Main.APP_NAME, Main.getVersion());
		
		String infoPlugin = "";
		
		infoPlugin += iliSuiteInfo;
		
		infoPlugin += arg1.getString("dialog.about.otherProjects")+ ls + ls;
		
		infoPlugin += umlEditor;
		
		for (Entry<String, ImplFactory> item : lstPlugin.entrySet()) {
			if (item.getValue() instanceof ImplFactory) {
				DbDescription dbDesc = item.getValue().getDbDescription();
				
				infoPlugin += 
					String.format(templateAppDescription,
							dbDesc.getAppName(), dbDesc.getAppVersion());
			}
		}
		
		infoPlugin += iliValidatorInfo;
		
		txt_version.setText(infoPlugin);
	}
	
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
