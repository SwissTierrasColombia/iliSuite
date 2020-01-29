package ai.ilisuite.impl.ili2pg;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.ilisuite.base.IliExecutable;
import ai.ilisuite.impl.DbDescription;
import ai.ilisuite.impl.EnumCustomPanel;
import ai.ilisuite.impl.ImplFactory;
import ai.ilisuite.impl.PanelCustomizable;
import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.impl.dbconn.Ili2DbScope;
import ai.ilisuite.impl.ili2pg.dbconn.Ili2PgScope;
import ai.ilisuite.impl.ili2pg.dbconn.PostgresConnection;
import ai.ilisuite.impl.ili2pg.view.DatabaseOptionsController;
import ch.ehi.ili2db.AbstractMain;
import ch.ehi.ili2pg.PgMain;


public class Ili2pgImpl implements ImplFactory {

	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.ilisuite.impl.ili2pg.resources.application");
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
	public IliExecutable getInterlisExecutable() {
		return new PgExecutable();
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
