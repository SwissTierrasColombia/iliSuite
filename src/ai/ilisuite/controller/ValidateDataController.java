package ai.ilisuite.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import ai.ilisuite.view.ValidateOptionsView;
import ai.ilisuite.view.wizard.Wizard;


public class ValidateDataController extends IliController {
	
	public ValidateDataController() throws IOException {
		super();
	}

	@Override
	protected String getExecutablePath() {
		File file = new File("programs/ilivalidator-1.11.6-bindist/ilivalidator-1.11.6.jar");
		return "java -jar  \"" + file.getAbsolutePath() + "\"";
	}

	@Override
	protected void addCustomParams(Map<String, String> params) {
		// this doesn't require custom parameters
	}

	@Override
	protected void addWizardSteps(Wizard wizard) throws IOException {
		wizard.add(new ValidateOptionsView(this));
	}
}
