package ai.iliSuite.impl.controller;

import java.util.Map;

import ai.iliSuite.impl.dbconn.AbstractConnection;

public interface IController {
	public Map<String, String> getParams();
	public void setConnection(AbstractConnection connection);
	public void setCreateSchema(boolean createSchema);
}
