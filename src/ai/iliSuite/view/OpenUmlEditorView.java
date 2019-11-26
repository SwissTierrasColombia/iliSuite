package ai.iliSuite.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.iliSuite.controller.ParamsController;
import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.Navigable;
import ai.iliSuite.view.util.navigation.ResourceUtil;
import ai.iliSuite.view.wizard.StepViewController;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class OpenUmlEditorView extends StepViewController implements Initializable {

	// FIX Tal vez en clase padre
	private Parent viewRootNode;
	
	private ParamsController controller;
	
	public OpenUmlEditorView() throws IOException {
		// XXX Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource("/ai/iliSuite/view/fxml/openUmlEditor.fxml", EnumPaths.RESOURCE_BUNDLE, this);
		
		//this.controller = controller;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
}
