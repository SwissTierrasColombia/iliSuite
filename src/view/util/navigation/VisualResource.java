package view.util.navigation;

import javafx.scene.Parent;

public class VisualResource {

	private Parent component;
	private Navigable controller;
	
	public VisualResource(Parent component, Navigable controller){
		this.component=component;
		this.controller=controller;
	}

	
	public Parent getComponent() {
		return component;
	}
	public void setComponent(Parent component) {
		this.component = component;
	}
	public Navigable getController() {
		return controller;
	}
	public void setController(Navigable controller) {
		this.controller = controller;
	}
	
}
