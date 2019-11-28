package ai.iliSuite.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class UmlEditor implements InterlisExecutable {
	@Override
	public void run() {
		try {

			Runtime.getRuntime().exec("java -jar ./programs/umleditor-3.6.6/umleditor.jar");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getCommand() {
		return null;
	}

	@Override
	public String getArgs(boolean hideSensitiveData) {
		return null;
	}

	@Override
	public HashMap<String, String> getParams() {
		return null;
	}

}
