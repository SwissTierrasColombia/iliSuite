package ai.iliSuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ai.iliSuite.base.InterlisExecutable;
import ai.iliSuite.util.exception.ExitException;
import ai.iliSuite.view.FinishActionView;
import ai.iliSuite.view.ValidateOptionsView;
import ai.iliSuite.view.wizard.EmptyWizardException;
import ai.iliSuite.view.wizard.Wizard;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class ValidateDataController implements ParamsController {
	private InterlisExecutable interlisExecutable;
	
	private List<HashMap<String, String>> paramsList;
	private Wizard wizard;
	
	private EventHandler<ActionEvent> finishHandler;
	private EventHandler<ActionEvent> goBackHandler;
	
	private Thread commandExecutionThread;
	
	public ValidateDataController(InterlisExecutable interlisExecutable) throws IOException {
		this.interlisExecutable = interlisExecutable;
		paramsList = new ArrayList<HashMap<String, String>>();
		wizard = new Wizard();		
		wizard.add(new ValidateOptionsView(this));
		wizard.add(new FinishActionView(this));
		
		EventHandler<ActionEvent> goBack = 
				(ActionEvent e) -> { if(goBackHandler != null) { goBackHandler.handle(e); }};
		EventHandler<ActionEvent> finish = 
				(ActionEvent e) -> { if(finishHandler != null) { finishHandler.handle(e); }};
		
		wizard.setOnBack(goBack);
		wizard.setOnCancel(goBack);
		wizard.setOnFinish(finish);
		
		try {
			wizard.init();
		} catch (EmptyWizardException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addParams(HashMap<String, String> params) {
		paramsList.add(params);
	}

	@Override
	public void removeParams(HashMap<String, String> params) {
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
		HashMap<String, String> params = interlisExecutable.getParams();
		
		for(HashMap<String, String> item:paramsList) {
			params.putAll(item);
		}
		
		return interlisExecutable.getArgs(true);
	}

	@Override
	public boolean execute() {
		SimpleBooleanProperty booleanResult = new SimpleBooleanProperty();
		Task<Boolean> task = new Task<Boolean>(){
			@Override
			protected Boolean call() throws Exception {
				try {
					interlisExecutable.run();
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
}