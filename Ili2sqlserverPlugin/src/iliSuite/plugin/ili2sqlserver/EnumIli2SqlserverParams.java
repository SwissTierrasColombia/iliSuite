package iliSuite.plugin.ili2sqlserver;

public enum EnumIli2SqlserverParams {
	/******************
	 * ili2db Params
	 ******************/
	DB_HOST("--dbhost", true),
	DB_PORT("--dbport", true),
	DB_DATABASE("--dbdatabase", true),
	DB_USER("--dbusr", true),
	DB_PWD("--dbpwd", true),
	DB_SCHEMA("--dbschema", true),
	DB_INSTANCE("--dbinstance",true),
	DB_WINDOWS_AUTH("--dbwindowsauth",false);

	private final String name;
	private final boolean needParam;

	EnumIli2SqlserverParams(String name, boolean needParam) {
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
