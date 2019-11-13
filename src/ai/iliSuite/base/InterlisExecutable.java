package ai.iliSuite.base;

import java.util.HashMap;
import java.util.List;

public interface InterlisExecutable {
	public void run();
	public HashMap<String, String> getParams();
	public List<String> getCommand();
	public String getArgs(boolean hideSensitiveData);
}
