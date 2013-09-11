package com.orekyuu.javatter.model;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.util.JavatterConfig;
import com.orekyuu.javatter.util.TwitterUtil;
import com.orekyuu.javatter.viewobserver.PopupViewObserver;

/**
 * ポップアップのモデル
 * @author orekyuu
 *
 */
public class PopupModel {

	private PopupViewObserver view;
	private TwitterUtil util=new TwitterUtil();
	private JavatterConfig conf=JavatterConfig.getInstance();

	/**
	 * Viewを設定
	 * @param view
	 */
	public void setView(PopupViewObserver view){
		this.view=view;
	}

	/**
	 * お気に入りイベント
	 * @param user
	 * @param status
	 */
	public void onFav(User user,Status status){
		if(conf.getUseTaskbar())
			view.onFav(user,status);
		if(JavatterConfig.getInstance().getThanks()){
			try {
				util.tweet(TwitterManager.getInstance().getTwitter(), "@"+user.getScreenName()+" ふぁぼありがとうございます。");
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * リツイートイベント
	 * @param rtUser RTしたユーザ
	 * @param rt RTされたStatus
	 */
	public void onRT(User rtUser,Status rt){
		if(conf.getUseTaskbar())
			view.onRT(rtUser, rt);
	}

	/**
	 * あんふぁぼイベント
	 * @param user イベントを起こしたユーザー
	 * @param status あんふぁぼされたつぶやき
	 */
	public void onUnFav(User user,Status status){
		if(conf.getUseTaskbar())
			view.onUnFav(user,status);
	}

	/**
	 * フォローイベント
	 * @param user
	 */
	public void onFollow(User user){
		if(conf.getUseTaskbar())
			view.onFollow(user);
	}

	/**
	 * ブロックイベント
	 * @param user
	 */
	public void onBlock(User user){
		if(conf.getUseTaskbar())
			view.onBlock(user);
	}
}
