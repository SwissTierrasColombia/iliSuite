package application.view;

import java.util.ResourceBundle;

import application.data.AppData;
import application.ili2db.common.EnumActionIli2Db;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.navigation.NavigationUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class MainOptionsController implements Navigable {

	private EnumPaths nextPath = null;

	@FXML
	private ResourceBundle applicationBundle;
	FXMLLoader loader;
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
	private Button btn_cancel;
	@FXML
	private Button btn_ok;

	@FXML
	private Label lbl_helpTitle;
	@FXML
	private Text txt_helpContent;

	@FXML
	public void initialize(/*URL arg0, ResourceBundle arg1*/) {

		applicationBundle = ResourceBundle.getBundle("resources.languages.application");
		loader = new FXMLLoader(getClass().getResource("mainOptions.fxml"), applicationBundle);

		AppData data = AppData.getInstance();
		data.getParamsContainer().getParamsMap().clear();

		NavigationUtil.clearStepStack();
		addListenerToToggleGroup();

	}

	private void addListenerToToggleGroup() {
		tg_mainOptions.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle newToggle) {
				if (newToggle == null) {
					lbl_helpTitle.setText("");
					txt_helpContent.setText("");
					nextPath = null;
				} else if (newToggle == btn_openUmlEditor) {
					lbl_helpTitle.setText(applicationBundle.getString("main.openUmlEditor"));
					txt_helpContent.setText(applicationBundle.getString("main.content.openUmlEditor"));
					nextPath = EnumPaths.OPEN_UML_EDITOR;
				} else if (newToggle == btn_generatePhysicalModel) {
					lbl_helpTitle.setText(applicationBundle.getString("main.generatePhysicalModel"));
					txt_helpContent.setText(applicationBundle.getString("main.content.generatePhysicalModel"));
					nextPath = EnumPaths.ILI2DB_COMMON_DATABASE_SELECTION;
					AppData.getInstance().setActionIli2Db(EnumActionIli2Db.IMPORT_SCHEMA);

				} else if (newToggle == btn_importData) {
					lbl_helpTitle.setText(applicationBundle.getString("main.importOrModifyData"));
					txt_helpContent.setText(applicationBundle.getString("main.content.importOrModifyData"));
					nextPath = EnumPaths.ILI2DB_COMMON_DATABASE_SELECTION;

					AppData.getInstance().setActionIli2Db(EnumActionIli2Db.IMPORT);

				} else if (newToggle == btn_validateData) {
					lbl_helpTitle.setText(applicationBundle.getString("main.validateData"));
					txt_helpContent.setText(applicationBundle.getString("main.content.validateData"));
					nextPath = EnumPaths.VAL_DATA_VALIDATE_OPTIONS;
				} else if (newToggle == btn_exportData) {
					lbl_helpTitle.setText(applicationBundle.getString("main.exportData"));
					txt_helpContent.setText(applicationBundle.getString("main.content.exportData"));
					nextPath = EnumPaths.ILI2DB_COMMON_DATABASE_SELECTION;

					AppData.getInstance().setActionIli2Db(EnumActionIli2Db.EXPORT);
				}

			}
		});
	}


	@Override
	public boolean validate() {
		if (tg_mainOptions.getSelectedToggle() == null)
			return false;
		else
			return true;

	}

	@Override
	public EnumPaths getNextPath() {
		return nextPath;
	}

	@Override
	public boolean isFinalPage() {
		return false;
	}

}
