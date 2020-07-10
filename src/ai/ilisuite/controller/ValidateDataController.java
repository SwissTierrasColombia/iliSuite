package ai.ilisuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.ilisuite.IliExec;
import ai.ilisuite.util.log.LogListenerExt;
import ai.ilisuite.util.params.ParamsUtil;
import ai.ilisuite.util.wizard.BuilderWizard;
import ai.ilisuite.view.FinishActionView;
import ai.ilisuite.view.ValidateOptionsView;
import ai.ilisuite.view.wizard.EmptyWizardException;
import ai.ilisuite.view.wizard.Wizard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class ValidateDataController implements ParamsController {
	private List<Map<String, String>> paramsList;
	private Wizard wizard;
	
	private EventHandler<ActionEvent> finishHandler;
	private EventHandler<ActionEvent> goBackHandler;
	
	private IliExec exec;
	
	private FinishActionView finalStep;
	
	public ValidateDataController() throws IOException {
		paramsList = new ArrayList<Map<String, String>>();
		wizard = BuilderWizard.buildMainWizard();
		wizard.add(new ValidateOptionsView(this));
		finalStep = new FinishActionView(this);

		wizard.add(finalStep);
		
		EventHandler<ActionEvent> goBack = 
				(ActionEvent e) -> { cancelExecution(); if(goBackHandler != null) { goBackHandler.handle(e); }};
		EventHandler<ActionEvent> finish = 
				(ActionEvent e) -> { if(finishHandler != null) { finishHandler.handle(e); }};
		
		wizard.setOnBack(goBack);
		wizard.setOnCancel(goBack);
		wizard.setOnFinish(finish);
		wizard.setOnExecute((ActionEvent e) -> execute());
		
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
	
	public void setOnFinish(EventHandler<ActionEvent> handler) {
		this.finishHandler = handler;
	}
	
	public void setOnGoBack(EventHandler<ActionEvent> handler) {
		this.goBackHandler = handler;
	}
	
	public String getTextParams() {
		List<String> command = getCommand();
		
		return ParamsUtil.getStringArgs(command, true);
	}
	
	private List<String> getCommand(){
		Map<String, String> params = new HashMap<String, String>();
		
		for(Map<String, String> item:paramsList) {
			params.putAll(item);
		}
		ParamsUtil.addCommonParameters(params);
		return ParamsUtil.getCommand(params);
	}

	@Override
	public boolean execute() {
		List<String> command = getCommand();
		
		LogListenerExt log = new LogListenerExt(finalStep.getTxtConsole());
		
		exec = new IliExec("java -jar ./programs/ilivalidator-1.11.6-bindist/ilivalidator-1.11.6.jar");

		exec.addListener(log);
		String[] list = command.toArray(new String[] {});
		
		exec.setOnSucceeded((ActionEvent e) ->{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
				wizard.setNextDisable(false);
				wizard.setExecuted(true);
				}});
		});
		
		wizard.setNextDisable(true);
		
		exec.setOnFailed(workerStateEvent -> {
			wizard.setNextDisable(false);
		});
		
		exec.exec(list);
		return true;
	}
	
	@Override
	public void cancelExecution() {
		wizard.setNextDisable(false);
		wizard.setExecuted(false);
		if(exec!=null) exec.cancelExecution();
	}
}
