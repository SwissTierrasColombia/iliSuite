package ai.ilisuite.controller;

import java.util.Map;

public interface DbSelectorController {
	public void databaseSelected(String dbKey);
	public boolean databaseConnecting(Map<String,String> connectionParams);
}
