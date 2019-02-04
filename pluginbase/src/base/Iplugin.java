package base;

public interface Iplugin {
	public void load();
	public void unload();
	public String getAppName();
	public String getAppVersion();
}
