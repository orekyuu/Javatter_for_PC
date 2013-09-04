package com.orekyuu.javatter.viewobserver;

import com.orekyuu.javatter.controller.MainWindowController;

/**
 * ツイート画面を表すインターフェース
 * @author orekyuu
 *
 */
public interface TweetViewObserver {

	/**
	 * Controllerを設定
	 * @param controller
	 */
	public void setTweetController(MainWindowController controller);
}
