package iliSuite.plugin.ili2gpkg;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import base.IPluginDb;
import base.controller.IController;
import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;
import ch.ehi.ili2gpkg.GpkgMain;
import iliSuite.plugin.ili2gpkg.dbconn.Ili2geopakageScope;
import iliSuite.plugin.ili2gpkg.dbconn.SqlLiteConnection;
import iliSuite.plugin.ili2gpkg.view.DatabaseOptionsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2gpkgPlugin implements IPluginDb{

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
		return "Ili2gpkgPlugin";
	}

	@Override
	public String getNameDB() {
		return "Geopackage";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2gpkg.resources.application");
		return bundle.getString("database.description");
	}

	@Override
	public Parent getDbConfigPanel() {
		return dbConfigPanel;
	}

	@Override
	public void loadDbConfigPanel(boolean createSchema) {
		//TODO instancia no en constructor
		connection = new SqlLiteConnection();
		
		// TODO verificar rutas
		ResourceBundle bundle = ResourceBundle.getBundle("iliSuite.plugin.ili2gpkg.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2gpkgPlugin.class.getResource("/iliSuite/plugin/ili2gpkg/view/DatabaseOptions.fxml"), bundle);
		
		
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
		// TODO Auto-generated method stub
		return new Ili2geopakageScope(connection);
	}

	@Override
	public int runMain(String[] args) {
		(new GpkgMain()).domain(args);
		return 0;
	}
	
	@Override
	public String getAppName() {
		return (new GpkgMain()).getAPP_NAME();
	}

	@Override
	public String getAppVersion() {
		return (new GpkgMain()).getVersion();
	}

}
