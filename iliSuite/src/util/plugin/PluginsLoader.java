package util.plugin;

import java.util.HashMap;
import java.util.Map;
import base.IPluginDb;
import base.Iplugin;
import iliSuite.plugin.ili2fgdb.Ili2fgdbPlugin;
import iliSuite.plugin.ili2gpkg.Ili2gpkgPlugin;
import iliSuite.plugin.ili2mssql.Ili2MsSqlPlugin;
import iliSuite.plugin.ili2ora.Ili2oraPlugin;
import iliSuite.plugin.ili2pg.Ili2pgPlugin;

public class PluginsLoader {
	static Map<String, Iplugin> plugins;

	static {
		plugins = new HashMap<String, Iplugin>();
	}

	public static void Load() {
		IPluginDb iplug = null;
		
		iplug = new Ili2fgdbPlugin();	
		plugins.put(iplug.getAppName(), iplug);

		iplug = new Ili2gpkgPlugin();	
		plugins.put(iplug.getAppName(), iplug);
		
		iplug = new Ili2MsSqlPlugin();	
		plugins.put(iplug.getAppName(), iplug);
		
		iplug = new Ili2oraPlugin();	
		plugins.put(iplug.getAppName(), iplug);
		
		iplug = new Ili2pgPlugin();	
		plugins.put(iplug.getAppName(), iplug);
	}

	public static Map<String, Iplugin> getPlugins() {
		return plugins;
	}

	public static Iplugin getPluginByKey(String key) {
		return plugins.get(key);
	}
}