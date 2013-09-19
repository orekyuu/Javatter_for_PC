package com.orekyuu.javatter.controller;

import com.orekyuu.javatter.model.ConfigModel;
import com.orekyuu.javatter.util.JavatterConfig;

/**
 * コンフィグのControllerクラス
 * @author orekyuu
 *
 */
public class ConfigController {

	private ConfigModel model;

	public ConfigController(){
	}

	/**
	 * ConfigModelを設定
	 * @param model
	 */
	public void setModel(ConfigModel model){
		this.model=model;
	}

	/**
	 * コンフィグをロードします
	 */
	public void load(){
		model.load();
	}

	/**
	 * Javaビームの設定を更新
	 * @param selected
	 */
	public void javaBeamUpdate(boolean selected) {
		JavatterConfig.getInstance().setJavaBeamRT(selected);
	}

	/**
	 * お礼の設定を更新
	 * @param selected
	 */
	public void thanks(boolean selected) {
		JavatterConfig.getInstance().setThanks(selected);
	}

	/**
	 * タスクバーの設定を更新
	 * @param selected
	 */
	public void taskbarUpdate(boolean selected){
		JavatterConfig.getInstance().setUseTaskbar(selected);
	}

	/**
	 * ローカルキャッシュの設定を更新
	 * @param selected
	 */
	public void cacheUpdate(boolean selected){
		JavatterConfig.getInstance().setUseLocalCache(selected);
	}
}
