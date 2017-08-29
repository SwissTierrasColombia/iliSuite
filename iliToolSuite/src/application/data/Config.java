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
	
	static public void loadConfig(File file, Config config) throws IOException{
		Properties properties = new Properties();
		
		InputStream stream = new FileInputStream(file);
		
		properties.load(stream);
		
		//TODO Nombre de los parametros
		config.modelDir = properties.getProperty("modeldir");
		
		Integer intProxyPort = null;
		
		try{
			intProxyPort = Integer.valueOf(properties.getProperty("proxyport"));
		}catch(NumberFormatException E){
			//
		}
		
		config.proxyPort = intProxyPort;
		config.proxyHost = properties.getProperty("proxyhost");
	}
	
	static public void saveConfig(File file, Config config) throws IOException{
		Properties properties = new Properties();
		
		//TODO nombre de los parametros quemado
		if(config.proxyHost != null && config.proxyPort!=null && !config.proxyHost.isEmpty()){
				properties.setProperty("proxyport", ""+config.proxyPort);
				properties.setProperty("proxyhost", config.proxyHost);
		}
		properties.setProperty("modeldir", config.modelDir);
		
        OutputStream out = new FileOutputStream(file);
        properties.store(out, "Config");
	}
}
