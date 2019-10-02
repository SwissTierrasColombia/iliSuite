package iliSuite.plugin.ili2fgdb.dbconn;

import java.sql.SQLException;
import java.util.List;

import base.dbconn.AbstractConnection;
import base.dbconn.Ili2DbScope;

public class Ili2fgdbScope extends Ili2DbScope {

	
	public Ili2fgdbScope(AbstractConnection connection){
		super(connection);
	}
	
	
	public List<String> getDatasetList() throws ClassNotFoundException, SQLException {
		String query = "SELECT datasetname FROM -__SCHEMA__-t_ili2db_dataset";
		return super.getDatasetList(query);
	}

	public List<String> getBasketList() throws ClassNotFoundException, SQLException {
		String query = "SELECT  t_ili_tid FROM -__SCHEMA__-t_ili2db_basket";
		return super.getBasketList(query);
	}

	public List<String> getTopicList() throws ClassNotFoundException, SQLException {
		String query = "SELECT  topic FROM -__SCHEMA__-t_ili2db_basket";
		return super.getTopicList(query);
	}

	public List<String> getModelList() throws ClassNotFoundException, SQLException {
		String query = "SELECT  modelname FROM -__SCHEMA__-t_ili2db_model";
		return super.getModelList(query);
	}
	
	public boolean isScoped() throws ClassNotFoundException, SQLException {
		String query = "SELECT setting FROM -__SCHEMA__-t_ili2db_settings" + " WHERE tag = 'ch.ehi.ili2db.BasketHandling'";
		return super.isScoped(query);	
	}

}
