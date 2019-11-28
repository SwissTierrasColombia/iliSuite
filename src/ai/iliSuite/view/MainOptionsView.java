package ai.iliSuite.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.iliSuite.controller.EnumActions;
import ai.iliSuite.controller.GeneralController;
import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.ResourceUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainOptionsView implements Initializable {

	// XXX the name is too generic
	private EnumActions selectedAction;
	
	private ResourceBundle bundle;

	@FXML
	private ToggleGroup tg_mainOptions;

	@FXML
	private ToggleButton btn_openUmlEditor;
	@FXML
	private ToggleButton btn_validateModel;
	@FXML
	private ToggleButton btn_generatePhysicalModel;
	@FXML
	private ToggleButton btn_importData;
	@FXML
	private ToggleButton btn_validateData;
	@FXML
	private ToggleButton btn_exportData;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnBack;

	@FXML
	private Label lbl_helpTitle;
	@FXML
	private Text txt_helpContent;

	private Parent viewRootNode;
	
	@FXML
	private BorderPane contentPane;
	
	private GeneralController controller;
	
	public MainOptionsView(GeneralController controller) throws IOException {
		this.controller = controller;
		viewRootNode = ResourceUtil.loadResource(EnumPaths.WIZARD_LAYOUT, EnumPaths.RESOURCE_BUNDLE, this);
		Parent content = ResourceUtil.loadResource(EnumPaths.MAIN_OPTIONS, EnumPaths.RESOURCE_BUNDLE, this);
		contentPane.setCenter(content);
		
		initButtons();
		addListenerToToggleGroup();
	}

	private void initButtons() {
		btnNext.setOnAction((ActionEvent e) -> { this.goForward(e); });
		btnCancel.setOnAction((ActionEvent e) -> { 
			Stage s = (Stage) viewRootNode.getScene().getWindow();
			s.close();
		});
		btnBack.setVisible(false);
		String strExit = bundle.getString("buttons.exit");
		btnCancel.setText(strExit);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
	}

	private void addListenerToToggleGroup() {
		tg_mainOptions.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle newToggle) {
				if (newToggle == null) {
					lbl_helpTitle.setText("");
					txt_helpContent.setText("");
					selectedAction = null;
				} else if (newToggle == btn_openUmlEditor) {
					lbl_helpTitle.setText(bundle.getString("main.openUmlEditor"));
					txt_helpContent.setText(bundle.getString("main.content.openUmlEditor"));
					selectedAction = EnumActions.OPEN_UML_EDITOR;
				} else if (newToggle == btn_generatePhysicalModel) {
					lbl_helpTitle.setText(bundle.getString("main.generatePhysicalModel"));
					txt_helpContent.setText(bundle.getString("main.content.generatePhysicalModel"));
					selectedAction = EnumActions.GENERATE_PHYSICAL_MODEL;
				} else if (newToggle == btn_importData) {
					lbl_helpTitle.setText(bundle.getString("main.importOrModifyData"));
					txt_helpContent.setText(bundle.getString("main.content.importOrModifyData"));
					selectedAction = EnumActions.IMPORT_DATA;
				} else if (newToggle == btn_validateData) {
					lbl_helpTitle.setText(bundle.getString("main.validateData"));
					txt_helpContent.setText(bundle.getString("main.content.validateData"));
					selectedAction = EnumActions.VALIDATE_DATA;
				} else if (newToggle == btn_exportData) {
					lbl_helpTitle.setText(bundle.getString("main.exportData"));
					txt_helpContent.setText(bundle.getString("main.content.exportData"));
					selectedAction = EnumActions.EXPORT_DATA;
				}

			}
		});
	}

	
	public void goForward(ActionEvent e) {
		controller.changeAction(selectedAction);
	}
	
	// XXX Create interface with 'getGraphicComponent' method
	public Parent getGraphicComponent() {
		return viewRootNode;
	}

	public EnumActions getSelectedAction() {
		return selectedAction;
	}
}
