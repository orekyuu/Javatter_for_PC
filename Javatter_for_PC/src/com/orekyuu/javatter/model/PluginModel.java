package com.orekyuu.javatter.model;

import com.orekyuu.javatter.view.IJavatterTab;
import com.orekyuu.javatter.viewobserver.PluginViewObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * プラグインのモデル
 * @author orekyuu
 *
 */
public class PluginModel
{
	private PluginViewObserver view;
	private List<String> list = new ArrayList<String>();
	private List<String> versionList = new ArrayList<String>();

	/**
	 * Viewを設定
	 * @param pluginView
	 */
	public void setView(PluginViewObserver pluginView) { this.view = pluginView; }

	/**
	 * プラグインを追加
	 * @param name
	 * @param version
	 */
	public void addPlugin(String name,String version)
	{
		this.list.add(name);
		this.versionList.add(version);
	}

	/**
	 * Viewに更新を通知
	 */
	public void notifyView(){
		this.view.update((String[])this.list.toArray(new String[0]),(String[])this.versionList.toArray(new String[0]));
	}

	/**
	 * プラグインのコンフィグを追加
	 * @param title タイトル
	 * @param tab タブ
	 */
	public void addPluginConfig(String title, IJavatterTab tab) {
		view.addPluginConfigTab(title, tab);
	}
}