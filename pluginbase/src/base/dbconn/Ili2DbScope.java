package base.dbconn;

import java.sql.SQLException;
import java.util.List;

public interface Ili2DbScope {
	public boolean isScoped() throws ClassNotFoundException, SQLException;;
	public List<String> getDatasetList() throws ClassNotFoundException, SQLException;
	public List<String> getBasketList() throws ClassNotFoundException, SQLException;
	public List<String> getTopicList() throws ClassNotFoundException, SQLException;
	public List<String> getModelList() throws ClassNotFoundException, SQLException;
}
