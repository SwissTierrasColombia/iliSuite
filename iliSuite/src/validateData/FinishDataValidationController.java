package validateData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.interlis2.validator.Main;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

import application.data.AppData;
import application.data.Config;
import application.exception.ExitException;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.params.EnumParams;
import application.util.params.ParamsContainer;
import ch.ehi.basics.logging.EhiLogger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import log.util.LogListenerExt;

public class FinishDataValidationController implements Navigable, Initializable {

	private StyleClassedTextArea txtConsole;
	
	private LogListenerExt log;

	@FXML
	private VBox verticalWrapper;

	// TODO Verificar si es el lugar correcto de la variable
	private List<String> command;

	private SimpleBooleanProperty booleanResult;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTxtConsole();
		
		booleanResult =  new SimpleBooleanProperty();
		log = new LogListenerExt(txtConsole, "");
		EhiLogger.getInstance().addListener(log);
		
		Config config = Config.getInstance();

		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();

		if (config.getProxyHost() != null && config.getProxyPort() != null && !config.getProxyHost().isEmpty()) {
			paramsContainer.getParamsMap().put(EnumParams.PROXY.getName(), config.getProxyHost());
			paramsContainer.getParamsMap().put(EnumParams.PROXY_PORT.getName(), config.getProxyPort() + "");
		}
		
		if (config.isTraceEnabled()) {
			paramsContainer.getParamsMap().put(EnumParams.TRACE.getName(), "true");
		}

		command = paramsContainer.getCommand(null);
		txtConsole.replaceText(String.join(" ", command)+"\n\n");
	}

	private void initTxtConsole() {
		txtConsole = new StyleClassedTextArea();
		
		VirtualizedScrollPane<StyleClassedTextArea> vsPane = new VirtualizedScrollPane<>(txtConsole);
		verticalWrapper.getChildren().add(vsPane);
        	VBox.setVgrow(vsPane, Priority.ALWAYS);
        
        	txtConsole.setWrapText(true);
        	txtConsole.setMinHeight(400);
	        txtConsole.getStyleClass().add("text_console");
		txtConsole.setEditable(false);
		
		txtConsole.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				txtConsole.moveTo(txtConsole.getLength()-1);
				txtConsole.requestFollowCaret();
			}
		});
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
