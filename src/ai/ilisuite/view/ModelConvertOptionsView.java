package ai.ilisuite.view;

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

import ai.ilisuite.application.data.Config;
import ai.ilisuite.controller.ParamsController;
import ai.ilisuite.impl.PanelCustomizable;
import ai.ilisuite.util.params.EnumParams;
import ai.ilisuite.util.procedures.ModelSearch;
import ai.ilisuite.view.dialog.ModelDirDialog;
import ai.ilisuite.view.dialog.MultipleSelectionDialog;
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

public class ModelConvertOptionsView extends StepViewController implements Initializable {

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
	private RadioButton radio_createEnumTabsWithId;
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
	
	private PanelCustomizable customPanelSchemaImport;
	
	private Parent viewRootNode;
	
	private ParamsController controller;
	
	private Map<String,String> params;

	public ModelConvertOptionsView(ParamsController controller) throws IOException {
		// XXX Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource(EnumPaths.MODEL_CONVERT_OPTIONS, EnumPaths.RESOURCE_BUNDLE, this);
		
		this.controller = controller;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		applicationBundle = resources;
		addInitListeners();
		addInitValues();
		
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
		tf_modelDir.setText(Config.getInstance().getModelDir());
		tf_srsAuth.setPromptText("EPSG");
		tf_srsCode.setPromptText("21781");
		
		radio_smart2.setSelected(true);
		radio_inputFile.setSelected(true);
		radio_createEnumTabsWithId.setSelected(true);
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
				models.sort(null);
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
	
	private void updateParams(){
		params = new HashMap<String, String>();
		
		if(radio_inputFile.isSelected())
			params.put(EnumParams.FILE_NAME.getName(), "" +tf_iliFilePath.getText()+ "" );
		else if(radio_inputModels.isSelected())
			params.put(EnumParams.MODELS.getName(), "" +tf_iliFilePath.getText()+ "");
		
		//--------------Mappings Options---------------------//
		if(!tf_modelDir.getText().isEmpty())
			params.put(EnumParams.MODEL_DIR.getName(), "" +tf_modelDir.getText()+ "");
		
		if(radio_noSmart.isSelected()){
			params.put(EnumParams.NO_SMART_MAPPING.getName(), "true");
		} else if(radio_smart1.isSelected()){
			params.put(EnumParams.SMART_1_INHERITANCE.getName(), "true");
		} else if(radio_smart2.isSelected()){
			params.put(EnumParams.SMART_2_INHERITANCE.getName(), "true");
		}
		
		if(radio_createEnumTabs.isSelected()){
			params.put(EnumParams.CREATE_ENUM_TABS.getName(), "true");
		} else if(radio_createSingleEnumTab.isSelected()){
			params.put(EnumParams.CREATE_SINGLE_ENUM_TAB.getName(), "true");
		} else if(radio_createEnumTxtCol.isSelected()){
			params.put(EnumParams.CREATE_ENUM_TXT_COL.getName(), "true");
		} else if(radio_createEnumTabsWithId.isSelected()) {
			params.put(EnumParams.CREATE_ENUM_TABS_WITH_ID.getName(), "true");
		}
		
		if(chk_beautifyEnumDispName.isSelected())
			params.put(EnumParams.BEAUTIFY_ENUM_DISP_NAME.getName(), "true");

		if(chk_coalesceCatalogueRef.isSelected())
			params.put(EnumParams.COALESCE_CATALOGUE_REF.getName(), "true");

		if(chk_coalesceMultisurface.isSelected())
			params.put(EnumParams.COALESCE_MULTISURFACE.getName(), "true");

		if(chk_expandMultiLingual.isSelected())
			params.put(EnumParams.EXPAND_MULTILINGUAL.getName(), "true");

		if(chk_coalesceMultiLine.isSelected())
			params.put(EnumParams.COALESCE_MULTILINE.getName(), "true");

		if(tf_srsAuth.getText()!=null && !tf_srsAuth.getText().equals(""))
			params.put(EnumParams.DEFAULT_SRS_AUTH.getName(), tf_srsAuth.getText());

		if(tf_srsCode.getText()!=null && !tf_srsCode.getText().equals(""))
			params.put(EnumParams.DEFAULT_SRS_CODE.getName(), tf_srsCode.getText());

		if(chk_strokeArgs.isSelected())
			params.put(EnumParams.STROKE_ARCS.getName(), "true");

		if(chk_oneGeomPerTable.isSelected())
			params.put(EnumParams.ONE_GEOM_PER_TABLE.getName(), "true");

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

		if(tf_t_id_name.getText()!=null && !tf_t_id_name.getText().equals(""))
			params.put(EnumParams.T_ID_NAME.getName(), tf_t_id_name.getText());

		if(chk_createTypeDiscriminator.isSelected())
			params.put(EnumParams.CREATE_TYPE_DISCRIMINATOR.getName(), "true");

		if(chk_iliMetaAttrs.isSelected())
			params.put(EnumParams.ILI_META_ATTRS.getName(), "true");

		if(chk_importTid.isSelected())
			params.put(EnumParams.IMPORT_TID.getName(), "true");

		if(chk_createBasketCol.isSelected())
			params.put(EnumParams.CREATE_BASKET_COL.getName(), "true");

		if(chk_createDatasetCol.isSelected())
			params.put(EnumParams.CREATE_DATASET_COL.getName(), "true");

		if(chk_createMetaInfo.isSelected())
			params.put(EnumParams.CREATE_METAINFO.getName(), "true");

		if(chk_ver4translation.isSelected())
			params.put(EnumParams.VER4_TRANSLATION.getName(), "true");

		if(tf_idSeqMin.getText()!=null && !tf_idSeqMin.getText().equals(""))
			params.put(EnumParams.ID_SEQ_MIN.getName(), tf_idSeqMin.getText());

		if(tf_idSeqMax.getText()!=null && !tf_idSeqMax.getText().equals(""))
			params.put(EnumParams.ID_SEQ_MAX.getName(), tf_idSeqMax.getText());

		if(tf_createScriptPath.getText()!=null && !tf_createScriptPath.getText().equals("") && tf_createScriptPath.getText().endsWith(".sql"))
			params.put(EnumParams.CREATE_SCRIPT.getName(),  "" +tf_createScriptPath.getText()+ "" );

		if(tf_dropScriptPath.getText()!=null && !tf_dropScriptPath.getText().equals("") && tf_dropScriptPath.getText().endsWith(".sql"))
			params.put(EnumParams.DROP_SCRIPT.getName(), "" +tf_dropScriptPath.getText()+"");
		
		updateCustomParams();
	}
	
	private void updateCustomParams() {
		if(customPanelSchemaImport != null) {			
			params.putAll(customPanelSchemaImport.getParams());
		}
	}

	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
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

	public void setCustomPanel(PanelCustomizable customPanel) {
		this.customPanelSchemaImport = customPanel;
		
		if(this.customPanelSchemaImport != null) {
			Tab tab = new Tab(customPanelSchemaImport.getName());
			tab.setContent(customPanelSchemaImport.getPanel());
			tabOptions.getTabs().add(tab);
		}
	}
}
