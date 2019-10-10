package ai.iliSuite.base;

import java.util.List;

public interface InterlisExecutable {
	public void run();
	public void addParam(String param, String value);
	public void removeParam(String param);
	public List<String> getCommand();
	public String getArgs(boolean hideSensitiveData);
}
