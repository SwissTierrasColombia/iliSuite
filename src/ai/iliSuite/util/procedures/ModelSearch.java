package ai.iliSuite.util.procedures;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import ch.interlis.ili2c.modelscan.IliFile;
import ch.interlis.ili2c.modelscan.IliModel;
import ch.interlis.ilirepository.IliFiles;
import ch.interlis.ilirepository.impl.RepositoryAccess;

public class ModelSearch {

	public static List<String> search(String URIs) {

		ArrayList<String> modelURIs = new ArrayList<String>(Arrays.asList(URIs.split(";")));
		List<String> models = new ArrayList<String>();
		
		
		for (String uri : modelURIs) {

			if (uri.trim().startsWith("http")) {
				
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
				

			} else {
				File folder = new File(uri);
				String[] matchingFilesNames = folder.list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.endsWith(".ili");
					}
				});

				for (String fileName : matchingFilesNames) {
					try {
						String path = folder.getCanonicalPath() + folder.separator + fileName;
						Charset charset = Charset.forName("windows-1254");
						Stream<String> lines = Files.lines(Paths.get(path), charset)
								.filter(line -> !line.matches("/\\*(?:.|[\\n\\r])*?\\*/")
										&& !line.trim().startsWith("//")
										&& line.matches(".*MODEL[\\s]+[a-zA-Z_0-9]+[\\s]+.*"));

						lines.forEach(each -> {
							Pattern p = Pattern.compile("(.*MODEL[\\s])+([a-zA-Z_0-9]+)([\\s]+.*)");
							Matcher m = p.matcher(each);
							m.matches();
							if (!models.contains(m.group(2)))
								models.add(m.group(2));

						});
						lines.close();
					} catch (IOException e) {

					}

				}
			}
		}
		return models;
	}

}
