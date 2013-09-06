package com.orekyuu.javatter.plugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
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

/**
 * プラグインを読み込むクラス
 * @author orekyuu
 *
 */
public class JavatterPluginLoader
{
	private List<JavatterPlugin> plugins = new ArrayList<JavatterPlugin>();
	private static List<JavatterProfileBuilder> profileBuilders=new ArrayList<JavatterProfileBuilder>();
	private static List<TweetObjectBuilder> builders=new ArrayList<TweetObjectBuilder>();
	private static List<String> loadedPluginFiles=new ArrayList<String>();
	private static List<String> mainClassList=new ArrayList<String>();

	/**
	 * 指定されたディレクトリのプラグインを読み込む
	 * @param file ディレクトリのパス
	 */
	public void loadPlugins(File file)
	{
		if (!file.exists()) {
			file.mkdir();
		}


		try{
			URLClassLoader loader=(URLClassLoader) getClass().getClassLoader();

			for (File f : file.listFiles()){
				if (f.getName().endsWith(".jar")&&!isLoadedPluginFile(f.getName())) {
					load(f,loader);
				}
			}

			for(String main:mainClassList){
				Class<?> plugin=loader.loadClass(main);
				Object obj=plugin.newInstance();
				if(obj instanceof JavatterPlugin){
					JavatterPlugin p=(JavatterPlugin) obj;
					plugins.add(p);
					loadedPluginFiles.add(file.getName());
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

	private boolean isLoadedPluginFile(String name){
		for(String file:loadedPluginFiles){
			if(file.equals(name))return true;
		}
		return false;
	}


	/**
	 * プラグインを初期化する
	 * @param pluginTab
	 * @param controller
	 * @param view
	 */
	public void initPlugins(PluginController pluginTab, MainWindowController controller, MainWindowView view)
	{
		for(JavatterPlugin plugin : this.plugins){
			if(!plugin.isLoaded()){
				plugin.setPluginLoader(this);
				plugin.preInit();
			}
		}
		for (JavatterPlugin plugin : this.plugins) {
			if(!plugin.isLoaded()){
				plugin.setMainViewAndController(view, controller);
				plugin.initPlugin();
				pluginTab.addPluginName(plugin.getPluginName(),plugin.getVersion());
				pluginTab.addPluginConfig(plugin.getPluginName(), plugin.getPluginConfigView());
			}
		}
		for(JavatterPlugin plugin : this.plugins){
			if(!plugin.isLoaded()){
				plugin.postInit();
				plugin.load();
			}
		}
	}

	/**
	 * プラグインをロード
	 * @param file プラグインのファイル
	 * @param loader クラスローダー
	 * @throws Exception
	 */
	private void load(File file,ClassLoader loader)throws Exception
	{
		addLibrary(file, loader);

		JarFile jar=new JarFile(file);
		Manifest manifest=jar.getManifest();
		Attributes attributes = manifest.getMainAttributes();
		String mainClass = attributes.getValue("Plugin-Main");
		mainClassList.add(mainClass);
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

	/**
	 * 指定されたファイルをクラスローダーの検索パスに追加します
	 * @param file 追加したいファイル
	 * @param loader 使用するクラスローダー
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws MalformedURLException
	 */
	public static void addLibrary(File file,ClassLoader loader) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException{
		Method m=URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
		m.setAccessible(true);
		m.invoke(loader, new Object[]{file.toURI().toURL()});
	}

	/**
	 * TweetObjectBuilderのリストを返す
	 * @return
	 */
	public static List<TweetObjectBuilder> getTweetObjectBuilder() {
		return builders;
	}

	/**
	 * ProfileBuilderのリストを返す
	 * @return
	 */
	public static List<JavatterProfileBuilder> getProfileBuilder(){
		return profileBuilders;
	}

	/**
	 * TweetObjectBuilderを追加
	 * @param builder
	 */
	protected static void addTweetObjectBuilder(TweetObjectBuilder builder) {
		builders.add(builder);
	}

	/**
	 * ProfileBuilderを追加
	 * @param builder
	 */
	protected static void addProfileBuilder(JavatterProfileBuilder builder) {
		profileBuilders.add(builder);
	}

	/**
	 * プラグインが読み込まれているか
	 * @param name プラグインの名前
	 * @return
	 */
	public boolean isPluginLoaded(String name) {
		for(JavatterPlugin plugin:plugins){
			System.out.println(plugin.getPluginName());
			if(plugin.getPluginName().equals(name)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列からプラグインを取得します
	 * @param name 取得したいプラグインの名前
	 * @return
	 */
	protected JavatterPlugin getPlugin(String name){
		for(JavatterPlugin plugin:plugins){
			if(plugin.getPluginName().equals(name)){
				return plugin;
			}
		}
		return null;
	}
}