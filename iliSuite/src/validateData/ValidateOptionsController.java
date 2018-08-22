package validateData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.data.AppData;
import application.data.Config;
import application.dialog.ModelDirDialog;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.params.EnumParams;
import application.util.params.ParamsContainer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

public class ValidateOptionsController implements Navigable, Initializable {

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AppData data = AppData.getInstance();
		data.getParamsContainer().getParamsMap().clear();
		
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
	public boolean validate() {
		boolean result = validateFields();
		if (result)
			addParams();
		return result;
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

	@Override
	public EnumPaths getNextPath() {
		return EnumPaths.VAL_DATA_FINISH_VALIDATION;
	}

	@Override
	public boolean isFinalPage() {
		return false;
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

	private void addParams() {
		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();
		HashMap<String,String> params = paramsContainer.getParamsMap();
		
		paramsContainer.setFinalPath("" + tf_XtfFile.getText() + "");

		// --------------Mappings Options---------------------//

		if (chk_additionalFunctionality.isSelected())
			params.put(EnumParams.IV_PLUGINS.getName(), tf_pluginsFolder.getText());
		else
			params.remove(EnumParams.IV_PLUGINS.getName());
		
		if (tf_modelDir.getText()!=null && !tf_modelDir.getText().isEmpty()) 
			params.put(EnumParams.IV_MODELDIR.getName(),tf_modelDir.getText());
		else
			params.remove(EnumParams.IV_MODELDIR.getName());
		
		if (chk_configFile.isSelected())
			params.put(EnumParams.IV_CONFIG_FILE.getName(), tf_configFile.getText());
		else
			params.remove(EnumParams.IV_CONFIG_FILE.getName());
		
		if (chk_logXtfFile.isSelected())
			params.put(EnumParams.IV_XTFLOG.getName(), tf_logXtfFile.getText());
		else
			params.remove(EnumParams.IV_XTFLOG.getName());
		
		if (chk_disableAreaValidation.isSelected())
			params.put(EnumParams.IV_DISABLEAREAVALIDATION.getName(), "true");
		else
			params.remove(EnumParams.IV_DISABLEAREAVALIDATION.getName());
		
		if (chk_forceTypeValidation.isSelected())
			params.put(EnumParams.IV_FORCETYPEVALIDATION.getName(), "true");
		else 
			params.remove(EnumParams.IV_FORCETYPEVALIDATION.getName());
		
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

	public void onClickBtnBrowseLogFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.log"), "*.log"));

		// TODO Validar titulo
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(window);
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
}
