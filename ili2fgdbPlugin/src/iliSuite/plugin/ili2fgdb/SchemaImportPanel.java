package iliSuite.plugin.ili2fgdb;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import base.PanelCustomizable;
import iliSuite.plugin.ili2fgdb.view.SchemaImportPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SchemaImportPanel implements PanelCustomizable{
	
	private SchemaImportPanelController customPanel;
	
	@Override
	public Parent getPanel() {
		Parent cp = null;
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2fgdb.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2fgdbPlugin.class.getResource("/iliSuite/plugin/ili2fgdb/view/SchemaImportPanel.fxml"), bundle);
		
		customPanel = new SchemaImportPanelController();
		
		loader.setController(customPanel);
		
		try {
			cp = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Override
	public Map<String, String> getParams() {
		return customPanel.getParams();
	}

	@Override
	public String getName() {
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2fgdb.resources.application");
		return bundle.getString("customPanel.Title");
	}
}
