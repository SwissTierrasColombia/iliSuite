package ai.ilisuite.impl.ili2gpkg;

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
import ai.ilisuite.impl.ili2gpkg.dbconn.Ili2geopakageScope;
import ai.ilisuite.impl.ili2gpkg.dbconn.SqlLiteConnection;
import ai.ilisuite.impl.ili2gpkg.view.DatabaseOptionsController;
import ch.ehi.ili2db.AbstractMain;
import ch.ehi.ili2gpkg.GpkgMain;


public class Ili2gpkgImpl implements ImplFactory{

	@Override
	public DbDescription getDbDescription() {
		ResourceBundle bundle = ResourceBundle.getBundle("ai.ilisuite.impl.ili2gpkg.resources.application");
		AbstractMain mainApp = new GpkgMain();
		String dbName = "Geopackage";
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
		return new Ili2geopakageScope(connection);
	}

	@Override
	public IliExecutable getInterlisExecutable() {
		return new GpkgExecutable();
	}

	@Override
	public PanelCustomizable getCustomPanel(EnumCustomPanel panelType) {
		return null;
	}

	@Override
	public AbstractConnection getConnector() {
		return new SqlLiteConnection();
	}
}
