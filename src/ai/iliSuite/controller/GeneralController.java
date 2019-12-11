package ai.iliSuite.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.iliSuite.base.Ili2db;
import ai.iliSuite.base.IliValidator;
import ai.iliSuite.base.InterlisExecutable;
import ai.iliSuite.base.UmlEditor;
import ai.iliSuite.view.GeneralLayoutView;
import ai.iliSuite.view.MainOptionsView;
import ai.iliSuite.view.util.navigation.EnumPaths;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class GeneralController {
	// XXX concrete (view)
	private GeneralLayoutView view;
	// XXX Program to interfaces, not implementations
	private MainOptionsView main;
	
	private ResourceBundle bundle;
	
	private EventHandler<ActionEvent> loadMainOptionsHandler;
	
	public GeneralController() throws IOException {
		bundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		
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
		view.changeTitle(EnumPaths.APP_ICON, bundle.getString("main.function.home.title"));
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
		EnumPaths iconPath = null;
		String textTitle = "";
		
		if(action == EnumActions.VALIDATE_DATA) {
			InterlisExecutable model = new IliValidator();
			result = new ValidateDataController(model);
			
			iconPath = EnumPaths.VALIDATE_ICON;
			textTitle = bundle.getString("main.function.validateData.title");
		} else if(action == EnumActions.GENERATE_PHYSICAL_MODEL) {
			Ili2db model = new Ili2db();
			result = new GeneratePhysicalModelController(model);
			
			iconPath = EnumPaths.GENERATEPHYSICALMODEL_ICON;
			textTitle = bundle.getString("main.function.generatePhysicalModel.title");
		} else if(action == EnumActions.EXPORT_DATA) {
			Ili2db model = new Ili2db();
			result = new ExportDataController(model);
			
			iconPath = EnumPaths.EXPORT_ICON;
			textTitle = bundle.getString("main.function.exportData.title");
		} else if(action == EnumActions.IMPORT_DATA) {
			Ili2db model = new Ili2db();
			result = new ImportDataController(model);
			
			iconPath = EnumPaths.IMPORT_ICON;
			textTitle = bundle.getString("main.function.importData.title");
		} else if(action == EnumActions.OPEN_UML_EDITOR) {
			InterlisExecutable model = new UmlEditor();
			result = new OpenUmlEditorController(model);
			
			iconPath = EnumPaths.OPEN_UML_EDITOR;
			textTitle = bundle.getString("main.function.openUml.title");
		}
		if(result != null) {
			result.setOnFinish(loadMainOptionsHandler);
			result.setOnGoBack(loadMainOptionsHandler);
			
			view.changeTitle(iconPath, textTitle);
		}
		return result;
	}
}
