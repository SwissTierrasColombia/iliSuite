package ai.ilisuite.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.ilisuite.base.IliExecutable;
import ai.ilisuite.base.IliValidator;
import ai.ilisuite.base.UmlEditor;
import ai.ilisuite.view.GeneralLayoutView;
import ai.ilisuite.view.MainOptionsView;
import ai.ilisuite.view.util.navigation.EnumPaths;
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
	
	public void changeAction(EnumIliSuiteActions action) {
		ParamsController actionController = null;
		try {
			actionController = getControllerFromAction(action);
			view.drawPage(actionController.getGraphicComponent());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ParamsController getControllerFromAction(EnumIliSuiteActions action) throws IOException{		
		ParamsController result = null;
		EnumPaths iconPath = null;
		String textTitle = "";
		
		if(action == EnumIliSuiteActions.VALIDATE_DATA) {
			IliExecutable model = new IliValidator();
			result = new ValidateDataController(model);
			
			iconPath = EnumPaths.VALIDATE_ICON;
			textTitle = bundle.getString("main.function.validateData.title");
		} else if(action == EnumIliSuiteActions.GENERATE_PHYSICAL_MODEL) {
			result = new GeneratePhysicalModelController();
			
			iconPath = EnumPaths.GENERATEPHYSICALMODEL_ICON;
			textTitle = bundle.getString("main.function.generatePhysicalModel.title");
		} else if(action == EnumIliSuiteActions.EXPORT_DATA) {
			result = new ExportDataController();
			
			iconPath = EnumPaths.EXPORT_ICON;
			textTitle = bundle.getString("main.function.exportData.title");
		} else if(action == EnumIliSuiteActions.IMPORT_DATA) {
			result = new ImportDataController();
			
			iconPath = EnumPaths.IMPORT_ICON;
			textTitle = bundle.getString("main.function.importData.title");
		} else if(action == EnumIliSuiteActions.OPEN_UML_EDITOR) {
			IliExecutable model = new UmlEditor();
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
