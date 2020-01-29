package ai.ilisuite.view.dialog;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ai.ilisuite.view.util.navigation.EnumPaths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class MultipleSelectionDialog extends Dialog<List<String>> implements Initializable {

	@FXML
	private Button btn_ok;
	@FXML
	private Button btn_cancel;
	@FXML
	private ListView<String> lst_available;
	@FXML
	private ListView<String> lst_selected;
	private SelectionMode modality;

	
	public MultipleSelectionDialog(List<String> available, List<String> selected,SelectionMode modality){
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
				
		FXMLLoader loader = new FXMLLoader(ModelDirDialog.class.getResource("multipleSelectionDialog.fxml"),resourceBundle);

		loader.setController(this);

		BorderPane page;
		try {
			page = (BorderPane) loader.load();
			this.getDialogPane().setContent(page);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setInitialData(available, selected, modality);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		this.getDialogPane().getButtonTypes().add(ButtonType.OK);
		
		this.setResultConverter(new Callback<ButtonType, List<String>>() {
			@Override
			public List<String> call(ButtonType b) {
				if (b == ButtonType.OK) {
					return lst_selected.getItems();
				}
				return null;
			}
		});
	}

	public void setInitialData(List<String> available, List<String> selected,SelectionMode modality){
		
		this.modality=modality;
		
		lst_available.getSelectionModel().setSelectionMode(modality);
		
		ObservableList<String> availableItems = FXCollections.observableArrayList(available);
		
		if(selected!=null && !selected.isEmpty()){
			ObservableList<String> selectedItems = FXCollections.observableArrayList(selected);
			availableItems.removeAll(selectedItems);
			lst_selected.setItems(selectedItems);
		}
		lst_available.setItems(availableItems);
	}
	
	@FXML
	private void handleMoveToRight(ActionEvent e){
		ObservableList<String> availableSelectedItems = lst_available.getSelectionModel().getSelectedItems();
		//si modalidad == simple y ya hay objetos a la derecha => no hacer nada
		if(modality != SelectionMode.SINGLE || lst_selected.getItems().isEmpty()){
			if(!availableSelectedItems.isEmpty()){
				lst_selected.getItems().addAll(availableSelectedItems);
				lst_available.getItems().removeAll(availableSelectedItems);
			}
		}
	}
	
	@FXML
	private void handleMoveToLeft(ActionEvent e){
		ObservableList<String> selSelectedItems = lst_selected.getSelectionModel().getSelectedItems();
		if(!selSelectedItems.isEmpty()){
			lst_available.getItems().addAll(selSelectedItems);
			lst_selected.getItems().removeAll(selSelectedItems);
		}
	}
	
	public List<String> getResultList() {
		return lst_selected.getItems();
	}
}
