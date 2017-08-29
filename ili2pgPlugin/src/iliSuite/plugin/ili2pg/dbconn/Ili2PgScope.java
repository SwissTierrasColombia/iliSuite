package iliSuite.plugin.ili2pg.dbconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;

// TOODO Ili2DbScope como clase abstracta?
public class Ili2PgScope implements Ili2DbScope {

	private AbstractConnection connection;
	
	public Ili2PgScope(AbstractConnection connection){
		this.connection = connection;
	}
	
	@Override
	public List<String> getDatasetList() throws ClassNotFoundException, SQLException {
		List<String> result = new ArrayList<>();
		
		Connection conn = null;

		try{
			conn = connection.getConnection();
		
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT DISTINCT datasetname FROM "+schema+"t_ili2db_dataset");
			while(rs.next()){
				String name = rs.getString("datasetname");
				result.add(name);
			}
		}finally{
			if(conn!=null)
				conn.close();
		}
		return result;
	}

	@Override
	public List<String> getBasketList() throws ClassNotFoundException, SQLException {
		List<String> result = new ArrayList<>();
		Connection conn = null;
		
		try{
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT DISTINCT t_id FROM "+schema+"t_ili2db_basket");
			while(rs.next()){
				String name = rs.getString("t_id");
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return result;
	}

	@Override
	public List<String> getTopicList() throws ClassNotFoundException, SQLException {
		List<String> result = new ArrayList<>();
		Connection conn = null;
		
		try{
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT DISTINCT topic FROM "+schema+"t_ili2db_basket");
			while(rs.next()){
				String name = rs.getString("topic");
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return result;	
	}

	@Override
	public List<String> getModelList() throws ClassNotFoundException, SQLException {
		List<String> result = new ArrayList<>();
		Connection conn = null;
		
		try{
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT DISTINCT modelname FROM "+schema+"t_ili2db_model");
			while(rs.next()){
				String name = rs.getString("modelname");
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return result;	
	}

}
