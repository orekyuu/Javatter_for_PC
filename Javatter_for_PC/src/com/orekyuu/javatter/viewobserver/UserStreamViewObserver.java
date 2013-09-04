package com.orekyuu.javatter.viewobserver;

import com.orekyuu.javatter.logic.UserStreamLogic;

/**
 * ユザーストリームを描画するクラスを表すインターフェース
 * @author orekyuu
 *
 */
public interface UserStreamViewObserver {

	/**
	 * ストリームの内容を更新
	 * @param model
	 */
	public void update(UserStreamLogic model);

}
