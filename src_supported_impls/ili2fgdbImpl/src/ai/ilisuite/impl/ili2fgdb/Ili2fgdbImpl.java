package ai.ilisuite.impl.ili2fgdb;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.ilisuite.impl.DbDescription;
import ai.ilisuite.impl.EnumCustomPanel;
import ai.ilisuite.impl.ImplFactory;
import ai.ilisuite.impl.PanelCustomizable;
import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.impl.dbconn.Ili2DbScope;
import ai.ilisuite.impl.ili2fgdb.dbconn.FgdbConnection;
import ai.ilisuite.impl.ili2fgdb.dbconn.Ili2fgdbScope;
import ai.ilisuite.impl.ili2fgdb.view.DatabaseOptionsController;


public class Ili2fgdbImpl implements ImplFactory {

	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.ilisuite.impl.ili2fgdb.resources.application");
		
		String dbName = "File Geodatabase";
		String helpText = bundle.getString("database.description");
		String appName = "ili2fgdb";
		String appVersion = "4.4.3";
		
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
