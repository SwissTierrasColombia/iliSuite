package ai.ilisuite.base;

import java.util.List;

import org.interlis2.validator.Main;

public class IliValidator implements IliExecutable {
	
	@Override
	public void run(List<String> params) {
		String[] args = params.toArray(new String[0]);
		Main.main(args);
	}
}
