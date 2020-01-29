package ai.ilisuite.impl.ili2gpkg.dbconn;

import java.sql.SQLException;
import java.util.Map;

import ai.ilisuite.impl.dbconn.AbstractConnection;

public class SqlLiteConnection extends AbstractConnection {

	@Override
	protected String getDriver() {
		return "org.sqlite.JDBC";
	}

	@Override
	protected String getServerStringConnection() {
		return "jdbc:sqlite:";
	}

	@Override
	protected String getUrl() {
		Map<String,String> params = getConnectionParams();
		
		String strDbFile = params.get("dbfile") != null && !params.get("dbfile").isEmpty() ? params.get("dbfile") : "";
		
		return getServerStringConnection() + strDbFile;
	}

	@Override
	public boolean checkSchema(String databaseSchema) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
