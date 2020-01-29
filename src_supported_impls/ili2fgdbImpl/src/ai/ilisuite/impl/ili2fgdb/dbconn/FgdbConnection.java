package ai.ilisuite.impl.ili2fgdb.dbconn;

import java.sql.SQLException;
import java.util.Map;

import ai.ilisuite.impl.dbconn.AbstractConnection;

public class FgdbConnection extends AbstractConnection {

	@Override
	protected String getDriver() {
		return "ch.ehi.ili2fgdb.jdbc.FgdbDriver";
	}

	@Override
	protected String getServerStringConnection() {
		return "jdbc:ili2fgdb:";
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
