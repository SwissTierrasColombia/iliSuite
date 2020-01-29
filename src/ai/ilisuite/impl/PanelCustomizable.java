package ai.ilisuite.impl;

import java.util.List;
import java.util.Map;
import javafx.scene.Parent;

public interface PanelCustomizable {
	public Parent getPanel();
	public Map<String, String> getParams();
	public List<String> getParamsForRemove();
	public String getName();
}
