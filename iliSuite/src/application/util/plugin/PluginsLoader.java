package application.util.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import base.IPluginDb;
import base.Iplugin;
import iliSuite.plugin.ili2fgdb.Ili2fgdbPlugin;
import iliSuite.plugin.ili2gpkg.Ili2gpkgPlugin;
import iliSuite.plugin.ili2mssql.Ili2MsSqlPlugin;
import iliSuite.plugin.ili2pg.Ili2pgPlugin;

public class PluginsLoader {
	static Map<String, Iplugin> plugins;

	static {
		plugins = new HashMap<String, Iplugin>();
	}

	private static void LoadMainPlugins() {
		
		IPluginDb item;
		
		item = new Ili2pgPlugin();
		plugins.put(item.getName(), item);
		
		item = new Ili2MsSqlPlugin();
		plugins.put(item.getName(), item);
		
		item = new Ili2gpkgPlugin();
		plugins.put(item.getName(), item);
		
		item = new Ili2fgdbPlugin();
		plugins.put(item.getName(), item);
	}

	public static void Load() {

		File pluginDirectory = new File("./plugins");

		if (pluginDirectory.exists()) {
			File[] pluginsFiles = pluginDirectory.listFiles();

			for (int k = 0; k < pluginsFiles.length; k++) {
				if (pluginsFiles[k].isFile() && pluginsFiles[k].getName().endsWith(".jar")) {
					loadClass(pluginsFiles[k]);
				}
			}
		} else {
			// TODO Verificar excepción catch(SecurityException se){
			pluginDirectory.mkdir();
		}

		LoadMainPlugins();
	}

	private static void loadClass(File jarFile) {

		String pluginName = jarFile.getName().replace(".jar", "");

		ClassLoader classLoader1;
		try {
			classLoader1 = URLClassLoader.newInstance(new URL[] { jarFile.toURI().toURL() });

			String packageName = pluginName.replace("Plugin", "").toLowerCase();
			// TODO arreglar nombre del package
			
			String absoluteClassName = "iliSuite.plugin." + packageName+"." + pluginName;
			Iplugin pluginInstance = (Iplugin) classLoader1.loadClass(absoluteClassName).newInstance();

			plugins.put(pluginName, pluginInstance);

			// TODO Agregar mensaje de plugin cargado
			pluginInstance.load();
		} catch (MalformedURLException e) {
			System.out.println("Error loading plugin " + pluginName);
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("Error loading plugin " + pluginName);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Error loading plugin " + pluginName);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading plugin " + pluginName);
			e.printStackTrace();
		}
	}

	public static Map<String, Iplugin> getPlugins() {
		return plugins;
	}

	public static Iplugin getPluginByKey(String key) {
		return plugins.get(key);
	}

}
