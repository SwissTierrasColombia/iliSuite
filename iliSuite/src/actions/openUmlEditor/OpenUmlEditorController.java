package actions.openUmlEditor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import view.util.navigation.EnumPaths;
import view.util.navigation.Navigable;

public class OpenUmlEditorController implements Navigable, Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public boolean validate() {
		try {

			Runtime.getRuntime().exec("java -jar ./programs/umleditor-3.6.4.9/umleditor.jar");

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
