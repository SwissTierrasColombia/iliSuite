package ai.ilisuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import ai.ilisuite.base.IliExec;
import ai.ilisuite.base.log.IliAppMessagesClasifier;
import ai.ilisuite.base.log.IliMessageReceiver;
import ai.ilisuite.base.log.IliMessageType;
import ai.ilisuite.util.params.ParamsUtil;
import ai.ilisuite.util.wizard.BuilderWizard;
import ai.ilisuite.view.FinishActionView;
import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.wizard.EmptyWizardException;
import ai.ilisuite.view.wizard.Wizard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public abstract class IliController implements ParamsController, IliMessageReceiver, ExecutionController {
	protected ResourceBundle bundle;
	private List<Map<String,String>> paramsList;
	
	private EventHandler<ActionEvent> finishHandler;
	private EventHandler<ActionEvent> goBackHandler;

	private Wizard wizard;
	
	private FinishActionView finalStep;
	private Map<IliMessageType, List<String>> messages;
	private String registerReport = "";
	private Boolean successfullProcess;
	
	private IliExec exec;
	
	abstract protected String getExecutablePath();
	abstract protected void addCustomParams(Map<String, String> params);
	abstract protected void addWizardSteps(Wizard wizard) throws IOException;
	
	public IliController() throws IOException {
		paramsList = new ArrayList<>();
		bundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		initWizard();
		resetResults();
	}
	
	private void initWizard() throws IOException {
		EventHandler<ActionEvent> goBack = 
				(ActionEvent e) -> { cancelExecution(); if(goBackHandler != null) { goBackHandler.handle(e); }};
		EventHandler<ActionEvent> finish = 
				(ActionEvent e) -> { if(finishHandler != null) { finishHandler.handle(e); }};

		finalStep = new FinishActionView(this);
		wizard = BuilderWizard.buildMainWizard();
		wizard.setOnBack(goBack);
		wizard.setOnCancel(goBack);
		wizard.setOnFinish(finish);
		wizard.setOnExecute((ActionEvent e) -> execute());
		
		addWizardSteps(wizard);
		
		wizard.add(finalStep);
		
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

	public String getTextParams() {
		List<String> command = getCommand();
		
		return getExecutablePath() + " " + ParamsUtil.getStringArgs(command, true);
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
	public Parent getGraphicComponent() {
		return wizard.getGraphicComponent();
	}

	@Override
	public void message(String message, IliMessageType messageType) {
		if(messageType == IliMessageType.Error || messageType == IliMessageType.Warning
				|| messageType == IliMessageType.Other) {
			addMessage(message, messageType);
			finalStep.addIssue(message, messageType);
		} else if (messageType == IliMessageType.RegisterReport){
			registerReport = message;
		}
	}
	
	private List<String> getCommand(){
		Map<String, String> params = new HashMap<String, String>();
		addCustomParams(params);
		for(Map<String, String> item:paramsList) {
			params.putAll(item);
		}
		ParamsUtil.addCommonParameters(params);
		return ParamsUtil.getCommand(params);
	}
	
	private void addMessage(String message, IliMessageType messageType) {
		List<String> messageList = messages.get(messageType);
		
		if(messageList == null) {
			messageList = new ArrayList<>();
			messages.put(messageType, messageList);
		}
		
		messageList.add(message);
	}

	@Override
	public boolean execute() {
		List<String> command = getCommand();
		
		exec = new IliExec(getExecutablePath());
		
		exec.addListener(new IliAppMessagesClasifier(this));
		String[] list = command.toArray(new String[] {});
		
		exec.setOnSucceeded((ActionEvent e) ->{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					finalStep.disableProgressIndicator();
					wizard.setNextDisable(false);
					wizard.setExecuted(true);
					finalStep.setProccessResult(true);

					successfullProcess = true;
					finalStep.setSummary(getSummary());
				}});
		});
		
		wizard.setNextDisable(true);
		
		exec.setOnFailed(workerStateEvent -> {
			finalStep.disableProgressIndicator();
			wizard.setNextDisable(false);
			finalStep.setProccessResult(false);
			
			successfullProcess = false;
			finalStep.setSummary(getSummary());
		});
		
		exec.exec(list);
		finalStep.enableProgressIndicator();
		return true;
	}

	@Override
	public void cancelExecution() {
		finalStep.disableProgressIndicator();
		finalStep.reset();
		wizard.setNextDisable(false);
		wizard.setExecuted(false);
		resetResults();
		if(exec!=null) exec.cancelExecution();
	}
	
	private void resetResults() {
		messages = new HashMap<>();
		registerReport = "";
		successfullProcess = null;
	}
	
	public String getSummary() {
		String result = "";
		if(successfullProcess != null) {
			String strOthers = bundle.getString("finish.others");
			String strWarnings = bundle.getString("finish.warnings");
			String strErrors = bundle.getString("finish.errors");
			String strProcessedRecords = bundle.getString("finish.processedRecords");
			List<String> abc = new ArrayList<String>();
			
			String.join(",", abc);
			
			result = successfullProcess.booleanValue()?
					bundle.getString("finish.processSuccessful")
					:bundle.getString("finish.processFailed");
			
			if(messages.get(IliMessageType.Error)!=null)
				result += "\n" + strErrors + ": " + messages.get(IliMessageType.Error).size();
			
			if(messages.get(IliMessageType.Warning)!=null)
				result += "\n" + strWarnings + ": " + messages.get(IliMessageType.Warning).size();
			
			if(messages.get(IliMessageType.Other)!=null)
				result += "\n" + strOthers + ": " + messages.get(IliMessageType.Other).size();
			
			if(registerReport != null && !registerReport.isEmpty())
				result += "\n\n" + strProcessedRecords + ":" + "\n\n" + registerReport;
		}
		
		return result;
	}
}
