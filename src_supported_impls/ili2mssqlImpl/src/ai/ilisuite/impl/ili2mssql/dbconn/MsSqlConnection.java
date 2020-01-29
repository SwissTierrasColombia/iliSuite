package ai.ilisuite.impl.ili2mssql.dbconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import ai.ilisuite.impl.dbconn.AbstractConnection;

public class MsSqlConnection extends AbstractConnection {

	@Override
	protected String getDriver() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	protected String getServerStringConnection() {
		return "jdbc:sqlserver://";
	}

	@Override
	protected String getUrl() {
		Map<String,String> params = getConnectionParams();
		
		String strDbHost = params.get("host") != null && !params.get("host").isEmpty() ? params.get("host") : "localhost";
		String strPort = params.get("port") != null && !params.get("port").isEmpty() ? ":" + params.get("port") : "";
		String strInstance = params.get("instance") == null || params.get("instance").isEmpty()?"":"\\"+params.get("instance");
		String strDbdatabase = ";databaseName="+params.get("databaseName");
		String strWindowsAuth =  params.get("dbWindowsAuth")!=null?";integratedSecurity=true":"";
		
		return getServerStringConnection() + strDbHost + strInstance + strPort + strDbdatabase + strWindowsAuth;
	}

	@Override
	public boolean checkSchema(String schema) throws SQLException, ClassNotFoundException {
		boolean schemaExist = false;
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		try {
			ResultSet rs = statement.executeQuery(
					"SELECT SCHEMA_ID FROM sys.schemas WHERE  [name] = '" + schema + "'");
			
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
