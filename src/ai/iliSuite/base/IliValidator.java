package ai.iliSuite.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.interlis2.validator.Main;

import ai.iliSuite.util.params.EnumParams;

public class IliValidator implements InterlisExecutable {
	private HashMap<String, String> paramsMap;
		
	public IliValidator() {
		paramsMap = new HashMap<String, String>();
	}
	
	@Override
	public void run() {
		String[] args = getCommand().toArray(new String[0]);
		Main.main(args);
	}

	@Override
	public List<String> getCommand() {
		HashMap<String, String> params = (HashMap<String, String>) paramsMap.clone();
		List<String> result = new ArrayList<String>();
		String finalPath = params.get(EnumParams.FILE_NAME.getName());
		
		if(finalPath != null && !finalPath.isEmpty())
			params.remove(EnumParams.FILE_NAME.getName());
		
		for(Map.Entry<String, String> item:params.entrySet()){
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

	@Override
	public String getArgs(boolean hideSensitiveData) {
		List<String> command = getCommand();
		
		if (hideSensitiveData) {
			// FIX hard-coding?
			int index = command.indexOf("--dbpwd");
			if(index != -1) {
				command.set(index+1, "**********");
			}
		}
		
		String result = String.join(" ", command);
		
		return result;
	}

	@Override
	public HashMap<String, String> getParams() {
		return paramsMap;
	}

}
