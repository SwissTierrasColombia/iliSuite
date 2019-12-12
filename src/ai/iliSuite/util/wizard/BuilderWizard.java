package ai.iliSuite.util.wizard;

import java.io.IOException;

import ai.iliSuite.util.constant.Constants;
import ai.iliSuite.view.wizard.Wizard;
import javafx.geometry.Insets;

public class BuilderWizard {
	static public Wizard buildMainWizard() throws IOException {
		Wizard result = new Wizard();
		result.setMargin(new Insets(Constants.MAIN_WIZARD_CONTENT_MARGIN));
		result.setHasExecution(true);
		
		result.setMaxHeight(Constants.MAIN_WIZARD_CONTENT_HEIGHT);
		result.setMinHeight(Constants.MAIN_WIZARD_CONTENT_HEIGHT);
		result.setPrefHeight(Constants.MAIN_WIZARD_CONTENT_HEIGHT);
		
		return result;
	}
}
