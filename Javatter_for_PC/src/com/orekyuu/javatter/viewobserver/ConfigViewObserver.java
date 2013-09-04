package com.orekyuu.javatter.viewobserver;

import com.orekyuu.javatter.controller.ConfigController;
import com.orekyuu.javatter.model.ConfigModel;

/**
 * コンフィグ描画クラスを表すインターフェース
 * @author orekyuu
 *
 */
public interface ConfigViewObserver {

	/**
	 * Controllerを設定
	 * @param controller
	 */
	public void setConfigController(ConfigController controller);

	/**
	 * 表示内容を更新
	 * @param model
	 */
	public void update(ConfigModel model);
}
