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

	@Override
	public void init() throws InterruptedException {
		System.setSecurityManager(new IliSuiteSecurityManager());
		Thread.sleep(1000 * 2);
		try {
			Config config = Config.getInstance();

			// TODO Nombre en string
			File file = new File(".config.properties");

			if (file.exists()) {
				Config.loadConfig(file, config);
			} else {
				// TODO Excepcion si no puede crear el archivo
				file.createNewFile();
			}

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

	@Override
	public void start(Stage primaryStage) {

		try {
			VisualResource rootLayout = ResourceUtil.loadResource(getClass(), EnumPaths.GENERAL_LAYOUT,
					EnumPaths.RESOURCE_BUNDLE);
			NavigationUtil.setMainScreen(rootLayout);

			Scene scene = new Scene(rootLayout.getComponent());
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
