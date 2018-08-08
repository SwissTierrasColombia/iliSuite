package application.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	private String modelDir;
	private Integer proxyPort;
	private String proxyHost;
	private String language;
	
	private boolean traceEnabled; 

	static private Config instance;
	

	public String getModelDir() {
		return modelDir;
	}

	public void setModelDir(String modelDir) {
		this.modelDir = modelDir;
	}
	
	private Config(){
		modelDir = "";
		proxyHost = "";
		proxyPort = null;
		traceEnabled = false;
	}
	
	static public Config getInstance(){
		if(instance==null){
			instance = new Config();
		}
		
		return instance;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public boolean isTraceEnabled() {
		return traceEnabled;
	}

	public void setTraceEnabled(boolean traceEnabled) {
		this.traceEnabled = traceEnabled;
	}

	static public void loadConfig(File file, Config config) throws IOException{
		Properties properties = new Properties();
		
		InputStream stream = new FileInputStream(file);
		
		properties.load(stream);
		
		//TODO parameters name: hard-code
		config.modelDir = properties.getProperty("modeldir");
		
		Integer intProxyPort = null;
		
		try{
			intProxyPort = Integer.valueOf(properties.getProperty("proxyport"));
		}catch(NumberFormatException E){
			//
		}
		
		config.proxyPort = intProxyPort;
		config.proxyHost = properties.getProperty("proxyhost");
		
		config.language = null;
		
		String strLanguage = properties.getProperty("language");
		// FIX Is this place to check language list?
		if(strLanguage != null && (strLanguage.equals("en") 
				|| strLanguage.equals("es") || strLanguage.equals("de"))) {
			config.language = strLanguage;
		}
		
		String strTrace = properties.getProperty("traceEnabled", "false");
		strTrace = strTrace.toLowerCase();
		
		config.traceEnabled = strTrace.equals("true");
	}
	
	static public void saveConfig(File file, Config config) throws IOException{
		Properties properties = new Properties();
		
		if(config.proxyHost != null && config.proxyPort!=null && !config.proxyHost.isEmpty()){
				properties.setProperty("proxyport", ""+config.proxyPort);
				properties.setProperty("proxyhost", config.proxyHost);
		}
		
		if(config.modelDir != null)
			properties.setProperty("modeldir", config.modelDir);
		
		if(config.language != null)
			properties.setProperty("language", config.language);
		
        OutputStream out = new FileOutputStream(file);
        properties.store(out, "Config");
	}
}
