package iliSuite.plugin.ili2fgdb;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import base.IPluginDb;
import base.controller.IController;
import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;
import ch.ehi.ili2fgdb.FgdbMain;
import iliSuite.plugin.ili2fgdb.dbconn.FgdbConnection;
import iliSuite.plugin.ili2fgdb.dbconn.Ili2fgdbScope;
import iliSuite.plugin.ili2fgdb.view.DatabaseOptionsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2fgdbPlugin implements IPluginDb {

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
		return "Ili2fgdbPlugin";
	}

	@Override
	public String getNameDB() {
		return "File Geodatabase";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2fgdb.resources.application");
		return bundle.getString("database.description");
	}

	@Override
	public Parent getDbConfigPanel() {
		return dbConfigPanel;
	}

	@Override
	public void loadDbConfigPanel(boolean createSchema) {
		connection = new FgdbConnection();
		
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2fgdb.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2fgdbPlugin.class.getResource("/iliSuite/plugin/ili2fgdb/view/DatabaseOptions.fxml"), bundle);
		
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

	@Override
	public int runMain(String[] args) {
		(new FgdbMain()).domain(args);
		return 0;
	}

}
