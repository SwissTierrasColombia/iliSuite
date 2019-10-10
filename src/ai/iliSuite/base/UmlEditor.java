package ai.iliSuite.base;

import java.io.IOException;
import java.util.List;

public class UmlEditor implements InterlisExecutable {
	@Override
	public void run() {
		try {

			Runtime.getRuntime().exec("java -jar ./programs/umleditor-3.6.5/umleditor.jar");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addParam(String param, String value) {
	}

	@Override
	public void removeParam(String param) {
	}

	@Override
	public List<String> getCommand() {
		return null;
	}

	@Override
	public String getArgs(boolean hideSensitiveData) {
		return null;
	}

}
