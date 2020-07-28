package ai.ilisuite.controller;

public interface ExecutionController {
	public boolean execute();
	public void cancelExecution();	
	public String getTextParams();
}
