package ai.ilisuite.util.plugin;

import java.util.HashMap;
import java.util.Map;

import ai.ilisuite.impl.ImplFactory;
import ai.ilisuite.impl.ili2fgdb.Ili2fgdbImpl;
import ai.ilisuite.impl.ili2gpkg.Ili2gpkgImpl;
import ai.ilisuite.impl.ili2mssql.Ili2MsSqlImpl;
import ai.ilisuite.impl.ili2ora.Ili2oraImpl;
import ai.ilisuite.impl.ili2pg.Ili2pgImpl;

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