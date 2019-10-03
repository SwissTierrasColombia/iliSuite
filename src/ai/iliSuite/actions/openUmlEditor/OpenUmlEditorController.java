package ai.iliSuite.actions.openUmlEditor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.Navigable;
import javafx.fxml.Initializable;

public class OpenUmlEditorController implements Navigable, Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public boolean validate() {
		try {

			Runtime.getRuntime().exec("java -jar ./programs/umleditor-3.6.5/umleditor.jar");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
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
