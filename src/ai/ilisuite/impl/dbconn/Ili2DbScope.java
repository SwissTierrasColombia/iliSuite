package ai.ilisuite.impl.dbconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class Ili2DbScope{

	private AbstractConnection connection;

	public Ili2DbScope(AbstractConnection connection) {
		this.connection = connection;
	}
	
	public boolean isScoped() throws SQLException{
		return isScoped(null);
	}

	protected boolean isScoped(String query) throws SQLException {
		boolean result = false;
		Connection conn = null;
		
		try {
			conn = connection.getConnection();
			String schema = connection.getConnectionParams().get("databaseSchema");

			if (schema == null)
				schema = "";
			else
				schema += ".";

			Statement statement = conn.createStatement();
			ResultSet rs = null;
			if(query==null)
				rs = statement.executeQuery("SELECT setting FROM " + schema + "t_ili2db_settings" + " WHERE tag = 'ch.ehi.ili2db.BasketHandling'");
			else{
				String[] queryWithoutSchema = query.split("-__SCHEMA__-");
				String queryPreSchema = queryWithoutSchema[0];
				String queryPostSchema = queryWithoutSchema[1];
				
				rs = statement.executeQuery(queryPreSchema+schema+queryPostSchema);
			}
			while (rs.next()) {
				String setting = rs.getString("setting");
				if (setting != null && setting.equals("readWrite"))
					result = true;
			}
		} finally {

			conn.close();
		}
		return result;
	}
	
	public List<String> getDatasetList() throws SQLException{
		return getDatasetList(null);
	}

	protected List<String> getDatasetList(String query) throws SQLException {
		List<String> result = new ArrayList<>();

		Connection conn = null;

		try {
			conn = connection.getConnection();

			String schema = connection.getConnectionParams().get("databaseSchema");

			if (schema == null)
				schema = "";
			else
				schema += ".";

			Statement statement = conn.createStatement();
			ResultSet rs = null;
			if(query==null)
				rs = statement.executeQuery("SELECT DISTINCT datasetname FROM " + schema + "t_ili2db_dataset WHERE datasetname IS NOT NULL");
			else
				rs = statement.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("datasetname");
				result.add(name);
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return result;
	}
	
	public List<String> getBasketList() throws SQLException{
		return getBasketList(null);
	}

	protected List<String> getBasketList(String query) throws SQLException {
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
			ResultSet rs = null;
			if(query==null)
				rs = statement.executeQuery("SELECT DISTINCT t_ili_tid FROM "+schema+"t_ili2db_basket WHERE t_ili_tid IS NOT NULL");
			else{
				String[] queryWithoutSchema = query.split("-__SCHEMA__-");
				String queryPreSchema = queryWithoutSchema[0];
				String queryPostSchema = queryWithoutSchema[1];
				
				rs = statement.executeQuery(queryPreSchema+schema+queryPostSchema);
			}
			while(rs.next()){
				String name = rs.getString("t_ili_tid");
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return result;
	}

	public List<String> getTopicList() throws SQLException{
		return getTopicList(null);
	}
	
	protected List<String> getTopicList(String query) throws SQLException {
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
			ResultSet rs = null;
			if(query==null)
				rs = statement.executeQuery("SELECT DISTINCT topic FROM "+schema+"t_ili2db_basket");
			else{
				String[] queryWithoutSchema = query.split("-__SCHEMA__-");
				String queryPreSchema = queryWithoutSchema[0];
				String queryPostSchema = queryWithoutSchema[1];
				
				rs = statement.executeQuery(queryPreSchema+schema+queryPostSchema);
			}
				
			while(rs.next()){
				String name = rs.getString("topic");
				result.add(name);
			}
		}finally{
		
			conn.close();
		}
		return result;	
	}
	
	public List<String> getModelList() throws SQLException{
		return getModelList(null);
	}

	protected List<String> getModelList(String query) throws SQLException {
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
			ResultSet rs = null;
			if(query==null)
				rs = statement.executeQuery("SELECT DISTINCT modelname FROM "+schema+"t_ili2db_model");
			else{
				String[] queryWithoutSchema = query.split("-__SCHEMA__-");
				String queryPreSchema = queryWithoutSchema[0];
				String queryPostSchema = queryWithoutSchema[1];
				
				rs = statement.executeQuery(queryPreSchema+schema+queryPostSchema);
			}
				
			while(rs.next()){
				String name = rs.getString("modelname");
				name = name.replaceAll("\\{[ a-zA-Z0-9_]+\\}", "");
				String[] lstModel = name.split(" ");
				for(String item:lstModel) {
					result.add(item);
				}
			}
		}finally{
		
			conn.close();
		}
		return result;	
	}
}
