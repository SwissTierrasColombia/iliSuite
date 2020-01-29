package ai.ilisuite.controller;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public interface ParamsController {
	public void addParams(Map<String, String> params);
	public void removeParams(Map<String, String> params);
	public Parent getGraphicComponent();
	
	public String getTextParams();
	public boolean execute();
	
	public void setOnFinish(EventHandler<ActionEvent> handler);
	public void setOnGoBack(EventHandler<ActionEvent> handler);
}