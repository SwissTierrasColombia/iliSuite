package ai.ilisuite.view.wizard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.util.navigation.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class Wizard extends BaseWizard implements Initializable {
	private ResourceBundle applicationBundle;
	private Parent mainView;
	private Insets margin;

	@FXML
	private Button btnBack;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnCancel;
	// FIX Wizard should not content Pane
	@FXML
	private BorderPane contentPane;
	
	private double prefHeight;
	private double maxHeight;
	private double minHeight;

	public Wizard() throws IOException {
		mainView = ResourceUtil.loadResource(EnumPaths.WIZARD_LAYOUT, EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		applicationBundle = arg1;
		btnBack.setOnAction((ActionEvent e) -> { this.goBack(); });
		btnNext.setOnAction((ActionEvent e) -> { this.goForward(); });
		btnCancel.setOnAction((ActionEvent e) -> { this.cancel(); });
		
		prefHeight = maxHeight = minHeight = BorderPane.USE_COMPUTED_SIZE;
	}

	// XXX different to parent
	public Parent getGraphicComponent() {
		return mainView;
	}

	@Override
	protected void drawPage(StepViewController item) {
		// FIX Casting is not type-safety
		Region content = (Region) item.getGraphicComponent();
		BorderPane.setMargin(content, margin);
		content.setPrefHeight(prefHeight);
		content.setMaxHeight(maxHeight);
		content.setMinHeight(minHeight);
		
		contentPane.setCenter(content);
	}

	@Override
	protected void loadedPage() {
		String buttonTextKey = "";
		if(this.index == this.steps.size()-1) {
			if(this.hasExecution && !this.executed)
				buttonTextKey = "buttons.execute";
			else
				buttonTextKey = "buttons.finish";
		} else {
			buttonTextKey = "buttons.next";
		}
		String buttonText = applicationBundle.getString(buttonTextKey);
		btnNext.setText(buttonText);
	}
	
	public void setMargin(Insets margin) {
		this.margin = margin;
	}

	public void setPrefHeight(double prefHeight) {
		this.prefHeight = prefHeight;
	}

	public void setMaxHeight(double maxHeight) {
		this.maxHeight = maxHeight;
	}

	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}
	
	@Override
	public void setExecuted(boolean executed) {
		super.setExecuted(executed);
	}
	
	public void setNextDisable(boolean value) {
		btnNext.setDisable(value);
	}
}
