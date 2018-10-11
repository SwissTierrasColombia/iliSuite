package base;

public interface Iplugin {
	public void load();
	// public Parent run();
	public void unload();
	public String getName();
	public String getAppName();
	public String getAppVersion();
}
