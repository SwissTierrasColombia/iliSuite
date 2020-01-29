package ai.ilisuite.view.wizard;

import javafx.scene.Parent;

public abstract class StepViewController {
	
	public void cancel(StepArgs args) {	
	}

	public void loadedPage(StepArgs args) {	
	}

	public void end(StepArgs args) {	
	}

	public void goBack(StepArgs args) {	
	}

	public void goForward(StepArgs args) {	
	}
	
	abstract public Parent getGraphicComponent();
}
