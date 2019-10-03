package ai.iliSuite.impl.ili2gpkg;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2gpkg.dbconn.Ili2geopakageScope;
import ai.iliSuite.impl.ili2gpkg.dbconn.SqlLiteConnection;
import ai.iliSuite.impl.ili2gpkg.view.DatabaseOptionsController;
import ch.ehi.ili2gpkg.GpkgMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2gpkgImpl implements ImplFactory{

	private IController controllerDbConfigPanel;
	private Parent dbConfigPanel;
	private AbstractConnection connection;

	@Override
	public String getNameDB() {
		return "Geopackage";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2gpkg.resources.application");
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
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2gpkg.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2gpkgImpl.class.getResource("/ai/iliSuite/impl/ili2gpkg/view/DatabaseOptions.fxml"), bundle);
		
		
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

	@Override
	public Map<EnumCustomPanel, PanelCustomizable> getCustomPanels() {
		return null;
	}

}
