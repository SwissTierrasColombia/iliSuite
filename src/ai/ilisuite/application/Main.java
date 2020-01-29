package ai.ilisuite.application;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ai.ilisuite.application.data.Config;
import ai.ilisuite.controller.GeneralController;
import ai.ilisuite.util.exception.IliSuiteSecurityManager;
import ai.ilisuite.util.plugin.PluginsLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	private final String configFileName = ".config.properties";
	private final String defaultConfigFileName = ".defaultConfig.properties";
	private final String logAppDirName = "log";
	private final String iliSuiteDirName = ".ilisuite";
	
	@Override
	public void init() throws InterruptedException {
		System.setSecurityManager(new IliSuiteSecurityManager());
		Thread.sleep(1000 * 2);
		try {
			String ds = System.getProperty("file.separator");
			
			Config config = Config.getInstance();
			
			String iliDir = System.getProperty("user.home") + ds + iliSuiteDirName;
			config.setIliSuiteDir(iliDir);
			config.setConfigFileName(configFileName);
			config.setLogDir(iliDir + ds + logAppDirName);
			config.setLogAppDir(logAppDirName);
			
			CreateDirectoryStructureAndFiles(config);

			config.loadFromFile();

			setUserAgentStylesheet(STYLESHEET_CASPIAN);
			
			PluginsLoader.Load();
			
			String strLanguage = config.getLanguage();
			
			if(strLanguage != null) {
				Locale lan = new Locale(strLanguage);
				Locale.setDefault(lan);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CreateDirectoryStructureAndFiles(Config config) throws Exception {
		
		File dirLogAppIliSuite = new File(config.getLogAppDir());
		File dirIliSuite = new File(config.getIliSuiteDir());
		File dirLog = new File(config.getLogDir());
		File configFile = new File(config.getConfigPath());
		File defaultConfigFile = new File(defaultConfigFileName);
		
		if(!dirLogAppIliSuite.exists())
			dirLogAppIliSuite.mkdirs();
			
		if(!dirIliSuite.exists())
			dirIliSuite.mkdirs();

		if(!configFile.exists()){
			if(defaultConfigFile.exists()) {
				Files.copy(defaultConfigFile.toPath(), configFile.toPath());
			} else {
				configFile.createNewFile();
			}
		}
		
		if(!dirLog.exists())
			dirLog.mkdirs();
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			List<Image> icons = new ArrayList<Image>();

			icons.add(new Image(getClass().getResource("/ai/ilisuite/resources/images/icon128.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/ai/ilisuite/resources/images/icon64.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/ai/ilisuite/resources/images/icon32.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/ai/ilisuite/resources/images/icon48.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/ai/ilisuite/resources/images/icon16.png").toExternalForm()));

			String cssPath = getClass().getResource("/ai/ilisuite/resources/css/styles.css").toExternalForm();

			GeneralController generalController = new GeneralController(); 
			Scene scene = generalController.getScene(cssPath);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().addAll(icons);
			primaryStage.setTitle("iliSuite");
			primaryStage.show();
			generalController.loadMainOptions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
