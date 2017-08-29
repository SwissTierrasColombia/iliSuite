package application.util.navigation;

public interface Navigable{

	public boolean validate();
	public EnumPaths getNextPath();
	public boolean isFinalPage();
}
