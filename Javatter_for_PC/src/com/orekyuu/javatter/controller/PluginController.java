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

	/**
	 * モデルを設定
	 * @param pluginModel
	 */
	public void setModel(PluginModel pluginModel)
	{
		this.model = pluginModel;
	}

	/**
	 * プラグインをロード
	 * @param controller
	 * @param view
	 */
	public void load(MainWindowController controller, MainWindowView view) {
		JavatterPluginLoader pluginLoader = new JavatterPluginLoader();
		if(SaveDataManager.getInstance().getSaveData("JavatterConfig").getBoolean("isLoad")){
			pluginLoader.loadPlugins(new File("plugins/"));
		}
		pluginLoader.initPlugins(this, controller, view);
		addPluginName("全般","");
		addPluginConfig("全般", new JavatterPluginConfigTab());
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