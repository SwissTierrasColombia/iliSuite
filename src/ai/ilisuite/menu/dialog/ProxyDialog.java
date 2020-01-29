package ai.ilisuite.menu.dialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.ilisuite.view.util.navigation.EnumPaths;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class ProxyDialog extends Dialog<String> implements Initializable {
	@FXML
	TextField txtProxyHost;

	@FXML
	TextField txtProxyPort;

	Button btnOk;

	String proxyHost;
	Integer proxyPort;

	public ProxyDialog(String proxyHost, Integer proxyPort) throws IOException {
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;

		ResourceBundle resourceBundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		FXMLLoader loader = new FXMLLoader(ProxyDialog.class.getResource("proxyDialog.fxml"), resourceBundle);

		loader.setController(this);

		BorderPane page = (BorderPane) loader.load();
		this.getDialogPane().setContent(page);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (proxyPort != null)
			txtProxyPort.setText(Integer.toString(proxyPort));
		
		if(proxyHost != null)
			txtProxyHost.setText(proxyHost);

		this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		this.getDialogPane().getButtonTypes().add(ButtonType.OK);

		btnOk = (Button) this.getDialogPane().lookupButton(ButtonType.OK);

		btnOk.addEventFilter(ActionEvent.ACTION, event -> {
			// xor
			if (txtProxyHost.getText().isEmpty() ^ txtProxyPort.getText().isEmpty()) {
				if (txtProxyHost.getText().isEmpty())
					txtProxyHost.setStyle("-fx-border-color: red ; ");

				if (txtProxyPort.getText().isEmpty())
					txtProxyPort.setStyle("-fx-border-color: red ; ");

				event.consume();
			}
		});

		this.setResultConverter(new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType b) {
				if (b == ButtonType.OK) {
					return txtProxyHost.getText()
							+ (!txtProxyPort.getText().isEmpty() ? ":" + txtProxyPort.getText() : "");
				}
				return null;
			}
		});

		txtProxyHost.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty())
					txtProxyHost.setStyle(null);
				
				if(newValue.isEmpty()) txtProxyPort.setStyle(null);

				// btnOk.setDisable(txtProxyHost.getText().isEmpty()||txtProxyPort.getText().isEmpty());
			};
		});

		txtProxyPort.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (!newValue.isEmpty())
					txtProxyPort.setStyle(null);
				
				if(newValue.isEmpty()) txtProxyHost.setStyle(null);

				if (!newValue.matches("\\d*")) {
					txtProxyPort.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (txtProxyPort.getText().length() > 5) {
					String s = txtProxyPort.getText().substring(0, 5);
					txtProxyPort.setText(s);
				}
			}
		});
	}

	public String getProxyHost() {
		return txtProxyHost.getText();
	}

	public Integer getProxyPort() {
		Integer result = null;

		try {
			result = Integer.valueOf(txtProxyPort.getText());
		} catch (NumberFormatException E) {
		}

		return result;
	}
}
