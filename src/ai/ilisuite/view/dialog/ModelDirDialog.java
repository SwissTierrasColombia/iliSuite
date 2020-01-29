package ai.ilisuite.view.dialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ai.ilisuite.view.util.navigation.EnumPaths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class ModelDirDialog extends Dialog<List<String>> implements Initializable {

	private ResourceBundle bundle;
	@FXML
	private ListView<String> lstData;

	public ModelDirDialog()throws IOException {
		this(new ArrayList<String>());
	}
	
	public ModelDirDialog(List<String> data) throws IOException {
		bundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		FXMLLoader loader = new FXMLLoader(ModelDirDialog.class.getResource("modelDirDialog.fxml"), 
				bundle);

		loader.setController(this);

		BorderPane page = (BorderPane) loader.load();
		this.getDialogPane().setContent(page);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		this.getDialogPane().getButtonTypes().add(ButtonType.OK);
		
		
		this.setResultConverter(new Callback<ButtonType, List<String>>() {
			@Override
			public List<String> call(ButtonType b) {
				if (b == ButtonType.OK) {
					return getData();
				}
				return null;
			}
		});
		setData(null);
		
		lstData.setEditable(true);
		lstData.setCellFactory(TextFieldListCell.forListView());

		lstData.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(ListView.EditEvent<String> t) {
				lstData.getItems().set(t.getIndex(), t.getNewValue());
			}
		});
	}

	@FXML
	public void onClickBtnNew(ActionEvent event) throws IOException {
		
		EditItemDialog newItem = new EditItemDialog();
		
		//newItem.setHeaderText("Seleccione el archivo o url... ");
		newItem.setTitle(bundle.getString("dialog.editItem.titleAdd"));
		newItem.setResizable(false);
		
		Optional<String> result = newItem.showAndWait();
		
		if(result.isPresent()){
			lstData.getItems().add(result.get());
		}
	}
	
	@FXML
	public void onClickBtnDelete(ActionEvent event) throws IOException {
		int index = lstData.getSelectionModel().getSelectedIndex();
		
		if (index != -1) {
			Alert alertDialog = new Alert(AlertType.CONFIRMATION);
			
			alertDialog.setTitle(bundle.getString("dialog.modeldir.confirmDeleteDialogTitle"));
			alertDialog.setHeaderText("");
			alertDialog.setContentText(bundle.getString("dialog.modeldir.confirmDeleteDialogMessage") + " "+lstData.getItems().get(index));
			
			Optional<ButtonType> result = alertDialog.showAndWait();
			
			if(result.isPresent()){
				if(result.get()==ButtonType.OK){
					lstData.getItems().remove(index);
				}
			}
		}
		else{
			Alert warningDialog = new Alert(AlertType.WARNING);
			warningDialog.setHeaderText("");
			warningDialog.setContentText(bundle.getString("dialog.modeldir.dialogNoItemSelected"));
			warningDialog.showAndWait();
		}
	}
	
	@FXML
	public void onClickBtnModify(ActionEvent event) throws IOException {
		int index = lstData.getSelectionModel().getSelectedIndex();
		
		if (index != -1) {
			EditItemDialog editItem = new EditItemDialog(lstData.getItems().get(index));
			//editItem.setHeaderText("...Descripcion");
			editItem.setTitle(bundle.getString("dialog.editItem.titleModify"));
			editItem.setResizable(false);
			
			Optional<String> result = editItem.showAndWait();
			
			if(result.isPresent()){
				lstData.getItems().set(index, result.get());
			}
		}
		else{
			Alert warningDialog = new Alert(AlertType.WARNING);
			warningDialog.setHeaderText("");
			warningDialog.setContentText(bundle.getString("dialog.modeldir.dialogNoItemSelected"));
			warningDialog.showAndWait();
		}
	}
	
	public List<String> getData() {
		ObservableList<String> items = lstData.getItems();
		List<String> data = new ArrayList<String>();
		
		for(int k=0;k<items.size();k++)
			data.add(items.get(k));	
		
		return data;
	}

	public void setData(List<String> data) {
		ObservableList<String> items;

		if (data != null)
			items = FXCollections.observableArrayList(data);
		else
			items = FXCollections.observableArrayList();

		lstData.setItems(items);
	}
}
