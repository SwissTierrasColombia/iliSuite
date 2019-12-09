package ai.iliSuite.impl.ili2ora;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.iliSuite.impl.DbDescription;
import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2ora.dbConn.Ili2OraScope;
import ai.iliSuite.impl.ili2ora.dbConn.OracleConnection;
import ai.iliSuite.impl.ili2ora.view.DatabaseOptionsController;
import ch.ehi.ili2db.AbstractMain;
import ch.ehi.ili2ora.OraMain;


public class Ili2oraImpl implements ImplFactory{

	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2ora.resources.application");
		AbstractMain mainApp = new OraMain();
		String dbName = "Oracle database";
		String helpText = bundle.getString("database.description");
		String appName = mainApp.getAPP_NAME();
		String appVersion = mainApp.getVersion();
		
		return new DbDescription(appName, appVersion, dbName, helpText);
	}
	
	@Override
	public IController getController(AbstractConnection connection, boolean createSchema) throws IOException {
		return new DatabaseOptionsController(connection, createSchema);
	}

	@Override
	public int runMain(String[] args) {

		(new OraMain()).domain(args);
		
		return 0;
	}

	@Override
	public Ili2DbScope getScope(AbstractConnection connection) {
		return new Ili2OraScope(connection);
	}
	
	@Override
	public PanelCustomizable getCustomPanel(EnumCustomPanel panelType) {
		return null;
	}

	@Override
	public AbstractConnection getConnector() {
		return new OracleConnection();
	}
}
