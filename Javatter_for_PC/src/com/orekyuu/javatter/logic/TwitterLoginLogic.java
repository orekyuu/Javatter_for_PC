package com.orekyuu.javatter.logic;

import java.net.URL;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.orekyuu.javatter.viewobserver.LoginViewObserver;

/**
 * ログインするための動作を表すインターフェース
 * @author orekyuu
 *
 */
public interface TwitterLoginLogic {

	/**
	 * AccessTokenを登録
	 * @param token
	 */
	public void registerAccessToken(AccessToken token);

	/**
	 * Modelに対するViewを設定
	 * @param view
	 */
	public void setView(LoginViewObserver view);

	/**
	 * Viewを指定のURLで開く
	 * @param url 認証させるページのURL
	 */
	public void openView(URL url);

	/**
	 * 認証させる
	 * @param token ログイン用のリクエストトークン
	 */
	public void authentication(RequestToken token);

}
