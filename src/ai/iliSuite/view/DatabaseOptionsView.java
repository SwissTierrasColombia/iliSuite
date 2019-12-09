package ai.iliSuite.view;

import java.io.IOException;
import java.util.Map;

import ai.iliSuite.controller.ParamsController;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.controller.IController;
import ai.iliSuite.impl.dbconn.AbstractConnection;
import ai.iliSuite.view.wizard.StepArgs;
import ai.iliSuite.view.wizard.StepViewController;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class DatabaseOptionsView extends StepViewController {

	private BorderPane mainPane;	
	private ParamsController controller;
	private Map<String,String> params;
	private IController dbPanel;
		 
	public DatabaseOptionsView(ParamsController controller) {
		this.controller = controller;
		mainPane = new BorderPane();
		mainPane.prefWidth(700);
		mainPane.prefHeight(335);
	}
	
	@Override
	public void goForward(StepArgs args) {
		super.goForward(args);
		params = dbPanel.getParams();		
		boolean isValid = (params != null);

		args.setCancel(!isValid);
				
		if (isValid) {
			if(params != null) {
				controller.removeParams(params);
			}
			controller.addParams(params);
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
