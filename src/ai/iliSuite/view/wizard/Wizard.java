package ai.iliSuite.view.wizard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class Wizard extends BaseWizard implements Initializable {
	private Parent mainView;
	
	@FXML
	private Button btnBack;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnCancel;
	
	@FXML
	private BorderPane contentPane;
	

	public Wizard() throws IOException {
		// XXX hardcoding string path
		mainView = ResourceUtil.loadResource("/ai/iliSuite/view/wizard/wizardLayout.fxml", EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnBack.setOnAction((ActionEvent e) -> { this.goBack(); });
		btnNext.setOnAction((ActionEvent e) -> { this.goForward(); });
		btnCancel.setOnAction((ActionEvent e) -> { this.cancel(); });
	}

	// XXX different to parent
	public Parent getGraphicComponent() {
		return mainView;
	}

	@Override
	protected void drawPage(StepViewController item) {
		contentPane.setCenter(item.getGraphicComponent());
	}

	@Override
	protected void loadedPage() {
		// 
		if(this.index == this.steps.size()-1) {
			// XXX hardcoding
			btnNext.setText("finish");
		} else {
			// XXX hardcoding
			btnNext.setText("next");
		}
	}
}
