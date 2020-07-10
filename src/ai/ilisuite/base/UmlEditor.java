package ai.ilisuite.base;

import java.io.IOException;


public class UmlEditor {
	public void run() {
		try {
			Runtime.getRuntime().exec("java -jar ./programs/umleditor-3.6.6/umleditor.jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
