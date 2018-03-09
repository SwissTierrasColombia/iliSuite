package iliSuite.plugin.ili2ora;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import base.IPluginDb;
import base.controller.IController;
import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;
import ch.ehi.ili2ora.OraMain;
import iliSuite.plugin.ili2ora.dbConn.Ili2OraScope;
import iliSuite.plugin.ili2ora.dbConn.OracleConnection;
import iliSuite.plugin.ili2ora.view.DatabaseOptionsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2OraPlugin implements IPluginDb{

	private IController controllerDbConfigPanel;
	private Parent dbConfigPanel;
	private AbstractConnection connection;
	
	@Override
	public void load() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return "Ili2OraPlugin";
	}

	@Override
	public String getNameDB() {
		return "Oracle database";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2ora.resources.application");
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
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2ora.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2OraPlugin.class.getResource("/iliSuite/plugin/ili2ora/view/DatabaseOptions.fxml"), bundle);
		
		loader.setController(new DatabaseOptionsController());
		
		try {
			dbConfigPanel = loader.load();
			controllerDbConfigPanel = loader.getController();
			controllerDbConfigPanel.setConnection(connection);

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
}
