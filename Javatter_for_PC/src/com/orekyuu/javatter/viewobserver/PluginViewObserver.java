package com.orekyuu.javatter.viewobserver;

import com.orekyuu.javatter.view.IJavatterTab;

public abstract interface PluginViewObserver
{
	public abstract void addPluginConfigTab(String title,IJavatterTab tab);

	public abstract void update(String[] array, String[] array2);
}