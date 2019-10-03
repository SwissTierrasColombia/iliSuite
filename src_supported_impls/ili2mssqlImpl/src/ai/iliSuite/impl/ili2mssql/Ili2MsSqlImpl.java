package ai.iliSuite.impl.ili2mssql;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2mssql.dbconn.Ili2MsSqlScope;
import ai.iliSuite.impl.ili2mssql.dbconn.MsSqlConnection;
import ai.iliSuite.impl.ili2mssql.view.DatabaseOptionsController;
import ch.ehi.ili2mssql.MsSqlMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Ili2MsSqlImpl implements ImplFactory {

	private IController controllerDbConfigPanel;
	private Parent dbConfigPanel;
	private AbstractConnection connection;

	@Override
	public String getNameDB() {
		return "MS SQL Server";
	}

	@Override
	public String getHelpText() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2mssql.resources.application");
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
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2mssql.resources.application");
		FXMLLoader loader = new FXMLLoader(Ili2MsSqlImpl.class.getResource("/ai/iliSuite/impl/ili2mssql/view/DatabaseOptions.fxml"), bundle);
		
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
	
	@Override
	public String getAppName() {
		return (new MsSqlMain()).getAPP_NAME();
	}

	@Override
	public String getAppVersion() {
		return (new MsSqlMain()).getVersion();
	}

	@Override
	public Map<EnumCustomPanel, PanelCustomizable> getCustomPanels() {
		return null;
	}
}
