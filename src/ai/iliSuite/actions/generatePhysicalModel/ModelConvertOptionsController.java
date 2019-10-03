package ai.iliSuite.actions.generatePhysicalModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import ai.iliSuite.application.data.AppData;
import ai.iliSuite.application.data.Config;
import ai.iliSuite.impl.EnumCustomPanel;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.PanelCustomizable;
import ai.iliSuite.util.params.EnumParams;
import ai.iliSuite.util.params.ParamsContainer;
import ai.iliSuite.util.plugin.PluginsLoader;
import ai.iliSuite.util.procedures.ModelSearch;
import ai.iliSuite.view.dialog.ModelDirDialog;
import ai.iliSuite.view.dialog.MultipleSelectionDialog;
import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.Navigable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class ModelConvertOptionsController implements Navigable, Initializable {

	@FXML
	private ResourceBundle applicationBundle;
	@FXML
	private ToggleGroup tg_inheritance;
	@FXML
	private ToggleGroup tg_creationEnums;
	@FXML
	private ToggleGroup tg_inputSelection;
	@FXML
	private RadioButton radio_inputModels;
	@FXML
	private RadioButton radio_inputFile;
	@FXML
	private RadioButton radio_noSmart;
	@FXML
	private RadioButton radio_smart1;
	@FXML
	private RadioButton radio_smart2;
	@FXML
	private RadioButton radio_createEnumTabs;
	@FXML
	private RadioButton radio_createSingleEnumTab;
	@FXML
	private RadioButton radio_createEnumTxtCol;
	@FXML
	private CheckBox chk_beautifyEnumDispName;
	@FXML
	private CheckBox chk_coalesceCatalogueRef;
	@FXML
	private CheckBox chk_coalesceMultisurface;
	@FXML
	private CheckBox chk_coalesceMultiLine;
	@FXML
	private CheckBox chk_expandMultiLingual;
	@FXML
	private TextField tf_srsAuth;
	@FXML
	private TextField tf_srsCode;
	@FXML
	private CheckBox chk_strokeArgs;
	@FXML
	private CheckBox chk_oneGeomPerTable;
	@FXML
	private CheckBox chk_disableNameOptimization;
	@FXML
	private CheckBox chk_nameByTopic;
	@FXML
	private TextField tf_maxNameLength;
	@FXML
	private CheckBox chk_sqlEnableNull;
	@FXML
	private CheckBox chk_createFk;
	@FXML
	private CheckBox chk_createUnique;
	@FXML
	private CheckBox chk_createNumChecks;
	@FXML
	private CheckBox chk_createGeomIdx;
	@FXML
	private CheckBox chk_createFkIdx;
	@FXML
	private CheckBox chk_createStdCols;
	@FXML
	private TextField tf_t_id_name;
	@FXML
	private CheckBox chk_createTypeDiscriminator;
	@FXML
	private CheckBox chk_iliMetaAttrs;
	@FXML
	private CheckBox chk_importTid;
	@FXML
	private CheckBox chk_createBasketCol;
	@FXML
	private CheckBox chk_createDatasetCol;
	@FXML
	private CheckBox chk_createMetaInfo;
	@FXML
	private CheckBox chk_ver4translation;
	@FXML
	private TextField tf_idSeqMin;
	@FXML
	private TextField tf_idSeqMax;
	@FXML
	private TextField tf_createScriptPath;
	@FXML
	private TextField tf_dropScriptPath;
	@FXML
	private TextField tf_iliFilePath;
	
	@FXML
	private TextField tf_modelDir;
	
	@FXML
	private TabPane tabOptions;
	
	private ImplFactory plugin;
	
	PanelCustomizable customPanelSchemaImport;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		applicationBundle = resources;
		addInitListeners();
		
		tf_modelDir.setText(Config.getInstance().getModelDir());
		tf_srsAuth.setPromptText("EPSG");
		tf_srsCode.setPromptText("21781");
		
		addInitValues();
		
		String pluginKey = AppData.getInstance().getPlugin();
		plugin = (ImplFactory) PluginsLoader.getPluginByKey(pluginKey);
		
		Map<EnumCustomPanel, PanelCustomizable> lstCustomPanel = plugin.getCustomPanels();
		
		if(lstCustomPanel != null) {
			customPanelSchemaImport = lstCustomPanel.get(EnumCustomPanel.SCHEMA_IMPORT);
			if(customPanelSchemaImport != null) {
				Tab tab = new Tab(customPanelSchemaImport.getName());
				tab.setContent(customPanelSchemaImport.getPanel());
				tabOptions.getTabs().add(tab);
			}
		}
	}

	@Override
	public boolean validate() {
		boolean result = validateFields();
		if(result) {
			addParams();
			addCustomParams();
		}
		return result;
	}
	

	@Override
	public EnumPaths getNextPath() {
		return EnumPaths.FINISH_MODEL_GENERATION;
	}

	@Override
	public boolean isFinalPage() {
		return false;
	}
	
	private void addInitListeners(){
		
		tf_iliFilePath.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if(newValue!=null && !newValue.equals("") && newValue.endsWith(".ili"))
					tf_iliFilePath.setStyle(null);
			}
		});
		
		tg_inheritance.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (newValue != null) {
					radio_noSmart.setStyle(null);
					radio_smart1.setStyle(null);
					radio_smart2.setStyle(null);
				}
			}
		});
		
		tf_srsAuth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if(!newValue.matches("[\\w.]*")){
					tf_srsAuth.setText(newValue.replaceAll("[^\\w.]", ""));
				}
				if (tf_srsAuth.getText().length() > 50) {
	                String s = tf_srsAuth.getText().substring(0, 50);
	                tf_srsAuth.setText(s);
	            }
			}
		});
		tf_srsCode.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	tf_srsCode.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	            if (tf_srsCode.getText().length() > 7) {
	                String s = tf_srsCode.getText().substring(0, 7);
	                tf_srsCode.setText(s);
	            }
	        }
	    });
		tf_maxNameLength.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	tf_maxNameLength.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	            if (tf_maxNameLength.getText().length() > 4) {
	                String s = tf_maxNameLength.getText().substring(0, 4);
	                tf_maxNameLength.setText(s);
	            }
	        }
		});
		tf_t_id_name.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\w*")) {
	            	tf_t_id_name.setText(newValue.replaceAll("[^\\w]", ""));
	            }
	            if (tf_t_id_name.getText().length() > 50) {
	                String s = tf_t_id_name.getText().substring(0, 50);
	                tf_t_id_name.setText(s);
	            }
	        }
		});
		tf_idSeqMin.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	tf_idSeqMin.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	            if (tf_idSeqMin.getText().length() > 19) {
	                String s = tf_idSeqMin.getText().substring(0, 19);
	                tf_idSeqMin.setText(s);
	            }
	        }
		});
		tf_idSeqMax.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	tf_idSeqMax.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	            if (tf_idSeqMax.getText().length() > 19) {
	                String s = tf_idSeqMax.getText().substring(0, 19);
	                tf_idSeqMax.setText(s);
	            }
	        }
		});
		
		tf_createScriptPath.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (newValue.endsWith(".sql")) {
	            	tf_createScriptPath.setStyle(null);
	            }
	        }
		});
		tf_dropScriptPath.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (newValue.endsWith(".sql")) {
	            	tf_dropScriptPath.setStyle(null);
	            }
	        }
		});
	}
	
	private void addInitValues(){
		radio_smart2.setSelected(true);
		radio_inputFile.setSelected(true);
		radio_createEnumTabs.setSelected(true);
		chk_beautifyEnumDispName.setSelected(true);
		chk_coalesceCatalogueRef.setSelected(true);
		chk_coalesceMultisurface.setSelected(true);
		chk_coalesceMultiLine.setSelected(true);
		chk_strokeArgs.setSelected(true);
		chk_createFk.setSelected(true);
		chk_createUnique.setSelected(true);
		chk_createNumChecks.setSelected(true);
		chk_createGeomIdx.setSelected(true);
		chk_createFkIdx.setSelected(true);
		chk_createMetaInfo.setSelected(true);
		tf_srsCode.setText("3116");
	}
	
	private boolean validateFields(){
		boolean result = true;
		if (tg_inheritance.getSelectedToggle() == null) {
			radio_noSmart.setStyle("-fx-border-color: red ; ");
			radio_smart1.setStyle("-fx-border-color: red ; ");
			radio_smart2.setStyle("-fx-border-color: red ; ");
			result = false;
		}
		if(tf_iliFilePath.getText() == null || tf_iliFilePath.getText().equals("") || (radio_inputFile.isSelected() && !tf_iliFilePath.getText().endsWith(".ili"))){
			tf_iliFilePath.setStyle("-fx-border-color: red ;");
			result = false;
		}
		if(!tf_createScriptPath.getText().equals("") && !tf_createScriptPath.getText().endsWith(".sql")){
			tf_createScriptPath.setStyle("-fx-border-color: red;");
			result = false;
		}
		if(!tf_dropScriptPath.getText().equals("") && !tf_dropScriptPath.getText().endsWith(".sql")){
			tf_dropScriptPath.setStyle("-fx-border-color: red;");
			result = false;
		}
		
		return result;
	}
	
	public void onClickBrowseCreateScript(ActionEvent e){
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.sql"),"*.sql"));
		fileChooser.setTitle(applicationBundle.getString("general.file.saveAs"));
		Window window = ((Node)e.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(window);
		if(selectedFile!=null)
			tf_createScriptPath.setText(selectedFile.getAbsolutePath());
	}
	
	public void onClickBrowseDropScript(ActionEvent e){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.sql"),"*.sql"));
		fileChooser.setTitle(applicationBundle.getString("general.file.saveAs"));
		Window window = ((Node)e.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(window);
		if(selectedFile!=null)
			tf_dropScriptPath.setText(selectedFile.getAbsolutePath());
	}
	
	public void onClickBrowseIliFile(ActionEvent e){
			tf_modelDir.setStyle(null);
		if(radio_inputFile.isSelected()) {
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.ili"), "*.ili"));
			fileChooser.setTitle(applicationBundle.getString("general.file.choose"));
			Window window = ((Node)e.getSource()).getScene().getWindow();
			File selectedFile = fileChooser.showOpenDialog(window);
			if(selectedFile!=null)
				tf_iliFilePath.setText(selectedFile.getAbsolutePath());
		}else if(radio_inputModels.isSelected()) {
			
			if(tf_modelDir.getText().isEmpty()) {
				tf_modelDir.setStyle("-fx-border-color: red ;");
			}else {
				tf_modelDir.setStyle(null);
				List<String> models = ModelSearch.search(tf_modelDir.getText());//SEARCH in modeldir
				
				List<String> selected = new ArrayList<String>();
				if(!tf_iliFilePath.getText().isEmpty() && !tf_iliFilePath.getText().endsWith(".ili"))
					selected = Arrays.asList(tf_iliFilePath.getText().split(";"));
				
				models.removeAll(selected);
				MultipleSelectionDialog dialog = 
						new MultipleSelectionDialog(models, selected, SelectionMode.MULTIPLE);
				
				dialog.setTitle(applicationBundle.getString("general.models"));
				
				Optional<List<String>> result = dialog.showAndWait();
	
				if(result.isPresent())
					tf_iliFilePath.setText(String.join(";", result.get()));
			}
			
			
		}
	}
	
	public void onClickSetModelDir(ActionEvent e){
		tf_modelDir.setStyle(null);
		try {
	        
	        ModelDirDialog dialog = new ModelDirDialog();
			dialog.setTitle(applicationBundle.getString("dialog.modeldir.title"));
            
	        if (tf_modelDir.getText() != null && !tf_modelDir.getText().isEmpty())
	        	dialog.setData(Arrays.asList(tf_modelDir.getText().split(";")));
	        
            
            Optional<List<String>> result = dialog.showAndWait();
	        
            if(result.isPresent()){
				tf_modelDir.setText(String.join(";", result.get()));
            }

            //return controller.isOkButton();
        } catch (IOException E) {
            E.printStackTrace();
            //return false;
        }
	}
	
	public void onSelectCreateDatasetCol(ActionEvent e){
		
		if(chk_createDatasetCol.isSelected())
			chk_createBasketCol.setSelected(true);
	}
	
	public void onSelectCreateBasketCol(ActionEvent e){
		
		if(!chk_createBasketCol.isSelected())
			chk_createDatasetCol.setSelected(false);
	}
	
	
	private void addParams(){
		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();
		HashMap<String,String> params = paramsContainer.getParamsMap();
		
		if(radio_inputFile.isSelected())
			paramsContainer.setFinalPath( "" +tf_iliFilePath.getText()+ "" );
		else if(radio_inputModels.isSelected())
			params.put(EnumParams.MODELS.getName(), "" +tf_iliFilePath.getText()+ "");
		//--------------Mappings Options---------------------//
		
		if(!tf_modelDir.getText().isEmpty()){
			params.put(EnumParams.MODEL_DIR.getName(), "" +tf_modelDir.getText()+ "");
		}else{
			params.remove(EnumParams.MODEL_DIR.getName());
		}
		
		
		if(radio_noSmart.isSelected()){
			params.put(EnumParams.NO_SMART_MAPPING.getName(), "true");
			params.remove(EnumParams.SMART_1_INHERITANCE.getName());
			params.remove(EnumParams.SMART_2_INHERITANCE.getName());
		}
		else if(radio_smart1.isSelected()){
			params.put(EnumParams.SMART_1_INHERITANCE.getName(), "true");
			params.remove(EnumParams.NO_SMART_MAPPING.getName());
			params.remove(EnumParams.SMART_2_INHERITANCE.getName());
		}
		else if(radio_smart2.isSelected()){
			params.put(EnumParams.SMART_2_INHERITANCE.getName(), "true");
			params.remove(EnumParams.NO_SMART_MAPPING.getName());
			params.remove(EnumParams.SMART_1_INHERITANCE.getName());
		}
		if(radio_createEnumTabs.isSelected()){
			params.put(EnumParams.CREATE_ENUM_TABS.getName(), "true");
			params.remove(EnumParams.CREATE_SINGLE_ENUM_TAB.getName());
			params.remove(EnumParams.CREATE_ENUM_TXT_COL.getName());
		}else if(radio_createSingleEnumTab.isSelected()){
			params.put(EnumParams.CREATE_SINGLE_ENUM_TAB.getName(), "true");
			params.remove(EnumParams.CREATE_ENUM_TABS.getName());
			params.remove(EnumParams.CREATE_ENUM_TXT_COL.getName());
		}else if(radio_createEnumTxtCol.isSelected()){
			params.put(EnumParams.CREATE_ENUM_TXT_COL.getName(), "true");
			params.remove(EnumParams.CREATE_ENUM_TABS.getName());
			params.remove(EnumParams.CREATE_SINGLE_ENUM_TAB.getName());
		}
		
		if(chk_beautifyEnumDispName.isSelected())
			params.put(EnumParams.BEAUTIFY_ENUM_DISP_NAME.getName(), "true");
		else
			params.remove(EnumParams.BEAUTIFY_ENUM_DISP_NAME.getName());
		if(chk_coalesceCatalogueRef.isSelected())
			params.put(EnumParams.COALESCE_CATALOGUE_REF.getName(), "true");
		else
			params.remove(EnumParams.COALESCE_CATALOGUE_REF.getName());
		if(chk_coalesceMultisurface.isSelected())
			params.put(EnumParams.COALESCE_MULTISURFACE.getName(), "true");
		else
			params.remove(EnumParams.COALESCE_MULTISURFACE.getName());
		if(chk_expandMultiLingual.isSelected())
			params.put(EnumParams.EXPAND_MULTILINGUAL.getName(), "true");
		else
			params.remove(EnumParams.EXPAND_MULTILINGUAL.getName());
		if(chk_coalesceMultiLine.isSelected())
			params.put(EnumParams.COALESCE_MULTILINE.getName(), "true");
		else
			params.remove(EnumParams.COALESCE_MULTILINE.getName(), "true");
		if(tf_srsAuth.getText()!=null && !tf_srsAuth.getText().equals(""))
			params.put(EnumParams.DEFAULT_SRS_AUTH.getName(), tf_srsAuth.getText());
		else
			params.remove(EnumParams.DEFAULT_SRS_AUTH.getName());
		if(tf_srsCode.getText()!=null && !tf_srsCode.getText().equals(""))
			params.put(EnumParams.DEFAULT_SRS_CODE.getName(), tf_srsCode.getText());
		else
			params.remove(EnumParams.DEFAULT_SRS_CODE.getName());
		if(chk_strokeArgs.isSelected())
			params.put(EnumParams.STROKE_ARCS.getName(), "true");
		else
			params.remove(EnumParams.STROKE_ARCS.getName());
		if(chk_oneGeomPerTable.isSelected())
			params.put(EnumParams.ONE_GEOM_PER_TABLE.getName(), "true");
		else
			params.remove(EnumParams.ONE_GEOM_PER_TABLE.getName());
		//------------DDL Options-----------//
		if(chk_disableNameOptimization.isSelected())
			params.put(EnumParams.DISABLE_NAME_OPTIMIZATION.getName(), "true");
		else
			params.remove(EnumParams.DISABLE_NAME_OPTIMIZATION.getName());
		if(chk_nameByTopic.isSelected())
			params.put(EnumParams.NAME_BY_TOPIC.getName(), "true");
		else
			params.remove(EnumParams.NAME_BY_TOPIC.getName());
		if(tf_maxNameLength.getText()!=null && !tf_maxNameLength.getText().equals(""))
			params.put(EnumParams.MAX_NAME_LENGTH.getName(), tf_maxNameLength.getText());
		else
			params.remove(EnumParams.MAX_NAME_LENGTH.getName());
		if(chk_sqlEnableNull.isSelected())
			params.put(EnumParams.SQL_ENABLE_NULL.getName(), "true");
		else
			params.remove(EnumParams.SQL_ENABLE_NULL.getName());
		if(chk_createFk.isSelected())
			params.put(EnumParams.CREATE_FK.getName(), "true");
		else
			params.remove(EnumParams.CREATE_FK.getName());
		if(chk_createUnique.isSelected())
			params.put(EnumParams.CREATE_UNIQUE.getName(), "true");
		else
			params.remove(EnumParams.CREATE_UNIQUE.getName());
		if(chk_createNumChecks.isSelected())
			params.put(EnumParams.CREATE_NUM_CHECKS.getName(), "true");
		else
			params.remove(EnumParams.CREATE_NUM_CHECKS.getName());
		if(chk_createGeomIdx.isSelected())
			params.put(EnumParams.CREATE_GEOM_IDX.getName(), "true");
		else
			params.remove(EnumParams.CREATE_GEOM_IDX.getName());
		if(chk_createFkIdx.isSelected())
			params.put(EnumParams.CREATE_FK_IDX.getName(), "true");
		else
			params.remove(EnumParams.CREATE_FK_IDX.getName());
		//-------------Metainformation/Miscellaneous Options -----------------------//
		if(chk_createStdCols.isSelected())
			params.put(EnumParams.CREATE_STD_COLS.getName(), "true");
		else
			params.remove(EnumParams.CREATE_STD_COLS.getName());
		if(tf_t_id_name.getText()!=null && !tf_t_id_name.getText().equals(""))
			params.put(EnumParams.T_ID_NAME.getName(), tf_t_id_name.getText());
		else
			params.remove(EnumParams.T_ID_NAME.getName());
		if(chk_createTypeDiscriminator.isSelected())
			params.put(EnumParams.CREATE_TYPE_DISCRIMINATOR.getName(), "true");
		else
			params.remove(EnumParams.CREATE_TYPE_DISCRIMINATOR.getName());
		if(chk_iliMetaAttrs.isSelected())
			params.put(EnumParams.ILI_META_ATTRS.getName(), "true");
		else
			params.remove(EnumParams.ILI_META_ATTRS.getName());
		if(chk_importTid.isSelected())
			params.put(EnumParams.IMPORT_TID.getName(), "true");
		else
			params.remove(EnumParams.IMPORT_TID.getName());
		if(chk_createBasketCol.isSelected())
			params.put(EnumParams.CREATE_BASKET_COL.getName(), "true");
		else
			params.remove(EnumParams.CREATE_BASKET_COL.getName());
		if(chk_createDatasetCol.isSelected())
			params.put(EnumParams.CREATE_DATASET_COL.getName(), "true");
		else
			params.remove(EnumParams.CREATE_DATASET_COL.getName());
		if(chk_createMetaInfo.isSelected())
			params.put(EnumParams.CREATE_METAINFO.getName(), "true");
		else
			params.remove(EnumParams.CREATE_METAINFO.getName());
		if(chk_ver4translation.isSelected())
			params.put(EnumParams.VER4_TRANSLATION.getName(), "true");
		else
			params.remove(EnumParams.VER4_TRANSLATION.getName());
		if(tf_idSeqMin.getText()!=null && !tf_idSeqMin.getText().equals(""))
			params.put(EnumParams.ID_SEQ_MIN.getName(), tf_idSeqMin.getText());
		else
			params.remove(EnumParams.ID_SEQ_MIN.getName());
		if(tf_idSeqMax.getText()!=null && !tf_idSeqMax.getText().equals(""))
			params.put(EnumParams.ID_SEQ_MAX.getName(), tf_idSeqMax.getText());
		else
			params.remove(EnumParams.ID_SEQ_MAX.getName());
		if(tf_createScriptPath.getText()!=null && !tf_createScriptPath.getText().equals("") && tf_createScriptPath.getText().endsWith(".sql"))
			params.put(EnumParams.CREATE_SCRIPT.getName(),  "" +tf_createScriptPath.getText()+ "" );
		else
			params.remove(EnumParams.CREATE_SCRIPT.getName());
		if(tf_dropScriptPath.getText()!=null && !tf_dropScriptPath.getText().equals("") && tf_dropScriptPath.getText().endsWith(".sql"))
			params.put(EnumParams.DROP_SCRIPT.getName(), "" +tf_dropScriptPath.getText()+"");
		else
			params.remove(EnumParams.DROP_SCRIPT.getName());
		
	}
	
	private void addCustomParams() {
		ParamsContainer paramsContainer = AppData.getInstance().getParamsContainer();
		HashMap<String,String> params = paramsContainer.getParamsMap();
		
		if(customPanelSchemaImport != null) {
			for(String key:customPanelSchemaImport.getParamsForRemove()) {
				params.remove(key);
			}
			
			params.putAll(customPanelSchemaImport.getParams());
		}
	}

}
