package validateData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.interlis2.validator.Main;

import application.data.AppData;
import application.exception.ExitException;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.params.ParamsContainer;
import application.view.undo.NoOpUndoManager;
import ch.ehi.basics.logging.EhiLogger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import log.util.LogListenerExt;

public class FinishDataValidationController implements Navigable, Initializable {

	private StyleClassedTextArea txtConsole;
	
	private LogListenerExt log;

	@FXML
	private VBox verticalWrapper;

	// TODO Verificar si es el lugar correcto de la variable
	private List<String> command;

	ExecutorService executor = Executors.newFixedThreadPool(1);
	boolean stop = false;
	
	Runnable runnableTask;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTxtConsole();
		
		new SimpleBooleanProperty();
		log = new LogListenerExt(txtConsole, "");
		EhiLogger.getInstance().addListener(log);
		
		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();
		
		ParamsContainer.addCommonsParameters();
		
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
		txtConsole.setUndoManager(new NoOpUndoManager());

		runnableTask = () -> {
			int maxParagraphs = 500;

			while (!stop) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					int numParsToRemove = txtConsole.getParagraphs().size() - maxParagraphs;
					if (numParsToRemove > 0) {
						txtConsole.deleteText(0, 0, numParsToRemove, 0);
					}

					txtConsole.showParagraphAtBottom(txtConsole.getParagraphs().size());

				});
			}
		};
	}

	@Override
	public boolean validate() {
		String[] arg = command.toArray(new String[0]);
		stop = false;
		executor.execute(runnableTask);
		try{
			Main.main(arg);
			stop = true;
			return true;
		} catch (ExitException e) {
			System.out.println(e.status);
			stop = true;
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
