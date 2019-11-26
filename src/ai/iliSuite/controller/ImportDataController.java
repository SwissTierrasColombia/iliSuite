package ai.iliSuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ai.iliSuite.base.Ili2db;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.util.exception.ExitException;
import ai.iliSuite.util.params.EnumParams;
import ai.iliSuite.util.plugin.PluginsLoader;
import ai.iliSuite.view.DatabaseOptionsView;
import ai.iliSuite.view.DatabaseSelectionView;
import ai.iliSuite.view.FinishActionView;
import ai.iliSuite.view.ImportDataOptionsView;
import ai.iliSuite.view.wizard.EmptyWizardException;
import ai.iliSuite.view.wizard.Wizard;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class ImportDataController implements DbSelectorController, ParamsController {
	private Ili2db model;
	private List<Map<String,String>> paramsList;
	private Wizard wizard;
	
	private DatabaseOptionsView dbSelectionScreen;
	private ImportDataOptionsView importDataOptions;
	
	private EventHandler<ActionEvent> finishHandler;
	private EventHandler<ActionEvent> goBackHandler;
	
	private Map<String, DbDescription> lstDbDescription;
	
	private ImplFactory dbImpl;
	
	private Thread commandExecutionThread;
	
	public ImportDataController(Ili2db model) throws IOException {
		this.model = model;
		paramsList = new ArrayList<Map<String, String>>();
		initLstDbDescription();
		initWizard();
	}
	
	private void initLstDbDescription() {
		
		Map<String, ImplFactory> lstPlugin = PluginsLoader.getPlugins();
		lstDbDescription = new HashMap<String, DbDescription>();
		
		for(Entry<String, ImplFactory> item : lstPlugin.entrySet()) {
			ImplFactory pluginItem = (ImplFactory) item.getValue();
			DbDescription description = new DbDescription();
			String dbKey = item.getKey();
			description.setKey(dbKey);
			description.setName(pluginItem.getNameDB());
			description.setHelpText(pluginItem.getHelpText());
			
			lstDbDescription.put(item.getKey(), description);
		}
	}
	
	private void initWizard() throws IOException {
		// FIX same body generate
		EventHandler<ActionEvent> goBack = 
				(ActionEvent e) -> { if(goBackHandler != null) { goBackHandler.handle(e); }};
		EventHandler<ActionEvent> finish = 
				(ActionEvent e) -> { if(finishHandler != null) { finishHandler.handle(e); }};
		
		dbSelectionScreen = new DatabaseOptionsView(this, false);
		importDataOptions = new ImportDataOptionsView(this);

		wizard = new Wizard();
		wizard.setOnBack(goBack);
		wizard.setOnCancel(goBack);
		wizard.setOnFinish(finish);

		wizard.add(new DatabaseSelectionView(this, lstDbDescription));
		wizard.add(dbSelectionScreen);
		wizard.add(importDataOptions);
		wizard.add(new FinishActionView(this));

		try {
			wizard.init();
		} catch (EmptyWizardException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addParams(Map<String, String> params) {
		paramsList.add(params);
	}

	@Override
	public void removeParams(Map<String, String> params) {
		paramsList.remove(params);
	}

	@Override
	public Parent getGraphicComponent() {
		return wizard.getGraphicComponent();
	}

	@Override
	public String getTextParams() {
		// FIX repeated body
		Map<String, String> params = model.getParams();
		params.put(EnumParams.DATA_EXPORT.getName(), null);
		for(Map<String, String> item:paramsList) {
			params.putAll(item);
		}

		return model.getArgs(true);
	}
	
	@Override
	public boolean execute() {
		// FIX repeated body
		SimpleBooleanProperty booleanResult = new SimpleBooleanProperty();
		Task<Boolean> task = new Task<Boolean>(){
			@Override
			protected Boolean call() throws Exception {
				try {
					model.run();
					return true;
				} catch (ExitException e) {
					System.out.println(e.status);
					return false;
				}
			}
		};
		task.setOnSucceeded(workerStateEvent ->{
			
			});
		booleanResult.bind(task.valueProperty());
		
		commandExecutionThread = new Thread(task);
		commandExecutionThread.start();
		
		return true;
	}
	
	@Override
	public void setDatabase(String dbKey) {
		dbImpl = PluginsLoader.getPluginByKey(dbKey);
		model.setDbImpl(dbImpl);
		dbSelectionScreen.setDbFactory(dbImpl);
		dbSelectionScreen.loadDbOptions();

		// FIX panel into plugin
//		Map<EnumCustomPanel, PanelCustomizable> lstCustomPanel = dbImpl.getCustomPanels();
//		
//		PanelCustomizable customPanelSchemaImport = lstCustomPanel.get(EnumCustomPanel.EXPORT);
//		if(customPanelSchemaImport != null) {
//			exportDataOptions.setCustomPanelSchemaImport(customPanelSchemaImport);
//		}
	}
	
	@Override
	public void setOnFinish(EventHandler<ActionEvent> handler) {
		this.finishHandler = handler;
	}
	
	@Override
	public void setOnGoBack(EventHandler<ActionEvent> handler) {
		this.goBackHandler = handler;
	}
}
