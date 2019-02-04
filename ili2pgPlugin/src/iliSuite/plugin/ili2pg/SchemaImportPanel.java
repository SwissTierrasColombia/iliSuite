package iliSuite.plugin.ili2pg;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import base.PanelCustomizable;
import iliSuite.plugin.ili2pg.view.SchemaImportPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SchemaImportPanel implements PanelCustomizable{
	
	private SchemaImportPanelController customPanel;
	
	@Override
	public Parent getPanel() {
		Parent cp = null;
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2pg.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2pgPlugin.class.getResource("/iliSuite/plugin/ili2pg/view/SchemaImportPanel.fxml"), bundle);
		
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
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2pg.resources.application");
		return bundle.getString("customPanel.Title");
	}
}
