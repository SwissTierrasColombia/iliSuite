package ai.ilisuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.fxmisc.richtext.StyleClassedTextArea;

import ai.ilisuite.util.log.LogListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.concurrent.Task;


public class IliExec {
	
	private Process proc;
	private List<LogListener> observers;
	private String app;
	protected EventHandler<ActionEvent> onSucceeded;
	protected EventHandler<ActionEvent> onFailed;
	
	
	public IliExec(String app) {
		observers = new ArrayList<>();
		this.app = app;
	}
	
	public void addListener(LogListener listener) {
		observers.add(listener);
	}
	
	public void removeListener(LogListener listener) {
		observers.remove(listener);
	}
	
	public void cancelExecution() {
		if(proc!=null) {
			proc.destroy();
		}
	}
	
	private void sendMessage(String message, boolean isErrorStream) {
		for(LogListener listener:observers) {
			listener.writeMessage(message, isErrorStream);
		}
	}

	public void exec(String[] params) {
		try {
			
			proc = Runtime.getRuntime().exec(app + " " +  String.join(" ",params));
			
            ExecutableStream errorGobbler = new 
                    ExecutableStream(proc.getErrorStream(), true);            
                
            ExecutableStream outputGobbler = new 
                ExecutableStream(proc.getInputStream(), false);
            
    		Thread errorThread = new Thread(errorGobbler);
    		Thread outputThread = new Thread(outputGobbler);
    		errorThread.start();
            outputThread.start();
            
    		Task<Boolean> task = new Task<Boolean>(){
    			@Override
    			protected Boolean call() throws Exception {
					int result;
					try {
						result = proc.waitFor();						
					} catch (InterruptedException e) {
						result = -1;
					}
					if(result==0 && onSucceeded != null) {
						ActionEvent arg0 = new ActionEvent();
						onSucceeded.handle(arg0);
					} else if(onFailed != null) {
						ActionEvent arg0 = new ActionEvent();
						onFailed.handle(arg0);
					}
					return result == 0;
				}
			};
            
			Thread commandExecutionThread = new Thread(task);
			commandExecutionThread.start();
                        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOnSucceeded(EventHandler<ActionEvent> onSucceeded) {
		this.onSucceeded = onSucceeded;
	}

	public void setOnFailed(EventHandler<ActionEvent> onFailed) {
		this.onFailed = onFailed;
	}

	class ExecutableStream extends Task<Boolean> {
	    InputStream is;
	    boolean isErrorStream;
	    
	    StyleClassedTextArea outText;
	    
	    ExecutableStream(InputStream is, boolean isErrorStream) {
	        this.is = is;
	        this.isErrorStream = isErrorStream;
	    }
	    
	    @Override
	    protected Boolean call() {
	    	Boolean result = null;
	        try {
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line=null;
	            while ( (line = br.readLine()) != null) {
	        		sendMessage(line, isErrorStream);
	            }
	            result = true;
	        } catch (IOException ioe) {
	        	result = false;  
	        }
	        return result;
	    }
	}
}
