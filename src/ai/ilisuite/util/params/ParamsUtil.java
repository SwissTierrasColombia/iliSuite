package ai.ilisuite.util.params;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.ilisuite.application.data.Config;

public class ParamsUtil {
	static private final String logExt=".log";

	static public List<String> getCommand(Map<String, String> params) {
		HashMap<String, String> paramsCopy = new HashMap<String, String>(params);
		List<String> result = new ArrayList<String>();
		String finalPath = paramsCopy.get(EnumParams.FILE_NAME.getName());
		
		if(finalPath != null && !finalPath.isEmpty())
			paramsCopy.remove(EnumParams.FILE_NAME.getName());
		
		for(Map.Entry<String, String> item:paramsCopy.entrySet()){
			String key = item.getKey();
			String value = item.getValue();
			result.add(key);
			
			if(value != null && !value.isEmpty() && !value.equals("true")){
				result.add(value);
			}
		}
		
		if(finalPath != null && !finalPath.isEmpty())
			result.add(finalPath);
		
		return result;
	}

	static public String getStringArgs(List<String> params, boolean hideSensitiveData) {
		List<String> command = new ArrayList<String>(params);
		
		if (hideSensitiveData) {
			int index = command.indexOf("--dbpwd");
			if(index != -1) {
				command.set(index+1, "**********");
			}
		}

		return String.join(" ", command);
	}

	static public void addCommonParameters (Map<String, String> params) {
		Config config = Config.getInstance();

		if (config.getProxyHost() != null && config.getProxyPort() != null && !config.getProxyHost().isEmpty()) {
			params.put(EnumParams.PROXY.getName(), config.getProxyHost());
			params.put(EnumParams.PROXY_PORT.getName(), config.getProxyPort() + "");
		}
		
		if (config.isTraceEnabled()) {
			params.put(EnumParams.TRACE.getName(), "true");
		}
	
		if (config.isLogEnabled()) {
			// add log file 
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String logDir = config.getLogDir() +  System.getProperty("file.separator") + dateFormat.format(now) + ParamsUtil.logExt;
			params.put(EnumParams.LOG.getName(), logDir);
		}
	}
}
