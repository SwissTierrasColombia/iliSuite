package ai.ilisuite.impl.ili2fgdb.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SchemaImportPanelController implements Initializable {
	@FXML
	private TextField tf_resolution;
	@FXML
	private TextField tf_tolerance;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addInitListeners();
	}

	private void addInitListeners(){
		tf_resolution.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            // ^(\\d*\\.)?\\d+$
				if (!newValue.matches("[\\d\\.]*")) {
	            	tf_resolution.setText(newValue.replaceAll("[^\\d\\.]", ""));
	            }
	            if (tf_resolution.getText().length() > 19) {
	                String s = tf_resolution.getText().substring(0, 19);
	                tf_resolution.setText(s);
	            }
	        }
		});
		
		tf_tolerance.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            // ^(\\d*\\.)?\\d+$
				if (!newValue.matches("[\\d\\.]*")) {
					tf_tolerance.setText(newValue.replaceAll("[^\\d\\.]", ""));
	            }
	            if (tf_tolerance.getText().length() > 19) {
	                String s = tf_tolerance.getText().substring(0, 19);
	                tf_tolerance.setText(s);
	            }
	        }
		});
	}
	
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<>();
		
		String resolution = tf_resolution.getText();
		String tolerance = tf_tolerance.getText();

		/// TODO missing decimal validation
		if(!resolution.isEmpty()) {
			result.put("--fgdbXyResolution", resolution);
		}
		if(!tolerance.isEmpty()) {
			result.put("--fgdbXyTolerance", tolerance);
		}

		return result;
	}
	
	public List<String> getParamsForRemove(){
		List<String> result = new ArrayList<>();
		
		String resolution = tf_resolution.getText();
		String tolerance = tf_tolerance.getText();

		if(resolution.isEmpty()) {
			result.add("--fgdbXyResolution");
		}
		if(tolerance.isEmpty()) {
			result.add("--fgdbXyTolerance");
		}

		return result;
	}
}
