package ai.ilisuite.base;

import java.io.IOException;
import java.util.List;

public class UmlEditor implements IliExecutable {
	@Override
	public void run(List<String> params) {
		try {
			Runtime.getRuntime().exec("java -jar ./programs/umleditor-3.6.6/umleditor.jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
