package iliSuite.plugin.ili2sqlserver.dbconn;

import java.sql.SQLException;
import java.util.Map;

import base.dbconn.AbstractConnection;

public class SqlserverConnection extends AbstractConnection {

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
		String strPort = params.get("port") != null && !params.get("port").isEmpty() ? params.get("port") : "";
		String strInstance = params.get("instance") == null || params.get("instance").isEmpty()?"":"\\"+params.get("instance");
		String strDbdatabase = ";databaseName="+params.get("databaseName");
		String strWindowsAuth =  params.get("dbWindowsAuth")!=null?";integratedSecurity=true":"";
		
		return getServerStringConnection() + strDbHost + strInstance + strPort + strDbdatabase + strWindowsAuth;
	}

	@Override
	protected boolean checkSchema(String databaseSchema) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
