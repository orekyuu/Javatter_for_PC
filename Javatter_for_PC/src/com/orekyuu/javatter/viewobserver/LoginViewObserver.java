package com.orekyuu.javatter.viewobserver;

import java.net.URL;

import com.orekyuu.javatter.controller.LoginController;
import com.orekyuu.javatter.logic.TwitterLoginLogic;

public interface LoginViewObserver {

	/**
	 * 指定のURLを開く
	 * @param url
	 */
	public void openView(URL url);

	/**
	 * イベントを通知するコントローラーを設定
	 * @param controller
	 */
	public void setController(LoginController controller);

	/**
	 * 画面を更新する
	 * @param logic
	 */
	public void update(TwitterLoginLogic logic);

}
