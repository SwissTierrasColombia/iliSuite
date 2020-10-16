package ai.ilisuite.impl.ili2ora;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.ilisuite.impl.DbDescription;
import ai.ilisuite.impl.EnumCustomPanel;
import ai.ilisuite.impl.ImplFactory;
import ai.ilisuite.impl.PanelCustomizable;
import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.impl.dbconn.Ili2DbScope;
import ai.ilisuite.impl.ili2ora.dbConn.Ili2OraScope;
import ai.ilisuite.impl.ili2ora.dbConn.OracleConnection;
import ai.ilisuite.impl.ili2ora.view.DatabaseOptionsController;
import ai.ilisuite.impl.ili2ora.SchemaImportPanel;


public class Ili2oraImpl implements ImplFactory{

	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.ilisuite.impl.ili2ora.resources.application");
		ResourceBundle version = ResourceBundle.getBundle("ai.ilisuite.impl.ili2ora.Version");

		String dbName = "Oracle database";
		String helpText = bundle.getString("database.description");
		String appName = "ili2ora";
		String appVersion = version.getString("version");
		
		return new DbDescription(appName, appVersion, dbName, helpText);
	}
	
	@Override
	public IController getController(AbstractConnection connection, boolean createSchema) throws IOException {
		return new DatabaseOptionsController(connection, createSchema);
	}
	
	@Override
	public Ili2DbScope getScope(AbstractConnection connection) {
		return new Ili2OraScope(connection);
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
		return new OracleConnection();
	}
}
