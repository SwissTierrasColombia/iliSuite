package ai.ilisuite.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import ai.ilisuite.view.ValidateOptionsView;
import ai.ilisuite.view.util.navigation.EnumPaths;
import ai.ilisuite.view.wizard.Wizard;


public class ValidateDataController extends IliController {
	private String ilivalidatorVersion;
	public ValidateDataController() throws IOException {
		super();
		ResourceBundle versionBundle = ResourceBundle.getBundle(EnumPaths.VERSION_BUNDLE.getPath());
		ilivalidatorVersion = versionBundle.getString("ilivalidatorVersion"); 
	}

	@Override
	protected String getExecutablePath() {
		
		File file = new File("programs/ilivalidator-" + ilivalidatorVersion + 
				"-bindist/ilivalidator-" + ilivalidatorVersion + ".jar");
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
