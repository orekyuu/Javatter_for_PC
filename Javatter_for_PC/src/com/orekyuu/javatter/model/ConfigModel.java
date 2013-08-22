package com.orekyuu.javatter.model;

import com.orekyuu.javatter.viewobserver.ConfigViewObserver;

public class ConfigModel {


	private ConfigViewObserver view;

	public void setView(ConfigViewObserver view){
		this.view=view;
	}

	public void load() {
		view.update(this);
	}

}
