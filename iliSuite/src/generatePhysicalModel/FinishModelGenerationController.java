package generatePhysicalModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.data.AppData;
import application.data.Config;
import application.exception.ExitException;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.params.EnumParams;
import application.util.params.ParamsContainer;
import application.util.plugin.PluginsLoader;
import base.IPluginDb;
import ch.ehi.basics.logging.EhiLogger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import log.util.LogListenerExt;

public class FinishModelGenerationController implements Navigable, Initializable {

	@FXML
	private Text txtConsole;

	private LogListenerExt log;
	
	@FXML
	private ScrollPane scrollConsole;

	// TODO Verificar si es el lugar correcto de la variable
	private List<String> command;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log = new LogListenerExt(txtConsole, "");
		EhiLogger.getInstance().addListener(log);
		Config config = Config.getInstance();

		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();

		if (config.getProxyHost() != null && config.getProxyPort() != null && !config.getProxyHost().isEmpty()) {
			paramsContainer.getParamsMap().put(EnumParams.PROXY.getName(), config.getProxyHost());
			paramsContainer.getParamsMap().put(EnumParams.PROXY_PORT.getName(), config.getProxyPort() + "");
		}

		command = paramsContainer.getCommand(EnumParams.SCHEMA_IMPORT.getName());

		txtConsole.setText(String.join(" ", command));
		
		txtConsole.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				scrollConsole.setVvalue(1);
			}
		});
	}

	@Override
	public void finalize() {
		EhiLogger.getInstance().removeListener(log);
	}

	IPluginDb plugin;

	@Override
	public boolean validate() {
		String pluginKey = AppData.getInstance().getPlugin();

		// TODO Verificar si es null
		plugin = (IPluginDb) PluginsLoader.getPluginByKey(pluginKey);

		String[] args = command.toArray(new String[0]);

		try {
			plugin.runMain(args);
			return true;
		} catch (ExitException e) {
			
			System.out.println(e.status);
			return false;
		}
	}

	@Override
	public EnumPaths getNextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFinalPage() {
		// TODO Auto-generated method stub
		return true;
	}

}
