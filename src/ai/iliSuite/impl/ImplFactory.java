package ai.iliSuite.impl;

import java.io.IOException;

import ai.iliSuite.base.IliExecutable;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.impl.dbconn.Ili2DbScope;

public interface ImplFactory {
	public DbDescription getDbDescription();
	// FIX name method
	public AbstractConnection getConnector();
	// FIX name method
	public IController getController(AbstractConnection connection, boolean createSchema) throws IOException;
	public Ili2DbScope getScope(AbstractConnection connection);
	public IliExecutable getInterlisExecutable(); 
	public PanelCustomizable getCustomPanel(EnumCustomPanel panelType);
}
