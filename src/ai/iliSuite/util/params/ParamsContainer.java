package ai.iliSuite.util.params;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.iliSuite.application.data.AppData;
import ai.iliSuite.application.data.Config;

public class ParamsContainer {
	
	private final String logExt=".log";
	
	private HashMap<String, String> paramsMap;
	private String finalPath;
	
	public ParamsContainer(){
		paramsMap = new HashMap<String, String>();
		finalPath = "";
	}
	

	
	public List<String> getCommand(String action){
		List<String> result = new ArrayList<String>();

		if(action != null && !action.isEmpty())
			result.add(action);
		
		for( Map.Entry<String, String> item:paramsMap.entrySet()){
			result.add(item.getKey());
			// TODO true?
			if(!item.getValue().equals("true")){
				result.add(item.getValue());
			}
		}
		if(this.getFinalPath() != null && !this.getFinalPath().equals(""))
			result.add(this.getFinalPath());
		
		return result;
	}

	/*********************
	 * Getters y Setters *
	 *********************/

	public HashMap<String, String> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(HashMap<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public String getFinalPath() {
		return finalPath;
	}

	public void setFinalPath(String finalPath) {
		this.finalPath = finalPath;
	}
	
	static public void addCommonsParameters () {
		Config config = Config.getInstance();
		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();

		if (config.getProxyHost() != null && config.getProxyPort() != null && !config.getProxyHost().isEmpty()) {
			paramsContainer.getParamsMap().put(EnumParams.PROXY.getName(), config.getProxyHost());
			paramsContainer.getParamsMap().put(EnumParams.PROXY_PORT.getName(), config.getProxyPort() + "");
		}
		
		if (config.isTraceEnabled()) {
			paramsContainer.getParamsMap().put(EnumParams.TRACE.getName(), "true");
		}
		
		
		if (config.isLogEnabled()) {
			// add log file 
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String logDir = config.getLogDir() +  System.getProperty("file.separator") + dateFormat.format(now) + paramsContainer.logExt;
			paramsContainer.getParamsMap().put(EnumParams.LOG.getName(), logDir);
		}
	}
}
