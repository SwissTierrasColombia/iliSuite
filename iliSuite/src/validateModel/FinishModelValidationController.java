package validateModel;

import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;

public class FinishModelValidationController implements Navigable {

	@Override
	public boolean validate() {
		return false;
	}

	@Override
	public EnumPaths getNextPath() {
		return null;
	}

	@Override
	public boolean isFinalPage() {
		return true;
	}

}
