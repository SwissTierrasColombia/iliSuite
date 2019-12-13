package ai.iliSuite.view;

import java.io.IOException;

import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.ResourceUtil;
import ai.iliSuite.view.wizard.StepViewController;
import javafx.scene.Parent;

public class OpenUmlEditorView extends StepViewController {

	// FIX Tal vez en clase padre
	private Parent viewRootNode;
	
	public OpenUmlEditorView() throws IOException {
		// XXX Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource(EnumPaths.OPEN_UML_EDITOR, EnumPaths.RESOURCE_BUNDLE, this);
	}

	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
}
