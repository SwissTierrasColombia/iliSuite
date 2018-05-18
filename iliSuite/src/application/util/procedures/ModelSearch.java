package application.util.procedures;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ModelSearch {
	
	public static List<String> search(String URIs) {
		
		
		ArrayList<String> modelURIs = new ArrayList<String>(Arrays.asList(URIs.split(";")));
		List<String> models = new ArrayList<String>();
		
		for(String uri : modelURIs) {
						
			File folder = new File(uri);
			String[] matchingFilesNames = folder.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
			        return name.endsWith(".ili");
			    }
			});
			
			for(String fileName : matchingFilesNames) {
				try {
					String path = folder.getCanonicalPath()+folder.separator+fileName;
					Stream <String> lines = Files.lines(Paths.get(path), Charset.defaultCharset()).
							filter(line -> !line.matches("/\\*(?:.|[\\n\\r])*?\\*/") && !line.trim().startsWith("//") && line.matches(".*MODEL[\\s]+[a-zA-Z_0-9]+[\\s]+.*"));
					
					lines.forEach(each -> {
						Pattern p = Pattern.compile("(.*MODEL[\\s])+([a-zA-Z_0-9]+)([\\s]+.*)");
						Matcher m = p.matcher(each);
						m.matches();
						if(!models.contains(m.group(2)))
							models.add(m.group(2));
						
						});
					lines.close();
				} catch (IOException e) {
				
				}

			}
		}
		return models;
	}

}
