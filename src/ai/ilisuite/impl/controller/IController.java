package ai.ilisuite.impl.controller;

import java.util.Map;

import javafx.scene.Parent;

public interface IController {
	public Map<String, String> getParams();
	public Parent getGraphicComponent(); // FIX it's repeated in several place 
}
