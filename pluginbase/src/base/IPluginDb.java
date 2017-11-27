package base;

import java.util.Map;

import base.dbconn.Ili2DbScope;
import javafx.scene.Parent;

public interface IPluginDb extends Iplugin{
	public String getNameDB();
	public String getHelpText();
	public Parent getDbConfigPanel();
	public void loadDbConfigPanel(boolean createSchema);
	public Map<String,String> getConnectionsParams();
	public Ili2DbScope getScope();
	
	public int runMain(String[] args);
}
