package ai.ilisuite.application.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	static private Config instance;

	private String modelDir;
	private Integer proxyPort;
	private String proxyHost;
	private String language;
	private boolean traceEnabled;
	private boolean logEnabled;
	private String logDir;

	private String iliSuiteDir;
	private String configFileName;
	private String logAppDir;

	private Config() {
		modelDir = "";
		proxyHost = "";
		proxyPort = null;
		traceEnabled = false;
		logEnabled = false;
	}

	static public Config getInstance() {
		if (instance == null)
			instance = new Config();

		return instance;
	}

	public void loadFromFile() throws IOException {
		File file = new File(getConfigPath());

		InputStream stream = new FileInputStream(file);

		Properties properties = new Properties();
		properties.load(stream);

		// TODO parameters name: hard-code
		modelDir = properties.getProperty("modeldir");

		Integer intProxyPort = null;

		try {
			intProxyPort = Integer.valueOf(properties.getProperty("proxyport"));
		} catch (NumberFormatException E) {
			//
		}

		proxyPort = intProxyPort;
		proxyHost = properties.getProperty("proxyhost");

		language = null;

		String strLanguage = properties.getProperty("language");

		// load default value
		// FIX Is this place to check language list?
		if (strLanguage != null && (strLanguage.equals("en") || strLanguage.equals("es") || strLanguage.equals("de"))) {
			language = strLanguage;
		}

		String strTrace = properties.getProperty("traceEnabled", "false");
		strTrace = strTrace.toLowerCase();

		traceEnabled = strTrace.equals("true");

		String strLogEnabled = properties.getProperty("logEnabled", "false");
		strTrace = strLogEnabled.toLowerCase();
		logEnabled = strLogEnabled.equals("true");

		logDir = properties.getProperty("logDir") != null? properties.getProperty("logDir") : logDir;
	}

	public void saveToFile() throws IOException {
		Properties properties = new Properties();

		if (proxyHost != null && proxyPort != null && !proxyHost.isEmpty()) {
			properties.setProperty("proxyport", "" + proxyPort);
			properties.setProperty("proxyhost", proxyHost);
		}

		if (modelDir != null)
			properties.setProperty("modeldir", modelDir);

		if (language != null)
			properties.setProperty("language", language);

		if (traceEnabled)
			properties.setProperty("traceEnabled", "true");

		if (logEnabled)
			properties.setProperty("logEnabled", "true");

		if (logDir != null && !logDir.isEmpty()) {
			properties.setProperty("logDir", logDir);
		}

		File file = new File(getConfigPath());
		OutputStream out = new FileOutputStream(file);
		properties.store(out, "Config");
	}

	// getters and setters
	public String getModelDir() {
		return modelDir;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public String getLanguage() {
		return language;
	}

	public boolean isTraceEnabled() {
		return traceEnabled;
	}

	public boolean isLogEnabled() {
		return logEnabled;
	}

	public String getLogDir() {
		return logDir;
	}

	public String getIliSuiteDir() {
		return iliSuiteDir;
	}

	public String getLogAppDir() {
		return logAppDir;
	}

	public void setModelDir(String modelDir) {
		this.modelDir = modelDir;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setTraceEnabled(boolean traceEnabled) {
		this.traceEnabled = traceEnabled;
	}

	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public void setIliSuiteDir(String iliSuiteDir) {
		this.iliSuiteDir = iliSuiteDir;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public void setLogAppDir(String logAppDir) {
		this.logAppDir = logAppDir;
	}

	public String getConfigPath() {
		return this.iliSuiteDir + System.getProperty("file.separator") + this.configFileName;
	}
}
