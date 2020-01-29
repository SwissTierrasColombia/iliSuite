package ai.ilisuite.impl.ili2ora.dbConn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import ai.ilisuite.impl.dbconn.AbstractConnection;

public class OracleConnection  extends AbstractConnection {
	
	private final String DB_PORT="1521";
	private final String DB_HOST="localhost";
	
	@Override
	protected String getDriver() {
		return "oracle.jdbc.driver.OracleDriver";
	}

	@Override
	protected String getServerStringConnection() {
		return "jdbc:oracle:thin:@";
	}

	@Override
	protected String getUrl() {
		Map<String,String> params = getConnectionParams();
		
		String strDbHost = params.get("host")!=null&&!params.get("host").isEmpty()? params.get("host") : DB_HOST;
		String strPort = params.get("port")!=null && !params.get("port").isEmpty()? params.get("port") : DB_PORT;
		String strDbdatabase = params.get("databaseName") != null && !params.get("databaseName").isEmpty()? ":" + params.get("databaseName") : "";
		String strService = params.get("dbservice") != null && !params.get("dbservice").isEmpty()? "/" + params.get("dbservice") : "";
		
		String subProtocol = getServerStringConnection();
		
		if(params.get("dbservice") != null && !params.get("dbservice").isEmpty()) {
			subProtocol += "//";
		}
		return subProtocol + strDbHost + ":" + strPort + strDbdatabase + strService;

	}

	@Override
	public boolean checkSchema(String schema) throws SQLException, ClassNotFoundException {
		boolean schemaExist = false;
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		try {
			ResultSet rs = statement.executeQuery(
					"SELECT username FROM dba_users WHERE username = '" + schema + "'");
			
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
