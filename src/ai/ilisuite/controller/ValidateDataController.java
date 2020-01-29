package ai.ilisuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.ilisuite.base.IliExecutable;
import ai.ilisuite.util.exception.ExitException;
import ai.ilisuite.util.params.ParamsUtil;
import ai.ilisuite.util.wizard.BuilderWizard;
import ai.ilisuite.view.FinishActionView;
import ai.ilisuite.view.ValidateOptionsView;
import ai.ilisuite.view.wizard.EmptyWizardException;
import ai.ilisuite.view.wizard.Wizard;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class ValidateDataController implements ParamsController {
	private IliExecutable interlisExecutable;
	
	private List<Map<String, String>> paramsList;
	private Wizard wizard;
	
	private EventHandler<ActionEvent> finishHandler;
	private EventHandler<ActionEvent> goBackHandler;
	
	private Thread commandExecutionThread;
	
	public ValidateDataController(IliExecutable interlisExecutable) throws IOException {
		this.interlisExecutable = interlisExecutable;
		paramsList = new ArrayList<Map<String, String>>();
		wizard = BuilderWizard.buildMainWizard();
		wizard.add(new ValidateOptionsView(this));
		wizard.add(new FinishActionView(this));
		
		EventHandler<ActionEvent> goBack = 
				(ActionEvent e) -> { if(goBackHandler != null) { goBackHandler.handle(e); }};
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
		return ParamsUtil.getCommand(params);
	}

	@Override
	public boolean execute() {
		SimpleBooleanProperty booleanResult = new SimpleBooleanProperty();
		List<String> command = getCommand();
		
		Task<Boolean> task = new Task<Boolean>(){
			@Override
			protected Boolean call() throws Exception {
				try {
					interlisExecutable.run(command);
					return true;
				} catch (ExitException e) {
					System.out.println(e.status);
					return false;
				}
			}
		};
		task.setOnSucceeded(workerStateEvent ->{
			wizard.setNextDisable(false);
			
			if(booleanResult.getValue())
				wizard.setExecuted(true);
		});
		
		task.setOnFailed(workerStateEvent -> {
			wizard.setNextDisable(false);
		});
		
		booleanResult.bind(task.valueProperty());
		wizard.setNextDisable(true);
		
		commandExecutionThread = new Thread(task);
		commandExecutionThread.start();
		
		return true;
	}
}
