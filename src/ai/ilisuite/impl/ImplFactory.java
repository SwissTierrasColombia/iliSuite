package ai.ilisuite.impl;

import java.io.IOException;

import ai.ilisuite.base.IliExecutable;
import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.impl.dbconn.Ili2DbScope;

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
