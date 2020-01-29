package ai.ilisuite.util.procedures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ch.interlis.ili2c.modelscan.IliFile;
import ch.interlis.ili2c.modelscan.IliModel;
import ch.interlis.ilirepository.IliFiles;
import ch.interlis.ilirepository.impl.RepositoryAccess;

public class ModelSearch {

	@SuppressWarnings("rawtypes")
	public static List<String> search(String URIs) {

		ArrayList<String> modelURIs = new ArrayList<String>(Arrays.asList(URIs.split(";")));
		List<String> models = new ArrayList<String>();
		
		for (String uri : modelURIs) {
			try {
				RepositoryAccess repo = new RepositoryAccess();
				IliFiles files = repo.getIliFiles(uri);
				for(Iterator<IliFile> filei=files.iteratorFile();filei.hasNext();) {
					IliFile file=filei.next();
					for(Iterator modeli=file.iteratorModel();modeli.hasNext();){
						IliModel model=(IliModel)modeli.next();
						
						if (!models.contains(model.getName()))
							models.add(model.getName());
					}
				}
			}catch(Exception e) {
				//Do Nothing
			}
		}
		return models;
	}

}
