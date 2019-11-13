package ai.iliSuite.actions.validateData;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.interlis2.validator.Main;

import ai.iliSuite.application.data.AppData;
import ai.iliSuite.controller.ParamsController;
import ai.iliSuite.util.exception.ExitException;
import ai.iliSuite.util.log.LogListenerExt;
import ai.iliSuite.util.params.ParamsContainer;
import ai.iliSuite.view.util.console.NoOpUndoManager;
import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.Navigable;
import ai.iliSuite.view.util.navigation.ResourceUtil;
import ai.iliSuite.view.wizard.StepArgs;
import ai.iliSuite.view.wizard.StepViewController;
import ch.ehi.basics.logging.EhiLogger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FinishDataValidationController  extends StepViewController  implements Initializable {

	private StyleClassedTextArea txtConsole;
	
	private LogListenerExt log;

	@FXML
	private VBox verticalWrapper;

	ExecutorService executor = Executors.newFixedThreadPool(1);
	boolean stop = false;
	
	Runnable runnableTask;
	
	private Parent viewRootNode;

	private ParamsController controller;
	
	public FinishDataValidationController (ParamsController controller) throws IOException {
		this.controller = controller;
		
		// TODO Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource("/ai/iliSuite/actions/validateData/finishDataValidation.fxml", EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTxtConsole();
		log = new LogListenerExt(txtConsole, "");
		EhiLogger.getInstance().addListener(log);
	}
	
	@Override
	public void loadedPage(StepArgs args) {
		super.loadedPage(args);
		String command = controller.getTextParams();
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
	
	@FXML
	private void btnExecute_click() {
		controller.execute();
	}

	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
}
