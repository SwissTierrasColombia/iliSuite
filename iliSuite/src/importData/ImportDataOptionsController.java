package importData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.data.AppData;
import application.data.Config;
import application.dialog.ModelDirDialog;
import application.dialog.MultipleSelectionDialog;
import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;
import application.util.params.EnumParams;
import application.util.params.ParamsContainer;
import application.util.plugin.PluginsLoader;
import application.util.procedures.ModelSearch;
import base.IPluginDb;
import base.dbconn.Ili2DbScope;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class ImportDataOptionsController implements Navigable, Initializable {

	@FXML
	private ResourceBundle applicationBundle;
	@FXML
	private ToggleGroup tg_action;
	@FXML
	private RadioButton radio_import;
	@FXML
	private RadioButton radio_update;
	@FXML
	private RadioButton radio_replace;
	@FXML
	private RadioButton radio_delete;
	@FXML
	private CheckBox chk_deleteData;
	@FXML
	private TextField tf_datasetEditable;
	@FXML
	private TextField tf_datasetSelectable;
	@FXML
	private TextField tf_modelDir;
	@FXML
	private TextField tf_modelNames;
	@FXML
	private Button btn_browseDataset;
	@FXML
	private TextField tf_xtfPath;
	@FXML
	private Button btn_browseXtfFile;
	@FXML
	private Button btn_browseModels;
	@FXML
	private CheckBox chk_disableValidation;
	@FXML
	private CheckBox chk_disableAreaValidation;
	@FXML
	private CheckBox chk_skipGeometryErrors;
	@FXML
	private CheckBox chk_skipReferenceErrors;
	@FXML
	private CheckBox chk_forceTypeValidation;
	@FXML
	private CheckBox chk_validConfig;
	@FXML
	private TextField tf_validConfigFile;
	@FXML
	private Button btn_browseValidConfig;
	private boolean isScoped = false;
	

	@Override
	public boolean validate() {
		boolean result = validateFields();
		if (result)
			addParams();
		return result;
	}

	@Override
	public EnumPaths getNextPath() {
		return EnumPaths.IMP_DATA_FINISH_DATA_IMPORT;
	}

	@Override
	public boolean isFinalPage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		applicationBundle = resources;
		
		String pluginKey = AppData.getInstance().getPlugin();
		// TODO Verificar si es null
		IPluginDb plugin = (IPluginDb) PluginsLoader.getPluginByKey(pluginKey);
		Ili2DbScope scope = plugin.getScope();
		try{
			isScoped = scope.isScoped();
		}catch(SQLException|ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			if(!isScoped){
				radio_update.setDisable(true);
				radio_replace.setDisable(true);
				radio_delete.setDisable(true);
			}
			tf_datasetEditable.setDisable(true);
			tf_datasetSelectable.setDisable(true);
			btn_browseDataset.setDisable(true);			
		}
		addInitListeners();
		
		tf_modelDir.setText(Config.getInstance().getModelDir());
	}

	private void addInitListeners() {
		tg_action.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (newValue != null) {
					radio_import.setStyle(null);
					radio_update.setStyle(null);
					radio_replace.setStyle(null);
					radio_delete.setStyle(null);
				}

				Boolean importSelected = newValue == radio_import;
				Boolean disableDatasetSelectable = importSelected;
				Boolean disableDatasetEditable = !importSelected && isScoped;
				
				chk_deleteData.setDisable(!importSelected);
				
				tf_datasetSelectable.setDisable(disableDatasetSelectable);
				btn_browseDataset.setDisable(disableDatasetSelectable);
				
				tf_datasetEditable.setDisable(disableDatasetEditable);
				
				tf_datasetSelectable.setStyle(null);
				tf_datasetEditable.setStyle(null);
			}
		});

		tf_xtfPath.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals("") && newValue.endsWith(".xtf"))
					tf_xtfPath.setStyle(null);
			}
		});
		tf_datasetSelectable.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.isEmpty()) {
					tf_datasetSelectable.setStyle(null);
				}
			}
		});
		tf_validConfigFile.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.isEmpty()) {
					tf_validConfigFile.setStyle(null);
				}
			}
		});
		
		chk_validConfig.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == false) {
					tf_validConfigFile.setStyle(null);
					tf_validConfigFile.setDisable(true);
					btn_browseValidConfig.setDisable(true);
				}else{
					tf_validConfigFile.setDisable(false);
					btn_browseValidConfig.setDisable(false);
				}
		    }
		});
		
	}

	private boolean validateFields() {
		boolean result = true;
		if (tg_action.getSelectedToggle() == null) {
			radio_import.setStyle("-fx-border-color: red ; ");
			radio_update.setStyle("-fx-border-color: red ; ");
			radio_replace.setStyle("-fx-border-color: red ; ");
			radio_delete.setStyle("-fx-border-color: red ; ");
			result = false;
		}
		if (tg_action.getSelectedToggle() == radio_delete && tf_datasetSelectable.getText().isEmpty()) {
			tf_datasetSelectable.setStyle("-fx-border-color: red ; ");
			result = false;
		} else if (tg_action.getSelectedToggle() == radio_import && !tf_datasetSelectable.getText().isEmpty()) {
			tf_datasetSelectable.setStyle("-fx-border-color: red ; ");
			result = false;
		} else if (tg_action.getSelectedToggle() == radio_replace && tf_datasetSelectable.getText().isEmpty()) {
			tf_datasetSelectable.setStyle("-fx-border-color: red ; ");
			result = false;
		}

		if (tg_action.getSelectedToggle() != radio_delete && (tf_xtfPath.getText() == null || tf_xtfPath.getText().equals("") || !tf_xtfPath.getText().endsWith(".xtf"))) {
			tf_xtfPath.setStyle("-fx-border-color: red ;");
			result = false;
		}
		
		if (!chk_disableValidation.isSelected() && (chk_validConfig.isSelected() && (tf_validConfigFile.getText() == null || 
				tf_validConfigFile.getText().isEmpty() || 
				!tf_validConfigFile.getText().endsWith(".toml")))) {
			tf_validConfigFile.setStyle("-fx-border-color: red ;");
			result = false;
		}

		return result;
	}

	@FXML
	private void handleAddDatasetButton(ActionEvent e) {
		String pluginKey = AppData.getInstance().getPlugin();
		IPluginDb plugin = (IPluginDb) PluginsLoader.getPluginByKey(pluginKey);
		Ili2DbScope scope = plugin.getScope();

		try {
			ArrayList<String> selectedValues = new ArrayList<>();

			if (!tf_datasetSelectable.getText().isEmpty()) {
				selectedValues = new ArrayList<String>(Arrays.asList(tf_datasetSelectable.getText().split(";")));
			}

			MultipleSelectionDialog dialog = new MultipleSelectionDialog(scope.getDatasetList(), selectedValues,SelectionMode.SINGLE);

			dialog.setTitle(applicationBundle.getString("general.dataset"));
			Optional<List<String>> result = dialog.showAndWait();

			if (result.isPresent()) {
				tf_datasetSelectable.setText(String.join(";", result.get()));
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	private void handleBrowseXtfFile(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.xtf"), "*.xtf"));
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));
		Window window = ((Node) e.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);
		if (selectedFile != null)
			tf_xtfPath.setText(selectedFile.getAbsolutePath());
	}
	
	@FXML
	private void handleBrowseModelDir(ActionEvent e){
		try {
	        ModelDirDialog dialog = new ModelDirDialog();
			dialog.setTitle(applicationBundle.getString("dialog.modeldir.title"));
            
	        if (tf_modelDir.getText() != null && !tf_modelDir.getText().isEmpty())
	        	dialog.setData(Arrays.asList(tf_modelDir.getText().split(";")));
            
            Optional<List<String>> result = dialog.showAndWait();
	        
            if(result.isPresent()){
				tf_modelDir.setText(String.join(";", result.get()));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	@FXML
	private void handleBrowseModels(ActionEvent e){
		if(tf_modelDir.getText().isEmpty()) {
			tf_modelDir.setStyle("-fx-border-color: red ;");
		}else {
			tf_modelDir.setStyle(null);
			List<String> models = ModelSearch.search(tf_modelDir.getText());//SEARCH in modeldir
			
			List<String> selected = new ArrayList<String>();
			if(!tf_modelNames.getText().isEmpty())
				selected = Arrays.asList(tf_modelNames.getText().split(";"));
			
			models.removeAll(selected);
			MultipleSelectionDialog dialog = 
					new MultipleSelectionDialog(models, selected, SelectionMode.MULTIPLE);
			
			dialog.setTitle(applicationBundle.getString("general.models"));
			
			Optional<List<String>> result = dialog.showAndWait();

			if(result.isPresent())
				tf_modelNames.setText(String.join(";", result.get()));
		}
	}
	
	@FXML
	private void handleBrowseValidConfigFile(ActionEvent e){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter(applicationBundle.getString("general.file.extension.toml"), "*.toml"),
				new ExtensionFilter(applicationBundle.getString("general.file.extension.all"), "*.*"));

		// TODO Validar titulo
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) e.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);

		if (selectedFile != null) {
			tf_validConfigFile.setText(selectedFile.getAbsolutePath());
		}
	}
	
	@FXML
	private void handleChkDisableValidation(ActionEvent e){
		boolean disableValidation = chk_disableValidation.isSelected();
		
		chk_disableAreaValidation.setDisable(disableValidation);
		chk_skipGeometryErrors.setDisable(disableValidation);
		chk_skipReferenceErrors.setDisable(disableValidation);
		chk_forceTypeValidation.setDisable(disableValidation);
		chk_validConfig.setDisable(disableValidation);
		tf_validConfigFile.setDisable(disableValidation);
		btn_browseValidConfig.setDisable(disableValidation);
		
		if (disableValidation)
			tf_validConfigFile.setStyle(null);
	}

	private void addParams() {
		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();
		HashMap<String,String> params = paramsContainer.getParamsMap();
		
		if(!tf_modelNames.getText().isEmpty())
			params.put(EnumParams.MODELS.getName(), "" +tf_modelNames.getText()+ "");
		
		if(tg_action.getSelectedToggle() != radio_delete)
			paramsContainer.setFinalPath(tf_xtfPath.getText());
		else
			paramsContainer.setFinalPath("");
		
		if(!tf_modelDir.getText().isEmpty()){
			params.put(EnumParams.MODEL_DIR.getName(), tf_modelDir.getText());
		}else{
			params.remove(EnumParams.MODEL_DIR.getName());
		}
		
		params.remove(EnumParams.DATA_IMPORT.getName());
		params.remove(EnumParams.REPLACE.getName());
		params.remove(EnumParams.UPDATE.getName());
		params.remove(EnumParams.DELETE.getName());
		
		if (radio_import.isSelected()) {
			params.put(EnumParams.DATA_IMPORT.getName(), "true");
			if (chk_deleteData.isSelected())
				params.put(EnumParams.DELETE_DATA.getName(), "true");
			else
				params.remove(EnumParams.DELETE_DATA.getName());

		} else if (radio_replace.isSelected()) {
			params.put(EnumParams.REPLACE.getName(), "true");
		} else if (radio_delete.isSelected()) {
			params.put(EnumParams.DELETE.getName(), "true");
		} else if (radio_update.isSelected()) {
			params.put(EnumParams.UPDATE.getName(), "true");
		}

		if (!tf_datasetEditable.getText().isEmpty() && !tf_datasetEditable.isDisabled())
			params.put(EnumParams.DATASET.getName(),tf_datasetEditable.getText());
		else if (!tf_datasetSelectable.getText().isEmpty() && !tf_datasetSelectable.isDisabled())
			params.put(EnumParams.DATASET.getName(),tf_datasetSelectable.getText());
		else
			params.remove(EnumParams.DATASET.getName());
		
		if(!chk_disableValidation.isSelected()){
			params.remove(EnumParams.DISABLE_VALIDATION.getName());
			if(chk_disableAreaValidation.isSelected())
				params.put(EnumParams.DISABLE_AREA_VALIDATION.getName(), "true");
			else
				params.remove(EnumParams.DISABLE_AREA_VALIDATION.getName());
			if(chk_skipGeometryErrors.isSelected())
				params.put(EnumParams.SKIP_GEOMETRY_ERRORS.getName(), "true");
			else
				params.remove(EnumParams.SKIP_GEOMETRY_ERRORS.getName());
			if(chk_skipReferenceErrors.isSelected())
				params.put(EnumParams.SKIP_REFERENCE_ERRORS.getName(), "true");
			else
				params.remove(EnumParams.SKIP_REFERENCE_ERRORS.getName());
			if(chk_forceTypeValidation.isSelected())
				params.put(EnumParams.FORCE_TYPE_VALIDATION.getName(), "true");
			else
				params.remove(EnumParams.FORCE_TYPE_VALIDATION.getName());
			if(chk_validConfig.isSelected())
				params.put(EnumParams.VALID_CONFIG.getName(), tf_validConfigFile.getText());
			else
				params.remove(EnumParams.VALID_CONFIG.getName());
		}else{
			params.put(EnumParams.DISABLE_VALIDATION.getName(), "true");
			params.remove(EnumParams.DISABLE_AREA_VALIDATION.getName());
			params.remove(EnumParams.SKIP_GEOMETRY_ERRORS.getName());
			params.remove(EnumParams.SKIP_REFERENCE_ERRORS.getName());
			params.remove(EnumParams.FORCE_TYPE_VALIDATION.getName());
			params.remove(EnumParams.VALID_CONFIG.getName());
		}

	}

}
