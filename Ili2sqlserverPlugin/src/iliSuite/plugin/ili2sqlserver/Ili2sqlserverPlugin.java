package iliSuite.plugin.ili2sqlserver;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import base.IPluginDb;
import base.controller.IController;
import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;
import ch.ehi.ili2mssql.MsSqlMain;
import iliSuite.plugin.ili2sqlserver.dbconn.Ili2SqlserverScope;
import iliSuite.plugin.ili2sqlserver.dbconn.SqlserverConnection;
import iliSuite.plugin.ili2sqlserver.view.DatabaseOptionsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2sqlserverPlugin implements IPluginDb {

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
		// TODO Auto-generated method stub
		return "Ili2sqlserverPlugin";
	}

	@Override
	public String getNameDB() {
		return "Sql Server";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2sqlserver.resources.application");
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
	public void loadDbConfigPanel() {
		//TODO instancia no en constructor
		connection = new SqlserverConnection();
		
		// TODO verificar rutas
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2sqlserver.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2sqlserverPlugin.class.getResource("/iliSuite/plugin/ili2sqlserver/view/DatabaseOptions.fxml"), bundle);
		
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

		(new MsSqlMain()).domain(args);
		
		return 0;
	}

	@Override
	public Ili2DbScope getScope(){
		return new Ili2SqlserverScope(connection);
	}
}
