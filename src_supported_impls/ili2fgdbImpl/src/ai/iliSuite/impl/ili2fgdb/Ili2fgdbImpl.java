package ai.iliSuite.impl.ili2fgdb;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.iliSuite.base.IliExecutable;
import ai.iliSuite.impl.DbDescription;
import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.impl.ili2fgdb.dbconn.FgdbConnection;
import ai.iliSuite.impl.ili2fgdb.dbconn.Ili2fgdbScope;
import ai.iliSuite.impl.ili2fgdb.view.DatabaseOptionsController;
import ch.ehi.ili2db.AbstractMain;
import ch.ehi.ili2fgdb.FgdbMain;


public class Ili2fgdbImpl implements ImplFactory {

	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.iliSuite.impl.ili2fgdb.resources.application");
		AbstractMain mainApp = new FgdbMain();
		String dbName = "File Geodatabase";
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
	public Ili2DbScope getScope(AbstractConnection connection) {
		return new Ili2fgdbScope(connection);
	}

	@Override
	public IliExecutable getInterlisExecutable() {
		return new FgdbExecutable();
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
		return new FgdbConnection();
	}
}
