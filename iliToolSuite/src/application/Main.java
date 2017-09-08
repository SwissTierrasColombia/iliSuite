package application;

import java.io.File;

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
		Thread.sleep(2000 * 3);
		try {
			Config config = Config.getInstance();

			// TODO Nombre en string
			File file = new File(".config.properties");

			if (file.exists()) {
				Config.loadConfig(file, config);
			} else {
				// TODO Excepción si no puede crear el archivo
				file.createNewFile();
			}

			setUserAgentStylesheet(STYLESHEET_CASPIAN);
			// ...::
			PluginsLoader.Load();

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
			primaryStage.setTitle("IliSuite");
			String url = getClass().getResource("/resources/images/icon64.png").toExternalForm();
			primaryStage.getIcons().add(new Image(url));

			VisualResource mainOptions = ResourceUtil.loadResource(getClass(), EnumPaths.MAIN_OPTIONS,
					EnumPaths.RESOURCE_BUNDLE);
			NavigationUtil.setNextScreen(mainOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

	}

	public static void main(String[] args) {
		System.setSecurityManager(new IliSuiteSecurityManager());
		launch(args);
	}
}
