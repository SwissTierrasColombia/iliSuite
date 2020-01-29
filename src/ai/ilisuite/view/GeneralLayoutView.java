package ai.ilisuite.view;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ai.ilisuite.application.data.Config;
import ai.ilisuite.controller.GeneralController;
import ai.ilisuite.menu.dialog.AboutDialog;
import ai.ilisuite.menu.dialog.HelpDialog;
import ai.ilisuite.menu.dialog.PreferencesController;
import ai.ilisuite.menu.dialog.ProxyDialog;
import ai.ilisuite.view.dialog.ModelDirDialog;
import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.util.navigation.ResourceUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;


public class GeneralLayoutView implements Initializable {
	@FXML
	private Pane contentPane;
	@FXML
	private ImageView iv_functionIcon;
	@FXML
	private Label lbl_functionTitle;
	
	private ResourceBundle bundle;
	private Parent viewRootNode;
	
	public GeneralLayoutView(GeneralController controller) throws IOException {
		viewRootNode = ResourceUtil.loadResource(EnumPaths.GENERAL_LAYOUT, EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	public void onClick_MenuItemModelDir() {
		try {
			ModelDirDialog dialog = new ModelDirDialog();
			dialog.setTitle(bundle.getString("dialog.modeldirdefault.title"));

			Config config = Config.getInstance();

			if (config.getModelDir() != null && !config.getModelDir().isEmpty())
				dialog.setData(Arrays.asList(config.getModelDir().split(";")));

			Optional<List<String>> result = dialog.showAndWait();

			if (result.isPresent()) {
				config.setModelDir(String.join(";", result.get()));
				config.saveToFile();
			}

			// return controller.isOkButton();
		} catch (IOException E) {
			E.printStackTrace();
			// return false;
		}
	}
	
	public void onClick_MenuItemPreferences() {
		try {
			PreferencesController dialog = new PreferencesController();

			dialog.setTitle(bundle.getString("menu.item.preferences"));
			dialog.showAndWait();
		} catch (IOException E) {
			E.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		
		//TODO cambiar a una mejor forma de cargar, se pone de manera temporal
		changeTitle(EnumPaths.APP_ICON,bundle.getString("main.function.home.title"));
       
	}

	public void onClick_MenuItemProxyConf() {
		try {
			Config config = Config.getInstance();

			ProxyDialog dialog = new ProxyDialog(config.getProxyHost(), config.getProxyPort());
			dialog.setTitle(bundle.getString("dialog.proxy.title"));

			Optional<String> result = dialog.showAndWait();

			if (result.isPresent()) {
				config.setProxyHost(dialog.getProxyHost());
				config.setProxyPort(dialog.getProxyPort());
				config.saveToFile();
			}

		} catch (IOException E) {
			// TODO Unimplemented
			E.printStackTrace();
		}
	}
	
	@FXML
	private void onClick_MenuItemHelpContents(){
		HelpDialog dialog;
		try {
			dialog = new HelpDialog();
			dialog.setTitle(bundle.getString("dialog.help.title"));
			dialog.initModality(Modality.NONE);
			dialog.initStyle(StageStyle.DECORATED);
			dialog.resizableProperty().setValue(true);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onClick_MenuItemAbout() throws IOException{
		AboutDialog about = new AboutDialog();
		about.setTitle(bundle.getString("menu.item.about"));
		about.show();
	}
	
	
	public void changeTitle(EnumPaths iconPath, String title){
		lbl_functionTitle.setText(title);

		String url = getClass().getResource(iconPath.getPath()).toExternalForm();
		Image image = new Image(url);
		iv_functionIcon.setImage(image);
	}

	// XXX Create interface with 'getGraphicComponent' method
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
	
	// XXX This function is misnamed 'drawPage'
	public void drawPage(Parent content) {
		contentPane.getChildren().setAll(content);
	}
}
