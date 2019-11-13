package ai.iliSuite.controller;

public class DbDescription {
	private String Key;
	private String Name;
	private String HelpText;
	
	public String getKey() {
		return Key;
	}
	public String getName() {
		return Name;
	}
	public String getHelpText() {
		return HelpText;
	}
	public void setKey(String key) {
		Key = key;
	}
	public void setName(String name) {
		Name = name;
	}
	public void setHelpText(String helpText) {
		HelpText = helpText;
	}
}
