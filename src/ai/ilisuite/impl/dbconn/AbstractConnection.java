package ai.ilisuite.impl.dbconn;

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
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName(getDriver());
			
			if(connectionParams.containsKey("user") && connectionParams.containsKey("password"))
				conn = DriverManager.getConnection(getUrl(), connectionParams.get("user"), connectionParams.get("password"));
			else{
				conn = DriverManager.getConnection(getUrl()); 
			}
		} catch (ClassNotFoundException e) {
			throw new SQLException("Failed to load JDBC Driver");
		}
		
		return conn;
	}
	
	protected abstract String getUrl();
	
	public abstract boolean checkSchema(String schema) throws SQLException, ClassNotFoundException;
	
	public Map<String, String> getConnectionParams() {
		return connectionParams;
	}
	
	public void setConnectionParams(Map<String, String> connectionParams) {
		this.connectionParams = connectionParams;
	}
}





