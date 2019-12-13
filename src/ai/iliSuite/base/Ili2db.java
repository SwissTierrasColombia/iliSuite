package ai.iliSuite.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.util.params.EnumParams;
import ai.iliSuite.util.exception.ExitException;


public class Ili2db implements InterlisExecutable {
	private ImplFactory dbImpl;
	
	// TODO Change key for class or enum?
	private HashMap<String, String> paramsMap;
	
	boolean stop;
	
	public Ili2db() {
		paramsMap = new HashMap<String, String>();
		dbImpl = null;
	}
	
	@Override
	public void run() {
		String[] args = getCommand().toArray(new String[0]);
		stop = false;
		// executor.execute(runnableTask);
		try {
			dbImpl.runMain(args);
			stop = true;
			// return true;
		} catch (ExitException e) {
			System.out.println(e.status);
			stop = true; 
			// return false;
		}
	}
	
	@Override
	public List<String> getCommand(){
		HashMap<String, String> params = new HashMap<String, String>(paramsMap);
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
	
	public ImplFactory getDbImpl() {
		return dbImpl;
	}
	public void setDbImpl(ImplFactory dbImpl) {
		this.dbImpl = dbImpl;
	}
	
	@Override
	public String getArgs(boolean hideSensitiveData) {
		List<String> command = getCommand();
		
		if (hideSensitiveData) {
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
