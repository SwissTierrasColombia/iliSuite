package ai.iliSuite.view.util.navigation;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ResourceUtil {	
	public static Parent loadResource(String path, EnumPaths resourceBundle, Object viewController) throws IOException {
		Parent component;
		FXMLLoader loader;
		Class<ResourceUtil> c = ResourceUtil.class;
		if (resourceBundle != null) {
			ResourceBundle bundle = ResourceBundle.getBundle(resourceBundle.getPath());
			loader = new FXMLLoader(c.getResource(path), bundle);
		} else {
			loader = new FXMLLoader(c.getResource(path));
		}
		loader.setController(viewController);
		component = loader.load();
		return component;
	}

}
