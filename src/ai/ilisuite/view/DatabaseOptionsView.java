package ai.ilisuite.view;

import java.util.Map;

import ai.ilisuite.controller.DbSelectorController;
import ai.ilisuite.controller.ParamsController;
import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.view.wizard.StepArgs;
import ai.ilisuite.view.wizard.StepViewController;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;


public class DatabaseOptionsView extends StepViewController {

	private BorderPane mainPane;	
	private ParamsController controller;
	private Map<String,String> params;
	private IController dbPanel;
	private DbSelectorController dbController;
	
	public DatabaseOptionsView(ParamsController controller, DbSelectorController dbController) {
		this.controller = controller;
		this.dbController = dbController;
		
		mainPane = new BorderPane();
		mainPane.prefWidth(700);
		mainPane.prefHeight(335);
	}
	
	@Override
	public void goForward(StepArgs args) {
		super.goForward(args);
		Map<String, String> oldParams = params;
		params = dbPanel.getParams();		
		boolean isValid = (params != null);

		args.setCancel(!isValid);
				
		if (isValid) {
			if(oldParams != null) {
				controller.removeParams(oldParams);
			}
			controller.addParams(params);
			dbController.databaseConnecting(params);
		}
	}

	@Override
	public Parent getGraphicComponent() {
		return mainPane;
	}
	
	public void setDbPanel(IController dbPanel) {
		this.dbPanel = dbPanel; 
		mainPane.setCenter(dbPanel.getGraphicComponent());
	}
}
