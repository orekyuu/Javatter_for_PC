package com.orekyuu.javatter.model;

import java.net.MalformedURLException;
import java.net.URL;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.logic.TwitterLoginLogic;
import com.orekyuu.javatter.viewobserver.LoginViewObserver;

/**
 * ログインのモデル
 * @author orekyuu
 *
 */
public class LoginModel implements TwitterLoginLogic {

	private LoginViewObserver view;

	@Override
	public void registerAccessToken(AccessToken token) {
		//Twitterインスタンスにアクセストークンを設定
		TwitterManager.getInstance().getTwitter().setOAuthAccessToken(token);
		//Viewを更新
		view.update(this);
	}

	@Override
	public void setView(LoginViewObserver view) {
		this.view=view;
	}

	@Override
	public void openView(URL url) {
		view.openView(url);
	}

	@Override
	public void authentication(RequestToken token) {
		try {
			openView(new URL(token.getAuthenticationURL()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
