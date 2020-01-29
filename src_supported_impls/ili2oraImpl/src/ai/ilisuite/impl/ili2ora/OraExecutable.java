package ai.ilisuite.impl.ili2ora;
import java.util.List;

import ai.ilisuite.base.IliExecutable;
import ch.ehi.ili2ora.OraMain;

public class OraExecutable implements IliExecutable {

	@Override
	public void run(List<String> params) {
		String[] args = params.toArray(new String[0]);
		(new OraMain()).domain(args);
	}

}
