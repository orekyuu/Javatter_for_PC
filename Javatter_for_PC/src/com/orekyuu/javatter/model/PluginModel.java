package com.orekyuu.javatter.model;

import com.orekyuu.javatter.view.IJavatterTab;
import com.orekyuu.javatter.viewobserver.PluginViewObserver;
import java.util.ArrayList;
import java.util.List;

public class PluginModel
{
	private PluginViewObserver view;
	private List<String> list = new ArrayList<String>();
	private List<String> versionList = new ArrayList<String>();

	public void setView(PluginViewObserver pluginView) { this.view = pluginView; }

	public void addPlugin(String name,String version)
	{
		this.list.add(name);
		this.versionList.add(version);
		this.view.update((String[])this.list.toArray(new String[0]),(String[])this.versionList.toArray(new String[0]));
	}

	public void addPluginConfig(String title, IJavatterTab tab) {
		view.addPluginConfigTab(title, tab);
	}
}