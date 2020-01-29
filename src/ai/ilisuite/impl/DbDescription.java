package ai.ilisuite.impl;

public class DbDescription {
	private String appName;
	private String appVersion;
	private String dbName;
	private String helpText;

	public DbDescription(String appName, String appVersion, String dbName, String helpText) {
		this.appName = appName;
		this.appVersion = appVersion;
		this.dbName = dbName;
		this.helpText = helpText;
	}
	
	public String getAppName() {
		return appName;
	}
	public String getDbName() {
		return dbName;
	}
	public String getHelpText() {
		return helpText;
	}
	public String getAppVersion() {
		return appVersion;
	}
}
