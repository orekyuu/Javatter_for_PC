package com.orekyuu.javatter.controller;

import java.io.File;

import com.orekyuu.javatter.model.PluginModel;
import com.orekyuu.javatter.plugin.JavatterPluginLoader;
import com.orekyuu.javatter.util.SaveDataManager;
import com.orekyuu.javatter.view.IJavatterTab;
import com.orekyuu.javatter.view.JavatterPluginConfigTab;
import com.orekyuu.javatter.view.MainWindowView;

public class PluginController
{
	private PluginModel model;

	public void setModel(PluginModel pluginModel)
	{
		this.model = pluginModel;
	}

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

	public void addPluginName(String str,String version) {
		this.model.addPlugin(str,version);
	}

	public void addPluginConfig(String title,IJavatterTab tab) {
		model.addPluginConfig(title,tab);
	}

	public void notifyModel(){
		model.notifyView();
	}

}