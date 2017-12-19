package iliSuite.plugin.ili2fgdb.view;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import base.controller.IController;
import base.dbconn.AbstractConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class DatabaseOptionsController implements IController, Initializable {
	@FXML
	private ResourceBundle applicationBundle;

	private AbstractConnection connection;

	private boolean createSchema;

	@FXML
	private TextField tf_folder;

	@FXML
	private Button btnBrowse;
	
	private List<Node> listOfRequired;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		applicationBundle = arg1;
		listOfRequired = new ArrayList<>();
		listOfRequired.add(tf_folder);
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = null;

		if (validateRequiredFields()) {

			String file = tf_folder.getText();

			Map<String, String> params = new HashMap<String, String>();

			params.put("dbfile", file);

			connection.setConnectionParams(params);

			result = new HashMap<String, String>();

			if (file != null)
				result.put("--dbfile", file);

		}

		return result;
	}

	@Override
	public void setConnection(AbstractConnection connection) {
		this.connection = connection;
	}

	public void setCreateSchema(boolean createSchema) {
		this.createSchema = createSchema;
	}

	public void onClickBrowse(ActionEvent e) {

		DirectoryChooser folderChooser = new DirectoryChooser();

		Window window = ((Node) e.getSource()).getScene().getWindow();

		File selectedFolder = null;

		if (createSchema) {
//			folderChooser.setTitle(applicationBundle.getString("datafile.saveAs"));
			selectedFolder = folderChooser.showDialog(window);
		} else {
			folderChooser.setTitle(applicationBundle.getString("datafolder.choose"));
			selectedFolder = folderChooser.showDialog(window);
		}

		if (selectedFolder != null)
			tf_folder.setText(selectedFolder.getAbsolutePath());

	}
	
	protected boolean validateRequiredFields(){
		boolean toValid = true;
		for (Node n : listOfRequired) {
			if (n instanceof TextField) {
				if (((TextField) n).getText().isEmpty()) {
					((TextField) n).setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
					((TextField) n).setTooltip(new Tooltip(applicationBundle.getString("general.required")));
					((TextField) n).setOnKeyReleased(event -> {
						((TextField) n).setStyle(null);
						((TextField) n).setTooltip(null);
						});
					toValid = false;
				}
			}
		}
		return toValid;
	}

}
