package ai.ilisuite.impl.ili2pg.dbconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import ai.ilisuite.impl.dbconn.AbstractConnection;

public class PostgresConnection extends AbstractConnection {	
	@Override
	protected String getDriver() {
		return "org.postgresql.Driver";
	}

	@Override
	protected String getServerStringConnection() {
		return "jdbc:postgresql://";
	}

	@Override
	protected String getUrl(){
		Map<String,String> params = getConnectionParams();
		
		String dbHost = params.get("host") != null && !params.get("host").isEmpty() ? params.get("host") : "localhost";
		String dbPort = params.get("port") != null && !params.get("port").isEmpty() ? params.get("port") : "5432";

		return getServerStringConnection() + dbHost + ":" + dbPort + "/" + params.get("databaseName");
	}

	@Override
	public boolean checkSchema(String schema) throws SQLException, ClassNotFoundException {
		boolean schemaExist = false;
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		try {
			ResultSet rs = statement.executeQuery(
					"SELECT schema_name FROM information_schema.schemata WHERE schema_name = '" + schema + "'");
			
			while (rs.next()) {
				schemaExist = true;
				break;
			}
			if (!schemaExist)
				throw new SQLException("Schema error");
	
		} finally {
			statement.close();
			
			conn.close();
		}
		return schemaExist;
	}
}
