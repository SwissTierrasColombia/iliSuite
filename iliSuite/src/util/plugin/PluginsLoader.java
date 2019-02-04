package util.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import base.IPluginDb;
import base.Iplugin;

public class PluginsLoader {
	static Map<String, Iplugin> plugins;

	static {
		plugins = new HashMap<String, Iplugin>();
	}

	public static void Load() {

		File pluginDirectory = new File("plugins");

		if (pluginDirectory.exists()) {
			File[] pluginsFiles = pluginDirectory.listFiles();

			for (int k = 0; k < pluginsFiles.length; k++) {
				if (pluginsFiles[k].isFile() && pluginsFiles[k].getName().endsWith(".jar")) {
					loadClass(pluginsFiles[k]);
				}
			}
		} else {
			pluginDirectory.mkdir();
		}
	}

	private static void loadClass(File jarFile) {
		ClassLoader classLoader1;
		try {
			classLoader1 = URLClassLoader.newInstance(new URL[] { jarFile.toURI().toURL() });		
			InputStream stream = classLoader1.getResourceAsStream("metadata.properties");
	
			Properties properties = new Properties();

			if(stream!=null) {
				properties.load(stream);
				
				if(properties.getProperty("name") != null && !properties.getProperty("name").isEmpty()) {
					String pluginName = properties.getProperty("name");
					pluginName = pluginName.substring(0, 1).toUpperCase() + pluginName.substring(1, pluginName.length());
					String packageName = pluginName.toLowerCase();
					
					String absoluteClassName = "iliSuite.plugin." + packageName+"." + pluginName + "Plugin";
					
					Iplugin pluginInstance = (Iplugin) classLoader1.loadClass(absoluteClassName).newInstance();
			
					plugins.put(pluginName, pluginInstance);
			
					pluginInstance.load();				
				}
			} else {
				System.out.println(jarFile.getName() + " no contiene un archivo de propiedades");
			}
		}
		catch(MalformedURLException E) {
			System.out.println(jarFile.getName() + " Error en la ruta del jar");
		} catch (InstantiationException e) {
			System.out.println(jarFile.getName() + " Error al instanciar");
		} catch (IllegalAccessException e) {
			System.out.println(jarFile.getName() + " Error de acceso");
		} catch (ClassNotFoundException e) {
			System.out.println(jarFile.getName() + " Clase principal no encontrada");
		} catch (IOException e) {
			System.out.println(jarFile.getName() + " Error cargando archivo de propiedades del plugin");	
		}
	}

	public static Map<String, Iplugin> getPlugins() {
		return plugins;
	}

	public static Iplugin getPluginByKey(String key) {
		return plugins.get(key);
	}
}