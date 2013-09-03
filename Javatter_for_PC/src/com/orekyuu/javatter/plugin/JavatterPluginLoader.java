package com.orekyuu.javatter.plugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.orekyuu.javatter.controller.MainWindowController;
import com.orekyuu.javatter.controller.PluginController;
import com.orekyuu.javatter.view.MainWindowView;

public class JavatterPluginLoader
{
	private List<JavatterPlugin> plugins = new ArrayList<JavatterPlugin>();
	private static List<JavatterProfileBuilder> profileBuilders=new ArrayList<JavatterProfileBuilder>();
	private static List<TweetObjectBuilder> builders=new ArrayList<TweetObjectBuilder>();

	public void loadPlugins(File file)
	{
		if (!file.exists()) {
			file.mkdir();
		}

		try{
			URLClassLoader loader=(URLClassLoader) getClass().getClassLoader();

			for (File f : file.listFiles()){
				if (f.getName().endsWith(".jar")) {
					load(f,loader);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		try{
			URLClassLoader loader=(URLClassLoader) getClass().getClassLoader();
			load(loader);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void initPlugins(PluginController pluginTab, MainWindowController controller, MainWindowView view)
	{
		for (JavatterPlugin plugin : this.plugins) {
			plugin.setMainViewAndController(view, controller);
			plugin.initPlugin();
			pluginTab.addPluginName(plugin.getPluginName(),plugin.getVersion());
			pluginTab.addPluginConfig(plugin.getPluginName(), plugin.getPluginConfigView());
		}

		for(JavatterPlugin plugin : this.plugins){
			plugin.postInit();
		}
	}

	private void load(File file,ClassLoader loader)throws Exception
	{
		Method m=URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
		m.setAccessible(true);
		m.invoke(loader, new Object[]{file.toURI().toURL()});

		JarFile jar=new JarFile(file);
		Manifest manifest=jar.getManifest();
		Attributes attributes = manifest.getMainAttributes();
		String mainClass = attributes.getValue("Plugin-Main");
		Class<?> plugin=loader.loadClass(mainClass);
		Object obj=plugin.newInstance();
		if(obj instanceof JavatterPlugin){
			JavatterPlugin p=(JavatterPlugin) obj;
			plugins.add(p);
			p.preInit();
		}
	}

	private void load(ClassLoader loader) throws Exception
	{
		String path = System.getProperty("loadPlugins");
		if (path != null) {
			String[] pluginsPath = path.split(",");
			if (pluginsPath != null && pluginsPath.length != 0) {
				for (String pluginPath : pluginsPath) {
					Class plugin = Class.forName(pluginPath);
					Object obj = plugin.newInstance();
					if (obj instanceof JavatterPlugin) {
						plugins.add((JavatterPlugin)obj);
					}
				}
			}
		}
	}

	public static List<TweetObjectBuilder> getTweetObjectBuilder() {
		return builders;
	}

	public static List<JavatterProfileBuilder> getProfileBuilder(){
		return profileBuilders;
	}


	protected static void addTweetObjectBuilder(TweetObjectBuilder builder) {
		builders.add(builder);
	}


	protected static void addProfileBuilder(JavatterProfileBuilder builder) {
		profileBuilders.add(builder);
	}
}