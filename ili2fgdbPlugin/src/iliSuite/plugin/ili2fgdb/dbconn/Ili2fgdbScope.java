package iliSuite.plugin.ili2fgdb.dbconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;

public class Ili2fgdbScope implements Ili2DbScope {

	private AbstractConnection connection;
	
	public Ili2fgdbScope(AbstractConnection connection){
		this.connection = connection;
	}
	
	@Override
	public List<String> getDatasetList() throws ClassNotFoundException, SQLException {
		Set<String> result = new HashSet<>();
		
		Connection conn = null;

		try{
			conn = connection.getConnection();
		
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT datasetname FROM "+schema+" t_ili2db_dataset");
			while(rs.next()){
				String name = rs.getString("datasetname");
				
				if(name != null && !name.isEmpty())
					result.add(name);
			}
		}finally{
			if(conn!=null)
				conn.close();
		}
		return new ArrayList<String>(result);
	}

	@Override
	public List<String> getBasketList() throws ClassNotFoundException, SQLException {
		Set<String> result = new HashSet<>();
		Connection conn = null;
		
		try{
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT  t_ili_tid FROM "+schema+"t_ili2db_basket");
			while(rs.next()){
				String name = rs.getString("t_ili_tid");
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return new ArrayList<String>(result);
	}

	@Override
	public List<String> getTopicList() throws ClassNotFoundException, SQLException {
		Set<String> result = new HashSet<>();
		Connection conn = null;
		
		try{
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT  topic FROM "+schema+"t_ili2db_basket");
			while(rs.next()){
				String name = rs.getString("topic");
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return new ArrayList<String>(result);
	}

	@Override
	public List<String> getModelList() throws ClassNotFoundException, SQLException {
		Set<String> result = new HashSet<>();
		Connection conn = null;
		
		try{
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT  modelname FROM "+schema+"t_ili2db_model");
			while(rs.next()){
				String name = rs.getString("modelname");
				if(name.indexOf('{') != -1)
					name = name.substring(0, name.indexOf('{'));
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return new ArrayList<String>(result);
	}
	
	@Override
	public boolean isScoped() throws ClassNotFoundException, SQLException {
		boolean result = false;
		Connection conn = null;
		try{
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");
			
			if(schema==null)
				schema = "";
			else
				schema += ".";
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT setting FROM "+schema+"t_ili2db_settings" + " WHERE tag = 'ch.ehi.ili2db.BasketHandling'");
			while(rs.next()){
				String setting = rs.getString("setting");
				if(setting!=null && setting.equals("readWrite"))
					result = true;
			}
		}finally{
		
			conn.close();
		}
		return result;	
	}

}
