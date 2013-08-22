package com.orekyuu.javatter.model;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.util.JavatterConfig;
import com.orekyuu.javatter.util.TwitterUtil;
import com.orekyuu.javatter.viewobserver.PopupViewObserver;

public class PopupModel {

	private PopupViewObserver view;
	private TwitterUtil util=new TwitterUtil();

	public void setView(PopupViewObserver view){
		this.view=view;
	}

	public void onFav(User user,Status status){
		view.onFav(user,status);
		if(JavatterConfig.getInstance().getThanks()){
			try {
				util.tweet(TwitterManager.getInstance().getTwitter(), "@"+user.getScreenName()+" ふぁぼありがとうございます。");
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

	public void onRT(User rtUser,Status rt){
		view.onRT(rtUser, rt);
	}

	public void onUnFav(User user,Status status){
		view.onUnFav(user,status);
	}

	public void onFollow(User user){
		view.onFollow(user);
	}

	public void onBlock(User user){
		view.onBlock(user);
	}
}
