package ai.ilisuite.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ai.ilisuite.application.data.Config;
import ai.ilisuite.controller.ParamsController;
import ai.ilisuite.util.params.EnumParams;
import ai.ilisuite.view.dialog.ModelDirDialog;
import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.util.navigation.ResourceUtil;
import ai.ilisuite.view.wizard.StepArgs;
import ai.ilisuite.view.wizard.StepViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class ValidateOptionsView extends StepViewController implements Initializable {

	private ResourceBundle applicationBundle;

	@FXML
	private Button btnBrowseXtfFile;

	@FXML
	private TextField tf_XtfFile;

	@FXML
	private TextField tf_pluginsFolder;

	@FXML
	private TextField tf_modelDir;

	@FXML
	private Button btnBrowsePluginsFolder;

	@FXML
	private Button btnBrowseModelDir;

	@FXML
	private CheckBox chk_additionalFunctionality;

	@FXML
	private CheckBox chk_disableAreaValidation;

	@FXML
	private CheckBox chk_forceTypeValidation;

	@FXML
	private CheckBox chk_configFile;
	
	@FXML
	private CheckBox chk_logXtfFile;

	@FXML
	private TextField tf_configFile;

	@FXML
	private TextField tf_logXtfFile;

	@FXML
	private Button btnBrowseConfigFile;

	@FXML
	private Button btnBrowseLogXtfFile;
	
	// FIX Tal vez en clase padre
	private Parent viewRootNode;
	
	private ParamsController controller;
	
	private HashMap<String,String> params;
	
	public ValidateOptionsView(ParamsController controller) throws IOException {
		// XXX Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource(EnumPaths.VAL_DATA_VALIDATE_OPTIONS, EnumPaths.RESOURCE_BUNDLE, this);
		
		this.controller = controller;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		applicationBundle = resources;
		addInitListeners();
		
		tf_configFile.setDisable(true);
		tf_logXtfFile.setDisable(true);
		tf_pluginsFolder.setDisable(true);

		btnBrowseConfigFile.setDisable(true);
		btnBrowseLogXtfFile.setDisable(true);
		btnBrowsePluginsFolder.setDisable(true);

		tf_modelDir.setText(Config.getInstance().getModelDir());
	}
	
	@Override
	public void goForward(StepArgs args) {
		super.goForward(args);			
		boolean isValid = validateFields(); 
		args.setCancel(!isValid);
		
		if (isValid) {
			if(params != null) {
				controller.removeParams(params);
			}
			updateParams();
			controller.addParams(params);
		}
	}
	
	@Override
	public void goBack(StepArgs args) {
		super.goBack(args);
	}

	private void addInitListeners() {

		tf_XtfFile.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals("")
						&& (newValue.endsWith(".xtf") || newValue.endsWith(".itf")))
					tf_XtfFile.setStyle(null);
			}
		});

		tf_pluginsFolder.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals(""))
					tf_pluginsFolder.setStyle(null);
			}
		});

		tf_logXtfFile.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals(""))
					tf_logXtfFile.setStyle(null);
			}
		});

		tf_configFile.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals(""))
					tf_configFile.setStyle(null);
			}
		});

	}

	private boolean validateFields() {
		boolean result = true;

		if (tf_XtfFile.getText() == null || tf_XtfFile.getText().equals("")
				|| !(tf_XtfFile.getText().endsWith(".xtf") || tf_XtfFile.getText().endsWith(".itf"))) {
			tf_XtfFile.setStyle("-fx-border-color: red ;");
			result = false;
		}
		if (chk_additionalFunctionality.isSelected()) {

			if (tf_pluginsFolder.getText() == null || tf_pluginsFolder.getText().equals("")) {
				tf_pluginsFolder.setStyle("-fx-border-color: red ;");
				result = false;
			}
		}

		if (chk_logXtfFile.isSelected() && (tf_logXtfFile.getText() == null || tf_logXtfFile.getText().equals(""))) {
			tf_logXtfFile.setStyle("-fx-border-color: red ;");
			result = false;
		}

		if (chk_configFile.isSelected() && (tf_configFile.getText() == null || tf_configFile.getText().equals(""))) {
			tf_configFile.setStyle("-fx-border-color: red ;");
			result = false;
		}

		return result;
	}
	
	@FXML
	public void onClickBtnBrowseXtfFile(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.xtf"), "*.xtf"));
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.itf"), "*.itf"));

		// TODO Verificar si el campo de texto con el archivo xtf debe ser de
		// solo lectura
		// TODO Verificar si el t�tulo corresponde
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));
		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);
		if (selectedFile != null)
			tf_XtfFile.setText(selectedFile.getAbsolutePath());
	}

	public void onClickChk_additionalFunctionality(ActionEvent event) {
		boolean checked = chk_additionalFunctionality.isSelected();

		tf_pluginsFolder.setDisable(!checked);
		btnBrowsePluginsFolder.setDisable(!checked);

		if (!checked)
			tf_pluginsFolder.setStyle(null);
	}

	public void onClickBtnModelDir(ActionEvent event) {
		try {
			ModelDirDialog dialog = new ModelDirDialog();
			dialog.setTitle(applicationBundle.getString("dialog.modeldir.title"));
			
			if (tf_modelDir.getText() != null && !tf_modelDir.getText().isEmpty())
				dialog.setData(Arrays.asList(tf_modelDir.getText().split(";")));

			Optional<List<String>> result = dialog.showAndWait();

			if (result.isPresent()) {
				tf_modelDir.setText(String.join(";", result.get()));
			}
		} catch (IOException E) {
			E.printStackTrace();
		}
	}

	public void onClickBtnBrowsePluginsFolder(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();

		// TODO Verificar si el campo de texto con la carpeta de plugins debe
		// ser de solo lectura
		// TODO Ajustar el texto de la ventana (dircetoryChoser plugins)
		directoryChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedDirectory = directoryChooser.showDialog(window);

		if (selectedDirectory != null) {
			tf_pluginsFolder.setText(selectedDirectory.getAbsolutePath());
		}
	}

	private void updateParams() {
		params = new HashMap<String, String>();
		params.put(EnumParams.FILE_NAME.getName(), "" + tf_XtfFile.getText() + "");
		// --------------Mappings Options---------------------//

		if (chk_additionalFunctionality.isSelected())
			params.put(EnumParams.IV_PLUGINS.getName(), tf_pluginsFolder.getText());
		
		if (tf_modelDir.getText()!=null && !tf_modelDir.getText().isEmpty()) 
			params.put(EnumParams.MODEL_DIR.getName(),tf_modelDir.getText());
		
		if (chk_configFile.isSelected())
			params.put(EnumParams.IV_CONFIG_FILE.getName(), tf_configFile.getText());
		
		if (chk_logXtfFile.isSelected())
			params.put(EnumParams.IV_XTFLOG.getName(), tf_logXtfFile.getText());
		
		if (chk_disableAreaValidation.isSelected())
			params.put(EnumParams.IV_DISABLEAREAVALIDATION.getName(), "true");
		
		if (chk_forceTypeValidation.isSelected())
			params.put(EnumParams.IV_FORCETYPEVALIDATION.getName(), "true");
	}

	public void onClickBtnBrowseConfigFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter(applicationBundle.getString("general.file.extension.toml"), "*.toml"),
				new ExtensionFilter(applicationBundle.getString("general.file.extension.all"), "*.*"));

		// TODO Validar titulo
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);

		if (selectedFile != null) {
			tf_configFile.setText(selectedFile.getAbsolutePath());
		}
	}

	public void onClickBtnBrowseLogXtfFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.xtf"), "*.xtf"));

		// TODO Validar titulo
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(window);

		if (selectedFile != null) {
			tf_logXtfFile.setText(selectedFile.getAbsolutePath());
		}
	}

	public void onClickChk_configFile(ActionEvent event) {
		boolean checked = chk_configFile.isSelected();

		tf_configFile.setDisable(!checked);
		btnBrowseConfigFile.setDisable(!checked);

		if (!checked)
			tf_configFile.setStyle(null);
	}

	public void onClickChk_logXtfFile(ActionEvent event) {
		// chk_logXtfFile
		boolean checked = chk_logXtfFile.isSelected();

		tf_logXtfFile.setDisable(!checked);
		btnBrowseLogXtfFile.setDisable(!checked);

		if (!checked)
			tf_logXtfFile.setStyle(null);
	}
	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
}
