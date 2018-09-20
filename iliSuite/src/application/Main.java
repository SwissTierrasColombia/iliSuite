package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.data.Config;
import application.exception.IliSuiteSecurityManager;
import application.util.navigation.EnumPaths;
import application.util.navigation.NavigationUtil;
import application.util.navigation.ResourceUtil;
import application.util.navigation.VisualResource;
import application.util.plugin.PluginsLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	private final String configFileName = ".config.properties";
	private final String logDirName = "log";
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
		File file = new File(config.getConfigPath()); 
		
		if(!dirLogAppIliSuite.exists())
			dirLogAppIliSuite.mkdirs();
			
		if(!dirIliSuite.exists())
			dirIliSuite.mkdirs();

		if(!file.exists())
			file.createNewFile();
		
		if(!dirLog.exists())
			dirLog.mkdirs();
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			VisualResource rootLayout = ResourceUtil.loadResource(getClass(), EnumPaths.GENERAL_LAYOUT,
					EnumPaths.RESOURCE_BUNDLE);
			NavigationUtil.setMainScreen(rootLayout);

			Scene scene = new Scene(rootLayout.getComponent());
			scene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("iliSuite");
			
			List<Image> icons = new ArrayList<Image>();
			icons.add(new Image(getClass().getResource("/resources/images/icon128.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon64.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon32.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon48.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon16.png").toExternalForm()));
			
			
			primaryStage.getIcons().addAll(icons);

			VisualResource mainOptions = ResourceUtil.loadResource(getClass(), EnumPaths.MAIN_OPTIONS,
					EnumPaths.RESOURCE_BUNDLE);
			NavigationUtil.setNextScreen(mainOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

	}

	public static void main(String[] args) {
		launch(args);
	}
}
