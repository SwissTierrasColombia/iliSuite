package ai.ilisuite.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;

import ai.ilisuite.controller.ParamsController;
import ai.ilisuite.view.util.console.NoOpUndoManager;
import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.util.navigation.ResourceUtil;
import ai.ilisuite.view.wizard.StepArgs;
import ai.ilisuite.view.wizard.StepViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FinishActionView  extends StepViewController  implements Initializable {

	private StyleClassedTextArea txtConsole;

	@FXML
	private VBox verticalWrapper;

	private Parent viewRootNode;

	private ParamsController controller;
	
	public FinishActionView (ParamsController controller) throws IOException {
		this.controller = controller;
		
		// TODO Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource(EnumPaths.FINISH_ACTION, EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTxtConsole();
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
	}

	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
	
	@Override
	public void goBack(StepArgs args) {
		controller.cancelExecution();
	}

	public StyleClassedTextArea getTxtConsole() {
		return txtConsole;
	}
}
