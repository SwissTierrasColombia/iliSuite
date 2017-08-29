package application.ili2db.common;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import application.data.AppData;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.plugin.PluginsLoader;
import base.IPluginDb;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class DatabaseOptionsController implements Navigable, Initializable{
	IPluginDb plugin;
	
	@FXML
	private Button btn_ok;
	
	@FXML
	private BorderPane mainPane;
	

	@Override
	public boolean validate() {
		Map<String, String> result = plugin.getConnectionsParams();
		
		if(result==null) return false;
		
		AppData data = AppData.getInstance();
		data.getParamsContainer().getParamsMap().putAll(result);
		
		return true;
	}

	@Override
	public EnumPaths getNextPath() {
		EnumPaths result = null;
		
		EnumActionIli2Db action = AppData.getInstance().getActionIli2Db();
		
		switch(action){
		case EXPORT:
			result = EnumPaths.EXP_DATA_EXPORT_DATA_OPTIONS;
			break;
		case IMPORT:
			result = EnumPaths.IMP_DATA_IMPORT_OPTIONS;
			break;
		case IMPORT_SCHEMA:
			result = EnumPaths.MODEL_CONVERT_OPTIONS;
			break;
		}
		
		return result;
	}

	@Override
	public boolean isFinalPage() {
		return false;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String pluginKey = AppData.getInstance().getPlugin();
		
		// TODO Verificar si es null
		plugin = (IPluginDb) PluginsLoader.getPluginByKey(pluginKey);
		
		plugin.loadDbConfigPanel();
		
		mainPane.setCenter(plugin.getDbConfigPanel());
		
	}

	
	
}
