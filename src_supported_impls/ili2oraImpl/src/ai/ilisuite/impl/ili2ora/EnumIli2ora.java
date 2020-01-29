package ai.ilisuite.impl.ili2ora;

public enum EnumIli2ora {
	/******************
	 * ili2db Params
	 ******************/
	DB_HOST("--dbhost", true),
	DB_PORT("--dbport", true),
	DB_DATABASE("--dbdatabase", true),
	DB_SCHEMA("--dbschema", true),
	DB_USER("--dbusr", true),
	DB_SERVICE("--dbservice", true),
	DB_PWD("--dbpwd", true);

	private final String name;
	private final boolean needParam;

	EnumIli2ora(String name, boolean needParam) {
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
