package ai.iliSuite.util.plugin;

import java.util.HashMap;
import java.util.Map;

import ai.iliSuite.impl.ImplFactory;
import ai.iliSuite.impl.ili2fgdb.Ili2fgdbImpl;
import ai.iliSuite.impl.ili2gpkg.Ili2gpkgImpl;
import ai.iliSuite.impl.ili2mssql.Ili2MsSqlImpl;
import ai.iliSuite.impl.ili2ora.Ili2oraImpl;
import ai.iliSuite.impl.ili2pg.Ili2pgImpl;

public class PluginsLoader {
	static Map<String, ImplFactory> plugins;

	static {
		plugins = new HashMap<String, ImplFactory>();
	}

	public static void Load() {
		ImplFactory iplug = null;
		
		iplug = new Ili2fgdbImpl();	
		plugins.put(iplug.getAppName(), iplug);

		iplug = new Ili2gpkgImpl();	
		plugins.put(iplug.getAppName(), iplug);
		
		iplug = new Ili2MsSqlImpl();	
		plugins.put(iplug.getAppName(), iplug);
		
		iplug = new Ili2oraImpl();	
		plugins.put(iplug.getAppName(), iplug);
		
		iplug = new Ili2pgImpl();	
		plugins.put(iplug.getAppName(), iplug);
	}

	public static Map<String, ImplFactory> getPlugins() {
		return plugins;
	}

	public static ImplFactory getPluginByKey(String key) {
		return plugins.get(key);
	}
}