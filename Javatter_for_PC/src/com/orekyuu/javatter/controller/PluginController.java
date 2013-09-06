package com.orekyuu.javatter.controller;

import java.io.File;

import com.orekyuu.javatter.model.PluginModel;
import com.orekyuu.javatter.plugin.JavatterPluginLoader;
import com.orekyuu.javatter.util.SaveDataManager;
import com.orekyuu.javatter.view.IJavatterTab;
import com.orekyuu.javatter.view.JavatterPluginConfigTab;
import com.orekyuu.javatter.view.MainWindowView;

/**
 * プラグインタブのController
 * @author orekyuu
 *
 */
public class PluginController
{
	private PluginModel model;
	private MainWindowController controller;
	private MainWindowView view;

	/**
	 * モデルを設定
	 * @param pluginModel
	 */
	public void setModel(PluginModel pluginModel)
	{
		this.model = pluginModel;
	}

	/**
	 * メインViewとControllerのセッタ
	 * @param controller
	 * @param view
	 */
	public void setViewAndController(MainWindowController controller, MainWindowView view){
		this.controller=controller;
		this.view=view;
	}

	/**
	 * プラグインをロード
	 */
	public void load() {
		addPluginName("全般","");
		addPluginConfig("全般", new JavatterPluginConfigTab(this));
		JavatterPluginLoader pluginLoader = new JavatterPluginLoader();
		if(SaveDataManager.getInstance().getSaveData("JavatterConfig").getBoolean("isLoad")){
			pluginLoader.loadPlugins(new File("plugins/"));
		}

		pluginLoader.initPlugins(this, controller, view);
		notifyModel();
	}

	/**
	 * プラグインをリロード
	 */
	public void reloadPlugin(){
		JavatterPluginLoader pluginLoader = new JavatterPluginLoader();
		if(SaveDataManager.getInstance().getSaveData("JavatterConfig").getBoolean("isLoad")){
			pluginLoader.loadPlugins(new File("plugins/"));
		}
		pluginLoader.initPlugins(this, controller, view);
		notifyModel();
	}

	/**
	 * プラグインの名前を追加
	 * @param str
	 * @param version
	 */
	public void addPluginName(String str,String version) {
		this.model.addPlugin(str,version);
	}

	/**
	 * プラグインのコンフィグ画面を追加
	 * @param title
	 * @param tab
	 */
	public void addPluginConfig(String title,IJavatterTab tab) {
		model.addPluginConfig(title,tab);
	}

	/**
	 * 更新されたことをモデルへ通知
	 */
	public void notifyModel(){
		model.notifyView();
	}

}