package ai.iliSuite.impl.ili2pg;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.iliSuite.impl.DbDescription;
import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2pg.dbconn.Ili2PgScope;
import ai.iliSuite.impl.ili2pg.dbconn.PostgresConnection;
import ai.iliSuite.impl.ili2pg.view.DatabaseOptionsController;
import ch.ehi.ili2db.AbstractMain;
import ch.ehi.ili2pg.PgMain;


public class Ili2pgImpl implements ImplFactory {

	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2pg.resources.application");
		AbstractMain mainApp = new PgMain();
		String dbName = "Postgresql";
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
		
		(new PgMain()).domain(args);
		
		return 0;
	}

	@Override
	public Ili2DbScope getScope(AbstractConnection connection){
		return new Ili2PgScope(connection);
	}

	@Override
	public PanelCustomizable getCustomPanel(EnumCustomPanel panelType) {
		PanelCustomizable result = null;
		
		if(panelType == EnumCustomPanel.SCHEMA_IMPORT)
			result = new SchemaImportPanel();
		
		return result;
	}

	@Override
	public AbstractConnection getConnector() {
		return new PostgresConnection();
	}
}
