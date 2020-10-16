package ai.ilisuite.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ai.ilisuite.impl.DbDescription;
import ai.ilisuite.impl.EnumCustomPanel;
import ai.ilisuite.impl.ImplFactory;
import ai.ilisuite.impl.PanelCustomizable;
import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.util.params.EnumParams;
import ai.ilisuite.util.plugin.PluginsLoader;
import ai.ilisuite.view.DatabaseOptionsView;
import ai.ilisuite.view.DatabaseSelectionView;
import ai.ilisuite.view.ModelConvertOptionsView;
import ai.ilisuite.view.wizard.Wizard;


public class GeneratePhysicalModelController extends IliController implements DbSelectorController {
	private DatabaseOptionsView dbSelectionScreen;
	private ModelConvertOptionsView modelConvertOptions;
	private Map<String, DbDescription> lstDbDescription;
	private ImplFactory dbImpl;
	
	public GeneratePhysicalModelController() throws IOException {
		super();
	}

	@Override
	protected void addWizardSteps(Wizard wizard) throws IOException {
		initLstDbDescription();
		modelConvertOptions = new ModelConvertOptionsView(this);
		dbSelectionScreen = new DatabaseOptionsView(this, this);
		
		wizard.add(new DatabaseSelectionView(this, lstDbDescription));
		wizard.add(dbSelectionScreen);
		wizard.add(modelConvertOptions);
	}
	
	// FIX Repeated code
	private void initLstDbDescription() {
		Map<String, ImplFactory> lstPlugin = PluginsLoader.getPlugins();
		lstDbDescription = new HashMap<String, DbDescription>();
		
		for(Entry<String, ImplFactory> item : lstPlugin.entrySet()) {
			ImplFactory pluginItem = (ImplFactory) item.getValue();
			DbDescription description = pluginItem.getDbDescription();
			String dbKey = item.getKey();
			
			lstDbDescription.put(dbKey, description);
		}
	}

	@Override
	protected void addCustomParams(Map<String, String> params) {
		params.put(EnumParams.SCHEMA_IMPORT.getName(), null);
	}

	@Override
	protected String getExecutablePath() {
		DbDescription dbDesc = dbImpl.getDbDescription();
		
		String basePath = "programs/ili2db/";
		String AppNameAndVersion = dbDesc.getAppName() + "-" + dbDesc.getAppVersion(); 
		String javaExec = "java -jar";
		
		File file = new File(basePath + AppNameAndVersion + ".jar");
		
		return javaExec + " \"" + file.getAbsolutePath() + "\"";
	}

	@Override
	public void databaseSelected(String dbKey) {
		dbImpl = PluginsLoader.getPluginByKey(dbKey);
		
		AbstractConnection connection = dbImpl.getConnector();
		IController dbPanel;
		try {
			dbPanel = dbImpl.getController(connection, true);
			dbSelectionScreen.setDbPanel(dbPanel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PanelCustomizable customPanel = dbImpl.getCustomPanel(EnumCustomPanel.SCHEMA_IMPORT);

		if(customPanel != null) {
			modelConvertOptions.setCustomPanel(customPanel);
		}
	}

	@Override
	public boolean databaseConnecting(Map<String, String> connectionParams) {
		return true;
	}
}
