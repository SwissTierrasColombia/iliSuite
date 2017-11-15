package validateModel;

import application.util.navigation.EnumPaths;
import application.util.navigation.Navigable;

public class CompilerOptionsController implements Navigable{

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public EnumPaths getNextPath() {
		return EnumPaths.VAL_MODEL_FINISH_MODEL_VALIDATION;
	}

	@Override
	public boolean isFinalPage() {
		return false;
	}

}
