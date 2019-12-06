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
		ImplFactory[] lstIplug = new ImplFactory[] {
				new Ili2fgdbImpl(),
				new Ili2gpkgImpl(),
				new Ili2MsSqlImpl(),
				new Ili2oraImpl(),
				new Ili2pgImpl()};

		for(ImplFactory iplug:lstIplug) {
			String key = iplug.getDbDescription().getAppName(); 
			plugins.put(key, iplug);
		}
	}

	public static Map<String, ImplFactory> getPlugins() {
		return plugins;
	}

	public static ImplFactory getPluginByKey(String key) {
		return plugins.get(key);
	}
}