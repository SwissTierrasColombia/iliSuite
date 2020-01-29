package ai.ilisuite.view.dialog;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import ai.ilisuite.view.util.navigation.EnumPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.util.Callback;

public class EditItemDialog extends Dialog<String> {
	@FXML
	private TextField txtValue;
	
	ResourceBundle resourceBundle;
	
	// TODO Revisar si se deben enviar opciones
	public EditItemDialog() throws IOException {
		this("");
	}
	
	public EditItemDialog(String data) throws IOException {
		loadContent();
		txtValue.setText(data);
		
		// TODO botones por parametros??
		this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		this.getDialogPane().getButtonTypes().add(ButtonType.OK);
		
		this.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				if (b == ButtonType.OK) {
					return txtValue.getText();
				}

				return null;
			}
		});
	}
	
	private void loadContent() throws IOException{
		resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		FXMLLoader loader = new FXMLLoader(EditItemDialog.class.getResource("editItemDialog.fxml"),resourceBundle);
		loader.setController(this);
		BorderPane page;

		page = (BorderPane) loader.load();

		// TODO obtiene el recurso visual. Poner en codigo?
		this.getDialogPane().setContent(page);
	}

	public void onClickBtnFolderChooser(ActionEvent event) throws IOException {
		Node eventSource = (Node) event.getSource();
		Window ownerWindow = eventSource.getScene().getWindow();

		DirectoryChooser directoryChooser = new DirectoryChooser();

		directoryChooser.setTitle(resourceBundle.getString("dialog.editItem.selectFolder"));

		File selectedDirectory = directoryChooser.showDialog(ownerWindow);

		if (selectedDirectory != null) {
			txtValue.setText(selectedDirectory.getAbsolutePath());
		}
	}

	public String getData() {
		return txtValue.getText();
	}

	public void setData(String value) {
		this.txtValue.setText(value);
	}
}
