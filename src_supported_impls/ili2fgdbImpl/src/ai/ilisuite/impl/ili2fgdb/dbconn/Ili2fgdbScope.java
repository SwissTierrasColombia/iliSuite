package ai.ilisuite.impl.ili2fgdb.dbconn;

import java.sql.SQLException;
import java.util.List;

import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.impl.dbconn.Ili2DbScope;

public class Ili2fgdbScope extends Ili2DbScope {

	
	public Ili2fgdbScope(AbstractConnection connection){
		super(connection);
	}
	
	@Override
	public List<String> getDatasetList() throws SQLException {
		String query = "SELECT datasetname FROM -__SCHEMA__-t_ili2db_dataset";
		return super.getDatasetList(query);
	}

	@Override
	public List<String> getBasketList() throws SQLException {
		String query = "SELECT  t_ili_tid FROM -__SCHEMA__-t_ili2db_basket";
		return super.getBasketList(query);
	}

	@Override
	public List<String> getTopicList() throws SQLException {
		String query = "SELECT  topic FROM -__SCHEMA__-t_ili2db_basket";
		return super.getTopicList(query);
	}

	@Override
	public List<String> getModelList() throws SQLException {
		String query = "SELECT  modelname FROM -__SCHEMA__-t_ili2db_model";
		return super.getModelList(query);
	}
	
	@Override
	public boolean isScoped() throws SQLException {
		String query = "SELECT setting FROM -__SCHEMA__-t_ili2db_settings" + " WHERE tag = 'ch.ehi.ili2db.BasketHandling'";
		return super.isScoped(query);	
	}

}
