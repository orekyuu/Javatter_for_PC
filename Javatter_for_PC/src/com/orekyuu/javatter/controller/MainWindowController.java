package com.orekyuu.javatter.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import com.orekyuu.javatter.account.AccountManager;
import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.model.ConfigModel;
import com.orekyuu.javatter.model.JavaBeamModel;
import com.orekyuu.javatter.model.LoginModel;
import com.orekyuu.javatter.model.PluginModel;
import com.orekyuu.javatter.model.PopupModel;
import com.orekyuu.javatter.model.ReplyModel;
import com.orekyuu.javatter.model.TimeLineModel;
import com.orekyuu.javatter.plugin.ITweetListener;
import com.orekyuu.javatter.plugin.JavatterPluginLoader;
import com.orekyuu.javatter.util.JavatterUserStream;
import com.orekyuu.javatter.util.TwitterUtil;
import com.orekyuu.javatter.view.ConfigView;
import com.orekyuu.javatter.view.LoginView;
import com.orekyuu.javatter.view.MainWindowView;
import com.orekyuu.javatter.view.PluginView;
import com.orekyuu.javatter.view.PopupView;
import com.orekyuu.javatter.view.ReplyView;
import com.orekyuu.javatter.view.TimeLineView;
import com.orekyuu.javatter.viewobserver.PopupViewObserver;

/**
 * メインウィンドウのControllerクラス
 * @author orekyuu
 *
 */
public class MainWindowController
{
	private Twitter twitter;
	private MainWindowView view;
	private JavatterUserStream userStream;
	private List<ITweetListener> tweetListener=new ArrayList<ITweetListener>();

	/**
	 *
	 * @param view Viewを設定
	 */
	public MainWindowController(MainWindowView view)
	{
		this.twitter = TwitterManager.getInstance().getTwitter();
		this.view = view;
	}

	/**
	 * ツイートする
	 * @param tweet つぶやきたいメッセージ
	 * @param util TwitterUtil
	 * @throws TwitterException
	 */
	public void onTweet(String tweet, TwitterUtil util) throws TwitterException {
		String t=tweet;
		for(ITweetListener listener:tweetListener){
			t=listener.onTweet(t);
		}
		util.tweet(this.twitter, t);
	}

	/**
	 * リツイートする
	 * @param status リツイートする対象
	 * @param util TwitterUtil
	 * @throws TwitterException
	 */
	public void onRetweet(Status status, TwitterUtil util) throws TwitterException {
		util.rt(this.twitter, status);
	}

	/**
	 * リプライ先を設定
	 * @param status リプライ先
	 * @param util TwitterUtil
	 */
	public void onReply(Status status, TwitterUtil util) {
		util.setReplyID(status);
	}

	/**
	 * お気に入りに登録
	 * @param status
	 * @param util
	 * @throws TwitterException
	 */
	public void onFav(Status status, TwitterUtil util) throws TwitterException {
		util.fav(this.twitter, status);
	}

	/**
	 * Javaビームを放つ
	 * @param text 連結させる文字列
	 * @throws TwitterException
	 */
	public void shotJavaBeam(String text) throws TwitterException {
		TwitterUtil util = new TwitterUtil();
		onTweet(text + "Javaビームﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞwwwwwwwwww", util);
	}

	/**
	 * 起動に必要な動作
	 */
	public void start()
	{
		try {
			loggin();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}

		this.view.clearTab();

		this.userStream = new JavatterUserStream();

		UserStreamLogic timeline = new TimeLineModel();
		final UserStreamController userStreamController = new UserStreamController();
		TimeLineView tlView = new TimeLineView(this.view,JavatterPluginLoader.getTweetObjectBuilder());
		userStreamController.setModel(timeline);
		timeline.setView(tlView);
		this.view.addUserStreamTab("TimeLine", tlView);

		UserStreamLogic replyModel = new ReplyModel();
		final UserStreamController replyController = new UserStreamController();
		ReplyView rpView = new ReplyView(this.view,JavatterPluginLoader.getTweetObjectBuilder());
		replyController.setModel(replyModel);
		replyModel.setView(rpView);
		this.view.addUserStreamTab("Reply", rpView);

		ConfigModel configModel = new ConfigModel();
		ConfigController cofigController = new ConfigController();
		ConfigView configView = new ConfigView();
		cofigController.setModel(configModel);
		configModel.setView(configView);
		configView.setConfigController(cofigController);
		this.view.addMenuTab("Config", configView);
		cofigController.load();

		final UserStreamController javaBeamRT = new UserStreamController();
		UserStreamLogic javaRT = new JavaBeamModel();
		javaBeamRT.setModel(javaRT);

		final PopupController popupController = new PopupController();
		PopupModel popupModel = new PopupModel();
		PopupViewObserver popupView = new PopupView();
		popupController.setModel(popupModel);
		popupModel.setView(popupView);

		PluginModel pluginModel = new PluginModel();
		PluginController pluginController = new PluginController();
		PluginView pluginView = new PluginView();
		pluginController.setModel(pluginModel);
		pluginModel.setView(pluginView);
		this.view.addMenuTab("プラグイン管理", pluginView);
		pluginController.setViewAndController(this, this.view);
		pluginController.load();

		Thread th = new Thread()
		{
			public void run() {
				MainWindowController.this.userStream.addUserStreamController(popupController);
				MainWindowController.this.userStream.addUserStreamController(replyController);
				MainWindowController.this.userStream.addUserStreamController(userStreamController);
				MainWindowController.this.userStream.addUserStreamController(javaBeamRT);
				try {
					ResponseList<Status> status = TwitterManager.getInstance().getTwitter().getHomeTimeline();
					ResponseList<Status> reply = TwitterManager.getInstance().getTwitter().getMentionsTimeline();
					for (int i = status.size() - 1; i >= 0; i--) {
						userStreamController.onStatus((Status)status.get(i));
					}
					for (int i = reply.size() - 1; i >= 0; i--){
						replyController.onStatus((Status)reply.get(i));
					}
				}
				catch (TwitterException e) {
					e.printStackTrace();
				}
				MainWindowController.this.userStream.start();
			}
		};
		th.start();
	}

	public JavatterUserStream getUserStream() {
		return this.userStream;
	}

	public void addTweetListener(ITweetListener listener){
		tweetListener.add(listener);
	}

	public void loggin() throws MalformedURLException, TwitterException {
		if (AccountManager.getInstance().isLogined()) {
			AccessToken token = AccountManager.getInstance().getAccessToken();
			TwitterManager.getInstance().getTwitter().setOAuthAccessToken(token);
		} else {
			LoginView view = new LoginView();
			LoginController controller = new LoginController();
			LoginModel model = new LoginModel();

			model.setView(view);
			controller.setModel(model);
			view.setController(controller);

			controller.login();
		}

		this.view.setTitle(this.twitter.getScreenName());
	}
}
