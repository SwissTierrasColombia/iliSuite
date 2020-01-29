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
import ai.ilisuite.util.params.EnumParams;
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
	
	private ArrayList<Node> disableList;

	private Parent viewRootNode;
	private ParamsController controller;
	private Map<String,String> params;
	
	private List<String> datasetList;
	private List<String> topicList;
	private List<String> modelList;
	private List<String> basketList;
	
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
	
	private void addInitListeners(){
				
		tg_selectedScope.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		         if (tg_selectedScope.getSelectedToggle() == radio_dataset){
		        	 enableOnly(tf_dataset, btn_addDataset);
		         }
		         else if(tg_selectedScope.getSelectedToggle() == radio_baskets){
		        	 enableOnly(tf_baskets, btn_addBaskets);
		         }
		         else if(tg_selectedScope.getSelectedToggle() == radio_topics){
		        	 enableOnly(tf_topics, btn_addTopics);
		         }
		         else if(tg_selectedScope.getSelectedToggle() == radio_models){
		        	 enableOnly(tf_models, btn_addModels);
		         }
	        	 tf_dataset.setStyle(null);
	        	 tf_baskets.setStyle(null);
	        	 tf_models.setStyle(null);
	        	 tf_topics.setStyle(null);
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

	private void openMultipleSelectionDialog(TextField textList, List<String> valuesList, String titleKey) {
		ArrayList<String> selectedValues = new ArrayList<>();
		
		if(!textList.getText().isEmpty()){
			selectedValues = new ArrayList<String>(Arrays.asList(textList.getText().split(";")));
		}
		
		MultipleSelectionDialog dialog = new MultipleSelectionDialog(valuesList, selectedValues, SelectionMode.MULTIPLE);

		dialog.setTitle(applicationBundle.getString(titleKey));
		Optional<List<String>> result = dialog.showAndWait();

		if(result.isPresent()){
			textList.setText(String.join(";", result.get()));
		}
	}
	
	public void handleAddButtons(ActionEvent e) throws IOException {
		Button source = (Button) e.getSource();
		
		if (source == btn_addDataset) {
			enableOnly(tf_dataset, btn_addDataset);
			openMultipleSelectionDialog(tf_dataset, datasetList, "general.dataset");
		}else if(source == btn_addBaskets){
			enableOnly(tf_baskets, btn_addBaskets);
			openMultipleSelectionDialog(tf_baskets, basketList, "general.baskets");
		}else if(source == btn_addTopics){
			enableOnly(tf_topics, btn_addTopics);
			openMultipleSelectionDialog(tf_topics, topicList, "general.topics");
		}else if(source == btn_addModels){
			enableOnly(tf_models, btn_addModels);
			openMultipleSelectionDialog(tf_models, modelList, "general.models");
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

	public void setDatasetList(List<String> datasetList) {
		this.datasetList = datasetList;
		this.datasetList.sort(null);
	}

	public void setTopicList(List<String> topicList) {
		this.topicList = topicList;
		this.topicList.sort(null);
	}

	public void setModelList(List<String> modelList) {
		this.modelList = modelList;
		this.modelList.sort(null);
	}

	public void setBasketList(List<String> basketList) {
		this.basketList = basketList;
		this.basketList.sort(null);
	}
	
	public void setScoped(boolean isScoped) {
		if(!isScoped){
			disableList.add(radio_baskets);
			disableList.add(radio_dataset);
			disableList.add(radio_topics);
		} else {
			disableList.remove(radio_baskets);
			disableList.remove(radio_dataset);
			disableList.remove(radio_topics);
		}
		radio_baskets.setDisable(!isScoped);
		radio_dataset.setDisable(!isScoped);
		radio_topics.setDisable(!isScoped);
	}

}
