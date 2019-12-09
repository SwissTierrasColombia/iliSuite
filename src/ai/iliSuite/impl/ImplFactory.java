package ai.iliSuite.impl;

import java.io.IOException;
import java.util.Map;

import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;

public interface ImplFactory {
	public DbDescription getDbDescription();
	// FIX name method
	public AbstractConnection getConnector();
	// FIX name method
	public IController getController(AbstractConnection connection, boolean createSchema) throws IOException;
	//public Parent getDbConfigPanel(); // graphic part
	//public void loadDbConfigPanel(boolean createSchema); //¿?
	//public Map<String,String> getConnectionsParams(); // it should be supplied by controller
	public Ili2DbScope getScope(AbstractConnection connection);
	public int runMain(String[] args); 
	public PanelCustomizable getCustomPanel(EnumCustomPanel panelType);
}
