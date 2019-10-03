package ai.iliSuite.view.common.ili2db;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import ai.iliSuite.application.data.AppData;
import ai.iliSuite.util.plugin.PluginsLoader;
import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.Navigable;
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
	
	private EnumPaths action;

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
		return action;
	}

	@Override
	public boolean isFinalPage() {
		return false;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		EnumActionIli2Db actionSelected = AppData.getInstance().getActionIli2Db();
		
		switch(actionSelected){
		case EXPORT:
			action = EnumPaths.EXP_DATA_EXPORT_DATA_OPTIONS;
			break;
		case IMPORT:
			action = EnumPaths.IMP_DATA_IMPORT_OPTIONS;
			break;
		case IMPORT_SCHEMA:
			action = EnumPaths.MODEL_CONVERT_OPTIONS;
			break;
		}
		
		boolean createSchema = (action == EnumPaths.MODEL_CONVERT_OPTIONS);
		
		AppData data = AppData.getInstance();
		data.getParamsContainer().getParamsMap().clear();
		
		String pluginKey = AppData.getInstance().getPlugin();
		
		// TODO Verificar si es null
		plugin = (IPluginDb) PluginsLoader.getPluginByKey(pluginKey);
		
		plugin.loadDbConfigPanel(createSchema);
		
		mainPane.setCenter(plugin.getDbConfigPanel());
		
	}

	
	
}
