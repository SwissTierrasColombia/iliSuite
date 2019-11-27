package ai.iliSuite.controller;

import java.io.IOException;

import ai.iliSuite.base.Ili2db;
import ai.iliSuite.base.IliValidator;
import ai.iliSuite.base.InterlisExecutable;
import ai.iliSuite.base.UmlEditor;
import ai.iliSuite.view.GeneralLayoutView;
import ai.iliSuite.view.MainOptionsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class GeneralController {
	// XXX concrete (view)
	private GeneralLayoutView view;
	// XXX Program to interfaces, not implementations
	private MainOptionsView main;
	
	private EventHandler<ActionEvent> loadMainOptionsHandler;
	
	public GeneralController() throws IOException {
		view = new GeneralLayoutView(this);
		main = new MainOptionsView(this);
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
		} else if(action == EnumActions.GENERATE_PHYSICAL_MODEL) {
			Ili2db model = new Ili2db();
			result = new GeneratePhysicalModelController(model);
		} else if(action == EnumActions.EXPORT_DATA) {
			Ili2db model = new Ili2db();
			result = new ExportDataController(model);
		} else if(action == EnumActions.IMPORT_DATA) {
			Ili2db model = new Ili2db();
			result = new ImportDataController(model);
		} else if(action == EnumActions.OPEN_UML_EDITOR) {
			InterlisExecutable model = new UmlEditor();
			result = new OpenUmlEditorController(model);
		}
		if(result != null) {
			result.setOnFinish(loadMainOptionsHandler);
			result.setOnGoBack(loadMainOptionsHandler);
		}
		return result;
	}
}
