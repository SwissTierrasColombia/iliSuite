package ai.iliSuite.application.data;
import ai.iliSuite.util.params.ParamsContainer;
import ai.iliSuite.view.EnumActionIli2Db;

public class AppData {
	private String plugin;
	private EnumActionIli2Db actionIli2Db;
	
	private ParamsContainer paramsContainer;

	static private AppData instance;
	
	private AppData(){
		plugin = "";
		paramsContainer = new ParamsContainer();
	}
	
	static public AppData getInstance(){
		if(instance == null) instance = new AppData();
		return instance;
	}
	
	public String getPlugin(){
		return plugin;
	}
	
	public void setPlugin(String value){
		plugin = value;
	}

	public EnumActionIli2Db getActionIli2Db() {
		return actionIli2Db;
	}

	public void setActionIli2Db(EnumActionIli2Db actionIli2Db) {
		this.actionIli2Db = actionIli2Db;
	}

	public ParamsContainer getParamsContainer() {
		return paramsContainer;
	}

	public void setParamsContainer(ParamsContainer paramsContainer) {
		this.paramsContainer = paramsContainer;
	}
}
