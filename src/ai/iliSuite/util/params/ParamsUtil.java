package ai.iliSuite.util.params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsUtil {
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

}
