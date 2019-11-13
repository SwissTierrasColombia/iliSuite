package ai.iliSuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ai.iliSuite.base.IliValidator;
import ai.iliSuite.base.InterlisExecutable;
import ai.iliSuite.view.FinishActionView;
import ai.iliSuite.view.ValidateOptionsView;
import ai.iliSuite.view.general.GeneralLayoutController;
import ai.iliSuite.view.general.MainOptionsController;
import ai.iliSuite.view.wizard.StepViewController;
import ai.iliSuite.view.wizard.Wizard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class GeneralController {
	// XXX concrete (view)
	private GeneralLayoutController view;
	// XXX Program to interfaces, not implementations
	private MainOptionsController main;
	
	private EventHandler<ActionEvent> loadMainOptionsHandler;
	
	public GeneralController() throws IOException {
		view = new GeneralLayoutController(this);
		main = new MainOptionsController(this);
		//loadMainOptions();
		loadMainOptionsHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadMainOptions();
			}
		};
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
		ParamsController actionController = null;
		try {
			actionController = getControllerFromAction(action);
			view.drawPage(actionController.getGraphicComponent());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ParamsController getControllerFromAction(EnumActions action) throws IOException{		
		ParamsController result = null;
		
		if(action == EnumActions.VALIDATE_DATA) {
			InterlisExecutable model = new IliValidator();
			result = new ValidateDataController(model);
			result.setOnFinish(loadMainOptionsHandler);
			result.setOnGoBack(loadMainOptionsHandler);
		}
		
		return result;
	}
}
