package base;

import java.util.Map;

import javafx.scene.Parent;

public interface PanelCustomizable {
	public Parent getPanel();
	public Map<String, String> getParams();
	public String getName();
}
