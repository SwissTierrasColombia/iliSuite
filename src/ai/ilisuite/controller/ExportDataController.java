package ai.ilisuite.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ai.ilisuite.impl.DbDescription;
import ai.ilisuite.impl.ImplFactory;
import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.impl.dbconn.Ili2DbScope;
import ai.ilisuite.util.params.EnumParams;
import ai.ilisuite.util.plugin.PluginsLoader;
import ai.ilisuite.view.DatabaseOptionsView;
import ai.ilisuite.view.DatabaseSelectionView;
import ai.ilisuite.view.ExportDataOptionsView;
import ai.ilisuite.view.wizard.Wizard;


public class ExportDataController extends IliController implements DbSelectorController {
	private DatabaseOptionsView dbSelectionScreen;
	private ExportDataOptionsView exportDataOptions;
	private Map<String, DbDescription> lstDbDescription;
	private ImplFactory dbImpl;

	private AbstractConnection connection;
	
	public ExportDataController() throws IOException {
		super();
	}
	
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
	protected void addWizardSteps(Wizard wizard) throws IOException {
		initLstDbDescription();

		dbSelectionScreen = new DatabaseOptionsView(this, this);
		exportDataOptions = new ExportDataOptionsView(this);
		
		wizard.add(new DatabaseSelectionView(this, lstDbDescription));
		wizard.add(dbSelectionScreen);
		wizard.add(exportDataOptions);
	}
	@Override
	public void databaseSelected(String dbKey) {
		dbImpl = PluginsLoader.getPluginByKey(dbKey);
		connection = dbImpl.getConnector();
		IController dbPanel;
		try {
			dbPanel = dbImpl.getController(connection, false);
			dbSelectionScreen.setDbPanel(dbPanel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String getExecutablePath() {
		DbDescription dbDesc = dbImpl.getDbDescription();
		
		String basePath = "programs/";
		String AppNameAndVersion = dbDesc.getAppName() + "-" + dbDesc.getAppVersion(); 
		String jarPath = AppNameAndVersion + "-bindist/" + AppNameAndVersion + ".jar";
		String javaExec = "java -jar";
		
		File file = new File(basePath + jarPath);
		
		return javaExec + " \"" + file.getAbsolutePath() + "\"";
	}

	@Override
	public boolean databaseConnecting(Map<String, String> connectionParams) {
		Ili2DbScope scope = dbImpl.getScope(connection);
		try {
			boolean isScoped = scope.isScoped();
			exportDataOptions.setScoped(isScoped);
			exportDataOptions.setBasketList(scope.getBasketList());
			exportDataOptions.setDatasetList(scope.getDatasetList());
			exportDataOptions.setModelList(scope.getModelList());
			exportDataOptions.setTopicList(scope.getTopicList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	protected void addCustomParams(Map<String, String> params) {
		params.put(EnumParams.DATA_EXPORT.getName(), null);
	}
}
