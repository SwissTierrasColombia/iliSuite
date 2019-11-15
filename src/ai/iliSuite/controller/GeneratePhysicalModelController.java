package ai.iliSuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ai.iliSuite.base.Ili2db;
import ai.iliSuite.base.InterlisExecutable;
import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.util.plugin.PluginsLoader;
import ai.iliSuite.view.DatabaseOptionsView;
import ai.iliSuite.view.DatabaseSelectionView;
import ai.iliSuite.view.FinishActionView;
import ai.iliSuite.view.ValidateOptionsView;
import ai.iliSuite.view.wizard.EmptyWizardException;
import ai.iliSuite.view.wizard.Wizard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class GeneratePhysicalModelController implements ParamsController, DbSelectorController {

	private Ili2db model;
	
	private Wizard wizard;
	
	private DatabaseOptionsView dbSelectionScreen;
	
	private EventHandler<ActionEvent> finishHandler;
	private EventHandler<ActionEvent> goBackHandler;
	
	private Map<String, DbDescription> lstDbDescription;
	
	private ImplFactory dbImpl;
	
	public GeneratePhysicalModelController(Ili2db model) throws IOException {
		this.model = model;
		initLstDbDescription();

		initWizard();
	}

	private void initWizard() throws IOException {
		EventHandler<ActionEvent> goBack = 
				(ActionEvent e) -> { if(goBackHandler != null) { goBackHandler.handle(e); }};
		EventHandler<ActionEvent> finish = 
				(ActionEvent e) -> { if(finishHandler != null) { finishHandler.handle(e); }};
		dbSelectionScreen = new DatabaseOptionsView(this, true);

		wizard = new Wizard();
		wizard.setOnBack(goBack);
		wizard.setOnCancel(goBack);
		wizard.setOnFinish(finish);

		wizard.add(new DatabaseSelectionView(this, lstDbDescription));
		wizard.add(dbSelectionScreen);

		try {
			wizard.init();
		} catch (EmptyWizardException e) {
			e.printStackTrace();
		}
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
	@Override
	public void addParams(Map<String, String> params) {
	}

	@Override
	public void removeParams(Map<String, String> params) {
	}

	@Override
	public Parent getGraphicComponent() {
		return wizard.getGraphicComponent();
	}

	@Override
	public String getTextParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOnFinish(EventHandler<ActionEvent> handler) {
		this.finishHandler = handler;
	}
	
	@Override
	public void setOnGoBack(EventHandler<ActionEvent> handler) {
		this.goBackHandler = handler;
	}

	@Override
	public void setDatabase(String dbKey) {
		dbImpl = PluginsLoader.getPluginByKey(dbKey);
		model.setDbImpl(dbImpl);
		dbSelectionScreen.setDbFactory(dbImpl);
		dbSelectionScreen.loadDbOptions();
	}

}
