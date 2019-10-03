package ai.iliSuite.impl.ili2fgdb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2fgdb.dbconn.FgdbConnection;
import ai.iliSuite.impl.ili2fgdb.dbconn.Ili2fgdbScope;
import ai.iliSuite.impl.ili2fgdb.view.DatabaseOptionsController;
import ch.ehi.ili2fgdb.FgdbMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2fgdbImpl implements ImplFactory {

	private IController controllerDbConfigPanel;
	private Parent dbConfigPanel;
	private AbstractConnection connection;
	private Map<EnumCustomPanel, PanelCustomizable> customPanels;
	
	
	public Ili2fgdbImpl() {
		SchemaImportPanel panel = new SchemaImportPanel();
		customPanels = new HashMap<EnumCustomPanel, PanelCustomizable>();
		customPanels.put(EnumCustomPanel.SCHEMA_IMPORT, panel);
	}

	@Override
	public String getNameDB() {
		return "File Geodatabase";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2fgdb.resources.application");
		return bundle.getString("database.description");
	}

	@Override
	public Parent getDbConfigPanel() {
		return dbConfigPanel;
	}

	@Override
	public void loadDbConfigPanel(boolean createSchema) {
		connection = new FgdbConnection();
		
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2fgdb.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2fgdbImpl.class.getResource("/ai/iliSuite/impl/ili2fgdb/view/DatabaseOptions.fxml"), bundle);
		
		loader.setController(new DatabaseOptionsController());
		
		try {
			dbConfigPanel = loader.load();
			controllerDbConfigPanel = loader.getController();
			controllerDbConfigPanel.setConnection(connection);
			controllerDbConfigPanel.setCreateSchema(createSchema);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, String> getConnectionsParams() {
		Map<String,String> result = null;
		if(controllerDbConfigPanel!=null)
			result = controllerDbConfigPanel.getParams();
		return result;
	}

	@Override
	public Ili2DbScope getScope() {
		return new Ili2fgdbScope(connection);
	}
	
	static public void main(String[] args) {
		args = new String[]{"--help"};
		(new FgdbMain()).domain(args);
	}

	@Override
	public int runMain(String[] args) {
		(new FgdbMain()).domain(args);
		return 0;
	}
	
	@Override
	public String getAppName() {
		return (new FgdbMain()).getAPP_NAME();
	}

	@Override
	public String getAppVersion() {
		return (new FgdbMain()).getVersion();
	}

	@Override
	public Map<EnumCustomPanel, PanelCustomizable> getCustomPanels() {
		return customPanels;
	}


}
