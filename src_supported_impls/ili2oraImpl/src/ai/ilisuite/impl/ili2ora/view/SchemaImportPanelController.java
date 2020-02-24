package ai.ilisuite.impl.ili2ora.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import ai.ilisuite.impl.ili2ora.EnumIli2ora;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SchemaImportPanelController implements Initializable {
	@FXML
	private TextField tbxGeneralTablespace;
	@FXML
	private TextField tbxIndexTablespace;
	@FXML
	private TextField tbxLobTablespace;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addInitListeners();
	}

	private void addInitListeners(){
	}
	
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<>();
		
		String generalTablespace = tbxGeneralTablespace.getText();
		String indexTablespace = tbxIndexTablespace.getText();
		String lobTablespace = tbxLobTablespace.getText();
		
		if(!generalTablespace.isEmpty()) {
			result.put(EnumIli2ora.GENERAL_TABLESPACE.getName(), generalTablespace);
		}
		if(!indexTablespace.isEmpty()) {
			result.put(EnumIli2ora.INDEX_TABLESPACE.getName(), indexTablespace);
		}
		if(!lobTablespace.isEmpty()) {
			result.put(EnumIli2ora.LOB_TABLESPACE.getName(), lobTablespace);
		}

		return result;
	}
	
	public List<String> getParamsForRemove(){
		List<String> result = new ArrayList<>();
		
		String generalTablespace = tbxGeneralTablespace.getText();
		String indexTablespace = tbxIndexTablespace.getText();
		String lobTablespace = tbxLobTablespace.getText();
		
		if(!generalTablespace.isEmpty()) {
			result.add(EnumIli2ora.GENERAL_TABLESPACE.getName());
		}
		if(!indexTablespace.isEmpty()) {
			result.add(EnumIli2ora.INDEX_TABLESPACE.getName());
		}
		if(!lobTablespace.isEmpty()) {
			result.add(EnumIli2ora.LOB_TABLESPACE.getName());
		}

		return result;
	}
}
