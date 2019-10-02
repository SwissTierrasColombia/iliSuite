package base.controller;

import java.util.Map;

import base.dbconn.AbstractConnection;

public interface IController {
	public Map<String, String> getParams();
	public void setConnection(AbstractConnection connection);
	public void setCreateSchema(boolean createSchema);
}
