package ai.ilisuite.base;

import java.io.IOException;
import java.util.ResourceBundle;

import ai.ilisuite.view.util.navigation.EnumPaths;


public class UmlEditor {
	private String umlEditorVersion;
	
	public UmlEditor() {
		ResourceBundle versionBundle = ResourceBundle.getBundle(EnumPaths.VERSION_BUNDLE.getPath());
		umlEditorVersion = versionBundle.getString("umlEditorVersion");
	}
	public void run() {
		try {
			Runtime.getRuntime().exec("java -jar ./programs/umleditor-" + umlEditorVersion + "/umleditor-" + umlEditorVersion + ".jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getUmlEditorVersion() {
		return umlEditorVersion;
	}
}
