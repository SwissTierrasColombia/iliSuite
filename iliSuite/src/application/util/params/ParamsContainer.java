package application.util.params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsContainer {
	
	
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
}
