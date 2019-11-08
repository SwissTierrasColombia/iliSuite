package ai.iliSuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ai.iliSuite.actions.validateData.FinishDataValidationController;
import ai.iliSuite.actions.validateData.ValidateOptionsController;
import ai.iliSuite.view.general.GeneralLayoutController;
import ai.iliSuite.view.general.MainOptionsController;
import ai.iliSuite.view.wizard.StepViewController;
import ai.iliSuite.view.wizard.Wizard;
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
		List<StepViewController> steps = null;
		try {
			Wizard wizard = new Wizard();

			steps = getStepsFromAction(action);

			if(steps!=null) {
				wizard.add(steps);
				wizard.init();
				view.drawPage(wizard.getGraphicComponent());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<StepViewController> getStepsFromAction(EnumActions action) throws IOException{		
		if(action == null) return null;
		
		List<StepViewController> result = new ArrayList<StepViewController>();
		
		if(action == EnumActions.VALIDATE_DATA) {
			result.add(new ValidateOptionsController());
			result.add(new FinishDataValidationController());
		}
		
		return result;
	}
}
