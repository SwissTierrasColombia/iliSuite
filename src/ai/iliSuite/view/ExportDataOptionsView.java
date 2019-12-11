package ai.iliSuite.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import ai.iliSuite.application.data.Config;
import ai.iliSuite.controller.ParamsController;
import ai.iliSuite.impl.dbconn.Ili2DbScope;
import ai.iliSuite.util.params.EnumParams;
import ai.iliSuite.view.dialog.ModelDirDialog;
import ai.iliSuite.view.dialog.MultipleSelectionDialog;
import ai.iliSuite.view.util.navigation.EnumPaths;
import ai.iliSuite.view.util.navigation.ResourceUtil;
import ai.iliSuite.view.wizard.StepArgs;
import ai.iliSuite.view.wizard.StepViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

public class ExportDataOptionsView  extends StepViewController implements Initializable {

	@FXML
	private ResourceBundle applicationBundle;
	@FXML
	private Button btn_addDataset;
	@FXML
	private Button btn_addBaskets;
	@FXML
	private Button btn_addTopics;
	@FXML
	private Button btn_addModels;
	@FXML
	private Button btn_browseXtfFile;
	@FXML
	private ToggleGroup tg_selectedScope;
	@FXML
	private RadioButton radio_dataset;
	@FXML
	private RadioButton radio_baskets;
	@FXML
	private RadioButton radio_topics;
	@FXML
	private RadioButton radio_models;
	@FXML
	private TextField tf_dataset;
	@FXML
	private TextField tf_baskets;
	@FXML
	private TextField tf_topics;
	@FXML
	private TextField tf_models;
	@FXML
	private TextField tf_xtfFilePath;
	@FXML
	private CheckBox chk_disableRounding;
	
	@FXML
	private TextField tf_modelDir;
	
	private  Ili2DbScope scope;
	
	private ArrayList<Node> disableList;

	private Parent viewRootNode;
	private ParamsController controller;
	private Map<String,String> params;
	
	public ExportDataOptionsView(ParamsController controller) throws IOException {
		// XXX Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource(EnumPaths.EXP_DATA_EXPORT_DATA_OPTIONS, EnumPaths.RESOURCE_BUNDLE, this);
		
		this.controller = controller;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		applicationBundle = resources;
		addInitListeners();
		chk_disableRounding.setSelected(true);
		
		// FIX Modeldir from singleton
		tf_modelDir.setText(Config.getInstance().getModelDir());
		disableList = new ArrayList<>();
		disableList.add(tf_dataset);
		disableList.add(tf_baskets);
		disableList.add(tf_topics);
		disableList.add(tf_models);
		disableList.add(btn_addDataset);
		disableList.add(btn_addBaskets);
		disableList.add(btn_addTopics);
		disableList.add(btn_addModels);
	}
	
	public void setScope(Ili2DbScope scope) {
		// FIX Scope doesn't work
		this.scope = scope;
		
		disableList = new ArrayList<>();
		disableList.add(tf_dataset);
		disableList.add(tf_baskets);
		disableList.add(tf_topics);
		disableList.add(tf_models);
		disableList.add(btn_addDataset);
		disableList.add(btn_addBaskets);
		disableList.add(btn_addTopics);
		disableList.add(btn_addModels);
		/*try{
			if(false) {//!scope.isScoped()){
				disableList.add(radio_baskets);
				disableList.add(radio_dataset);
				disableList.add(radio_topics);
			}
		}catch(SQLException|ClassNotFoundException e){
			e.printStackTrace();
		}finally{		*/
			disableFields(disableList);
		//}
	}
	
	private void addInitListeners(){
				
		tg_selectedScope.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		         if (tg_selectedScope.getSelectedToggle() == radio_dataset){
		        	 enableOnly(tf_dataset, btn_addDataset);
		        	 tf_dataset.setStyle(null);
		        	 tf_baskets.setStyle(null);
		        	 tf_models.setStyle(null);
		        	 tf_topics.setStyle(null);
		         }
		         else if(tg_selectedScope.getSelectedToggle() == radio_baskets){
		        	 enableOnly(tf_baskets, btn_addBaskets);
		        	 tf_dataset.setStyle(null);
		        	 tf_baskets.setStyle(null);
		        	 tf_models.setStyle(null);
		        	 tf_topics.setStyle(null);
		         }
		         else if(tg_selectedScope.getSelectedToggle() == radio_topics){
		        	 enableOnly(tf_topics, btn_addTopics);
		        	 tf_dataset.setStyle(null);
		        	 tf_baskets.setStyle(null);
		        	 tf_models.setStyle(null);
		        	 tf_topics.setStyle(null);
		         }
		         else if(tg_selectedScope.getSelectedToggle() == radio_models){
		        	 enableOnly(tf_models, btn_addModels);
		        	 tf_dataset.setStyle(null);
		        	 tf_baskets.setStyle(null);
		        	 tf_models.setStyle(null);
		        	 tf_topics.setStyle(null);
		         }
		     } 
		});
		tf_xtfFilePath.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if(newValue!=null && !newValue.isEmpty() && newValue.endsWith(".xtf"))
					tf_xtfFilePath.setStyle(null);
			}
		});
		tf_dataset.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if(newValue!=null && !newValue.isEmpty())
					tf_dataset.setStyle(null);
			}
		});
		tf_baskets.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if(newValue!=null && !newValue.isEmpty())
					tf_baskets.setStyle(null);
			}
		});
		tf_topics.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if(newValue!=null && !newValue.isEmpty())
					tf_topics.setStyle(null);
			}
		});
		tf_models.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
				if(newValue!=null && !newValue.isEmpty())
					tf_models.setStyle(null);
			}
		});
		
	}

	private void updateParams() {
		params = new HashMap<String, String>();
		
		params.put(EnumParams.FILE_NAME.getName(), "" +tf_xtfFilePath.getText()+ "" );
		
		//--------------Export Options---------------------//
		
		if(!tf_modelDir.getText().isEmpty()){
			params.put(EnumParams.MODEL_DIR.getName(), tf_modelDir.getText());
		}
		
		if(radio_dataset.isSelected()){
			params.put(EnumParams.DATASET.getName(), tf_dataset.getText());
		}else if(radio_baskets.isSelected()){
			params.put(EnumParams.BASKETS.getName(), tf_baskets.getText());
		}else if(radio_topics.isSelected()){
			params.put(EnumParams.TOPICS.getName(), tf_topics.getText());
		}else if(radio_models.isSelected()){
			params.put(EnumParams.MODELS.getName(), tf_models.getText());
		}

		if (chk_disableRounding.isSelected()) {
			params.put(EnumParams.DISABLE_ROUNDING.getName(), "true");
		}
	}

	public void handleAddButtons(ActionEvent e) throws IOException {
		Button source = (Button) e.getSource();
		
		if (source == btn_addDataset) {
			enableOnly(tf_dataset, btn_addDataset);
			
			try {
				ArrayList<String> selectedValues = new ArrayList<>();
				
				if(!tf_dataset.getText().isEmpty()){
					selectedValues = new ArrayList<String>(Arrays.asList(tf_dataset.getText().split(";")));
				}
				
				MultipleSelectionDialog dialog = new MultipleSelectionDialog(scope.getDatasetList(), selectedValues, SelectionMode.MULTIPLE);

				dialog.setTitle(applicationBundle.getString("general.dataset"));
				Optional<List<String>> result = dialog.showAndWait();

				if(result.isPresent()){
					tf_dataset.setText(String.join(";", result.get()));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else if(source == btn_addBaskets){
			enableOnly(tf_baskets, btn_addBaskets);

			try{
				List<String> baskets = scope.getBasketList();
				// TODO Deberia asignarse null inicialmente
				ArrayList<String> selectedValues = new ArrayList<>();
			
				if(!tf_baskets.getText().isEmpty()){
					selectedValues = new ArrayList<String>(Arrays.asList(tf_baskets.getText().split(";")));
				}
				
				MultipleSelectionDialog dialog = 
						new MultipleSelectionDialog(baskets, selectedValues, SelectionMode.MULTIPLE);
				
				dialog.setTitle(applicationBundle.getString("general.baskets"));
				
				Optional<List<String>> result = dialog.showAndWait();

				if(result.isPresent()){
					tf_baskets.setText(String.join(";", result.get()));
				}
			
			}catch(SQLException e1){
				e1.printStackTrace();
			}

		}else if(source == btn_addTopics){
			enableOnly(tf_topics, btn_addTopics);
			
			try{
				List<String> topics = scope.getTopicList();
				// TODO Deberia asignarse null inicialmente
				ArrayList<String> selectedValues = new ArrayList<>();
			
				if(!tf_topics.getText().isEmpty()){
					selectedValues = new ArrayList<String>(Arrays.asList(tf_topics.getText().split(";")));
				}
				
				MultipleSelectionDialog dialog = 
						new MultipleSelectionDialog(topics, selectedValues, SelectionMode.MULTIPLE);
				
				dialog.setTitle(applicationBundle.getString("general.topics"));
				
				Optional<List<String>> result = dialog.showAndWait();

				if(result.isPresent()){
					tf_topics.setText(String.join(";", result.get()));
				}
			
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}else if(source == btn_addModels){
			enableOnly(tf_models, btn_addModels);
						try{
				List<String> models = scope.getModelList();
				// TODO Debería asignarse null inicialmente
				ArrayList<String> selectedValues = new ArrayList<>();
			
				if(!tf_models.getText().isEmpty()){
					selectedValues = new ArrayList<String>(Arrays.asList(tf_models.getText().split(";")));
				}
				
				MultipleSelectionDialog dialog = 
						new MultipleSelectionDialog(models, selectedValues, SelectionMode.MULTIPLE);
				
				dialog.setTitle(applicationBundle.getString("general.models"));
				
				Optional<List<String>> result = dialog.showAndWait();

				if(result.isPresent()){
					tf_models.setText(String.join(";", result.get()));
				}
			
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}
	}
	
	@FXML
	private void onClickBrowseXtfFile(ActionEvent e){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.xtf"),"*.xtf"));
		fileChooser.setTitle(applicationBundle.getString("general.file.saveAs"));
		Window window = ((Node)e.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(window);
		if(selectedFile!=null)
			tf_xtfFilePath.setText(selectedFile.getAbsolutePath());
	}
	
	
	private void enableOnly(Node... fields){
		
		disableFields(disableList);
		for(Node node : fields){
			node.setDisable(false);
		}
	}
	
	private void disableFields(List<Node> disableList){
		for(Node node : disableList){
			node.setDisable(true);
		}
	}
	
	private boolean validateFields(){
		boolean result = true;
		if (tg_selectedScope.getSelectedToggle() == null) {
			tf_dataset.setStyle("-fx-border-color: red ;");
			tf_baskets.setStyle("-fx-border-color: red ;");
			tf_models.setStyle("-fx-border-color: red ;");
			tf_topics.setStyle("-fx-border-color: red ;");
			result = false;
		}else if(tg_selectedScope.getSelectedToggle()==radio_dataset && tf_dataset.getText().isEmpty()){
			tf_dataset.setStyle("-fx-border-color: red ;");
			result = false;
		}else if(tg_selectedScope.getSelectedToggle()==radio_baskets && tf_baskets.getText().isEmpty()){
			tf_baskets.setStyle("-fx-border-color: red ;");
			result = false;
		}else if(tg_selectedScope.getSelectedToggle()==radio_models && tf_models.getText().isEmpty()){
			tf_models.setStyle("-fx-border-color: red ;");
			result = false;
		}else if(tg_selectedScope.getSelectedToggle()==radio_topics && tf_topics.getText().isEmpty()){
			tf_topics.setStyle("-fx-border-color: red ;");
			result = false;
		}
		if(tf_xtfFilePath.getText() == null || tf_xtfFilePath.getText().equals("") || !tf_xtfFilePath.getText().endsWith(".xtf")){
			tf_xtfFilePath.setStyle("-fx-border-color: red ;");
			result = false;
		}
		
		return result;
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

}
