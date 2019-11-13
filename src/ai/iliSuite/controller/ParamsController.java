package ai.iliSuite.controller;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public interface ParamsController {
	public void addParams(HashMap<String, String> params);
	public void removeParams(HashMap<String, String> params);
	public Parent getGraphicComponent();
	
	public String getTextParams();
	public boolean execute();
	
	public void setOnFinish(EventHandler<ActionEvent> handler);
	public void setOnGoBack(EventHandler<ActionEvent> handler);
}