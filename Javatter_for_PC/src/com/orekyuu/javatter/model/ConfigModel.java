package com.orekyuu.javatter.model;

import com.orekyuu.javatter.viewobserver.ConfigViewObserver;

/**
 * コンフィグのモデル
 * @author orekyuu
 *
 */
public class ConfigModel {


	private ConfigViewObserver view;

	/**
	 * Viewを設定
	 * @param view
	 */
	public void setView(ConfigViewObserver view){
		this.view=view;
	}

	/**
	 * コンフィグをロード
	 */
	public void load() {
		view.update(this);
	}

}
