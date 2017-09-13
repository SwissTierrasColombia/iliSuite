package validateData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.interlis2.validator.Main;

import application.data.AppData;
import application.data.Config;
import application.exception.ExitException;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.params.EnumParams;
import application.util.params.ParamsContainer;
import ch.ehi.basics.logging.EhiLogger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import log.util.LogListener;

public class FinishDataValidationController implements Navigable, Initializable {

	@FXML
	private Text txtConsole;
	
	private LogListener log;
	
	// TODO Verificar si es el lugar correcto de la variable
	private List<String> command;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log = new LogListener(txtConsole, "");
		EhiLogger.getInstance().addListener(log);
		
		Config config = Config.getInstance();
		
		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();

		if (config.getProxyHost() != null && config.getProxyPort() != null && !config.getProxyHost().isEmpty()) {
			paramsContainer.getParamsMap().put(EnumParams.PROXY.getName(), config.getProxyHost());
			paramsContainer.getParamsMap().put(EnumParams.PROXY_PORT.getName(), config.getProxyPort() + "");
		}

		command = paramsContainer.getCommand(null);
		txtConsole.setText(String.join(" ", command));
	}
	
	@Override
	public boolean validate() {
		String[] arg = command.toArray(new String[0]);
		
		try{
			Main.main(arg);
			return true;
		} catch (ExitException e) {
			System.out.println(e.status);
			return e.status==0;
		}
	}

	@Override
	public EnumPaths getNextPath() {
		return null;
	}

	@Override
	public boolean isFinalPage() {
		return true;
	}

}
