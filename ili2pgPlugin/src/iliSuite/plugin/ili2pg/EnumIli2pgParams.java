package iliSuite.plugin.ili2pg;

public enum EnumIli2pgParams {
	/******************
	 * ili2db Params
	 ******************/
	DB_HOST("--dbhost", true), 
	DB_PORT("--dbport", true), 
	DB_DATABASE("--dbdatabase", true), 
	DB_USER("--dbusr", true), 
	DB_PWD("--dbpwd", true), 
	DB_SCHEMA("--dbschema", true);

	private final String name;
	private final boolean needParam;

	EnumIli2pgParams(String name, boolean needParam) {
		this.name = name;
		this.needParam = needParam;
	}

	public String getName() {
		return name;
	}

	public boolean isParamNeeded() {
		return needParam;
	}

}
