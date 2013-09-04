package com.orekyuu.javatter.controller;

import com.orekyuu.javatter.account.AccountManager;
import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.logic.TwitterLoginLogic;
import java.net.MalformedURLException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * ログイン画面のControllerクラス
 * @author orekyuu
 *
 */
public class LoginController
{
	private TwitterLoginLogic model;
	private RequestToken token;
	private AccountManager manager = AccountManager.getInstance();

	/**
	 * モデルを設定
	 * @param model
	 */
	public void setModel(TwitterLoginLogic model)
	{
		this.model = model;
	}

	/**
	 * ログインを試みます
	 * @throws TwitterException
	 * @throws MalformedURLException
	 */
	public void login()throws TwitterException, MalformedURLException
	{
		Twitter twitter = TwitterManager.getInstance().getTwitter();
		this.token = twitter.getOAuthRequestToken();

		if (!isLoggin())
			this.model.authentication(this.token);
	}

	/**
	 * 認証済みかを返します
	 * @return
	 */
	public boolean isLoggin()
	{
		return this.manager.isLogined();
	}

	/**
	 * PinCodeを使って認証します
	 * @param pinCode
	 * @throws TwitterException
	 */
	public void onAuthentication(String pinCode) throws TwitterException
	{
		Twitter twitter = TwitterManager.getInstance().getTwitter();
		AccessToken t = twitter.getOAuthAccessToken(this.token, pinCode);
		this.manager.setAccessToken(t);
		this.model.registerAccessToken(t);
	}
}