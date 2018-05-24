package iliSuite.plugin.ili2mssql;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import base.IPluginDb;
import base.controller.IController;
import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;
import ch.ehi.ili2mssql.MsSqlMain;
import iliSuite.plugin.ili2mssql.dbconn.Ili2MsSqlScope;
import iliSuite.plugin.ili2mssql.dbconn.MsSqlConnection;
import iliSuite.plugin.ili2mssql.view.DatabaseOptionsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2MsSqlPlugin implements IPluginDb {

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
		return "Ili2MsSqlPlugin";
	}

	@Override
	public String getNameDB() {
		return "MS SQL Server";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2mssql.resources.application");
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
		connection = new MsSqlConnection();
		
		// TODO verificar rutas
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2mssql.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2MsSqlPlugin.class.getResource("/iliSuite/plugin/ili2mssql/view/DatabaseOptions.fxml"), bundle);
		
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
	
	static public void main(String args[]){
		args = new String[]{"--help"};
		new MsSqlMain().domain(args);
	}

	@Override
	public Ili2DbScope getScope(){
		return new Ili2MsSqlScope(connection);
	}
}
