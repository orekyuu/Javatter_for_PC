package com.orekyuu.javatter.controller;

import com.orekyuu.javatter.model.ConfigModel;
import com.orekyuu.javatter.util.JavatterConfig;

public class ConfigController {

	private ConfigModel model;

	public ConfigController(){
	}

	public void setModel(ConfigModel model){
		this.model=model;
	}

	public void load(){
		model.load();
	}

	public void javaBeamUpdate(boolean selected) {
		JavatterConfig.getInstance().setJavaBeamRT(selected);
	}

	public void thanks(boolean selected) {
		JavatterConfig.getInstance().setThanks(selected);
	}
}
