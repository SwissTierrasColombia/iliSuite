package base.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractConnection {

	protected abstract String getDriver();
	
	protected abstract String getServerStringConnection();
	
	private Map<String,String> connectionParams;
	
	public boolean isValid() throws SQLException, ClassNotFoundException {
		boolean result = false;
		Connection conn = getConnection();
		
		result = !conn.isClosed();
		
		conn.close();
		
		return result;
	}
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Class.forName(getDriver());
		
		// TODO
		Map<String,String> params = getConnectionParams();
		
		if(params.containsKey("user")&&params.containsKey("password"))
			conn = DriverManager.getConnection(getUrl(),params.get("user"),params.get("password"));
		else{
			conn = DriverManager.getConnection(getUrl()); 
		}
		
		return conn;
	}
	
	protected abstract String getUrl();
	
	protected abstract boolean checkSchema(String databaseSchema) throws SQLException, ClassNotFoundException;
	
	public Map<String, String> getConnectionParams() {
		return connectionParams;
	}
	
	public void setConnectionParams(Map<String, String> connectionParams) {
		this.connectionParams = connectionParams;
	}
}





