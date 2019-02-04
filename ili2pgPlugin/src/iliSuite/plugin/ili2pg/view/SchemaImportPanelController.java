package iliSuite.plugin.ili2pg.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import iliSuite.plugin.ili2pg.EnumIli2pgParams;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

public class SchemaImportPanelController implements Initializable {
	@FXML
	private CheckBox chk_setupPgExt;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addInitListeners();
		chk_setupPgExt.setSelected(true);
	}

	private void addInitListeners(){
	}
	
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<>();
		
		if(chk_setupPgExt.isSelected()) {
			result.put(EnumIli2pgParams.SETUP_PG_EXT.getName(), "true");
		}

		return result;
	}
}
