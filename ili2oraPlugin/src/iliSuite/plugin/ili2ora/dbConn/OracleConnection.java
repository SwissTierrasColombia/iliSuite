package iliSuite.plugin.ili2ora.dbConn;

import java.sql.SQLException;
import java.util.Map;

import base.dbconn.AbstractConnection;

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
		String strService = ""; // dbservice != null && !dbservice.isEmpty()? "/" + dbservice : "";
		
		String subProtocol = "jdbc:oracle:thin:@";
		
//		if(dbservice != null && !dbservice.isEmpty()) {
//			subProtocol += "//";
//		}
		return subProtocol + strDbHost + ":" + strPort + strDbdatabase + strService;

	}

	@Override
	protected boolean checkSchema(String databaseSchema) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
