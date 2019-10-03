package ai.iliSuite.impl.ili2ora;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2ora.dbConn.Ili2OraScope;
import ai.iliSuite.impl.ili2ora.dbConn.OracleConnection;
import ai.iliSuite.impl.ili2ora.view.DatabaseOptionsController;
import ch.ehi.ili2ora.OraMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2oraImpl implements ImplFactory{

	private IController controllerDbConfigPanel;
	private Parent dbConfigPanel;
	private AbstractConnection connection;

	@Override
	public String getNameDB() {
		return "Oracle database";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2ora.resources.application");
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
		//TODO instancia no en constructor
		connection = new OracleConnection();
		
		// TODO verificar rutas
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2ora.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2oraImpl.class.getResource("/ai/iliSuite/impl/ili2ora/view/DatabaseOptions.fxml"), bundle);
		
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

		(new OraMain()).domain(args);
		
		return 0;
	}

	@Override
	public Ili2DbScope getScope() {
		return new Ili2OraScope(connection);
	}
	
	@Override
	public String getAppName() {
		return (new OraMain()).getAPP_NAME();
	}

	@Override
	public String getAppVersion() {
		return (new OraMain()).getVersion();
	}

	@Override
	public Map<EnumCustomPanel, PanelCustomizable> getCustomPanels() {
		return null;
	}
}
