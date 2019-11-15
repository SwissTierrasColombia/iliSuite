package ai.iliSuite.view;

import java.util.Map;

import ai.iliSuite.controller.ParamsController;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.view.wizard.StepArgs;
import ai.iliSuite.view.wizard.StepViewController;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class DatabaseOptionsView extends StepViewController {

	private BorderPane mainPane;	
	boolean createSchema;
	private ImplFactory dbFactory;
	private ParamsController controller;
	private Map<String,String> params;
		 
	public DatabaseOptionsView(ParamsController controller, boolean createSchema) {
		this.createSchema = createSchema;
		this.controller = controller;
		mainPane = new BorderPane();
		mainPane.prefWidth(700);
		mainPane.prefHeight(335);
	}
	
	@Override
	public void goForward(StepArgs args) {
		super.goForward(args);
		params = dbFactory.getConnectionsParams();		
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

	public void setDbFactory(ImplFactory dbFactory) {
		this.dbFactory = dbFactory;
	}
	
	public void loadDbOptions() {
		dbFactory.loadDbConfigPanel(createSchema);
		mainPane.setCenter(dbFactory.getDbConfigPanel());
	}
}
