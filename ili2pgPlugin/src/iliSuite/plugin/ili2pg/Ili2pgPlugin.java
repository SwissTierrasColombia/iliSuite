package iliSuite.plugin.ili2pg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import base.IPluginDb;
import base.controller.IController;
import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;
import ch.ehi.ili2db.AbstractMain;
import ch.ehi.ili2pg.PgMain;
import iliSuite.plugin.ili2pg.dbconn.Ili2PgScope;
import iliSuite.plugin.ili2pg.dbconn.PostgresConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2pgPlugin implements IPluginDb {

	private IController controllerDbConfigPanel;
	private Parent dbConfigPanel;
	private AbstractConnection connection;

	public Ili2pgPlugin(){
		connection = new PostgresConnection();
	}
	
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
		return "Ili2pgPlugin";
	}

	@Override
	public String getNameDB() {
		return "Postgresql";
	}

	@Override
	public String getHelpText() {
		return "El texto de ayuda para postgis";
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
		// TODO verificar rutas
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2pg.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2pgPlugin.class.getResource("/iliSuite/plugin/ili2pg/view/DatabaseOptions.fxml"), bundle);
		
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
		
		(new PgMain()).domain(args);
		
		return 0;
	}

	@Override
	public Ili2DbScope getScope(){
		return new Ili2PgScope(connection);
	}
}
