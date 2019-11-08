package ai.iliSuite.controller;

import java.io.IOException;

import ai.iliSuite.view.general.GeneralLayoutController;
import ai.iliSuite.view.general.MainOptionsController;
import javafx.scene.Scene;

public class GeneralController {
	// XXX concrete (view)
	private GeneralLayoutController view;
	
	// XXX Program to interfaces, not implementations
	private MainOptionsController main;
	
	public GeneralController() throws IOException {
		view = new GeneralLayoutController(this);
		main = new MainOptionsController(this);
		//loadMainOptions();
	}
	
	public void loadMainOptions() {
		view.drawPage(main.getGraphicComponent());
	}
	
	public Scene getScene(String cssPath) {
		Scene scene = new Scene(view.getGraphicComponent());
		scene.getStylesheets().add(cssPath);
		return scene;
	}
	
	public void changeAction(EnumActions action) {
		System.out.println(action.name());
	}
}
