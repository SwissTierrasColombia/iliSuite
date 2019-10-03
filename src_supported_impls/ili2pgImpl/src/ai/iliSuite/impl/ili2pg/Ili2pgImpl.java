package ai.iliSuite.impl.ili2pg;

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
import ai.iliSuite.impl.ili2pg.dbconn.Ili2PgScope;
import ai.iliSuite.impl.ili2pg.dbconn.PostgresConnection;
import ai.iliSuite.impl.ili2pg.view.DatabaseOptionsController;
import ch.ehi.ili2pg.PgMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2pgImpl implements ImplFactory {

	private IController controllerDbConfigPanel;
	private Parent dbConfigPanel;
	private AbstractConnection connection;

	private Map<EnumCustomPanel, PanelCustomizable> customPanels;
	
	public Ili2pgImpl(){
		connection = new PostgresConnection();
		SchemaImportPanel panel = new SchemaImportPanel();
		customPanels = new HashMap<EnumCustomPanel, PanelCustomizable>();
		customPanels.put(EnumCustomPanel.SCHEMA_IMPORT, panel);
	}

	@Override
	public String getNameDB() {
		return "Postgresql";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2pg.resources.application");
		return bundle.getString("database.description");
	}

	@Override
	public Parent getDbConfigPanel() {
		return dbConfigPanel;
	}
	
	@Override
	public Map<String, String> getConnectionsParams() {
		Map<String,String> result = null;
		if(controllerDbConfigPanel!=null)
			result = controllerDbConfigPanel.getParams();
		return result;
	}

	@Override
	public void loadDbConfigPanel(boolean createSchema) {
		// TODO verificar rutas
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2pg.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2pgImpl.class.getResource("/ai/iliSuite/impl/ili2pg/view/DatabaseOptions.fxml"), bundle);
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
	public int runMain(String[] args) {
		
		(new PgMain()).domain(args);
		
		return 0;
	}

	@Override
	public Ili2DbScope getScope(){
		return new Ili2PgScope(connection);
	}

	@Override
	public String getAppName() {
		return (new PgMain()).getAPP_NAME();
	}

	@Override
	public String getAppVersion() {
		return (new PgMain()).getVersion();
	}

	@Override
	public Map<EnumCustomPanel, PanelCustomizable> getCustomPanels() {
		return customPanels;
	}
}
