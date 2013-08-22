package com.orekyuu.javatter.controller;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.UserStreamAdapter;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.logic.UserStreamLogic;

public class UserStreamController extends UserStreamAdapter{

	private UserStreamLogic model;

	public void setModel(UserStreamLogic model){
		this.model=model;
	}

	@Override
	public void onStatus(Status status){
		model.onStatus(status);
		try {
			if(isReply(status)){
				model.onReplyTweet(status);
			}
			if(status.isRetweet()){
				model.onRetweetTweet(status);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private boolean isReply(Status status) throws IllegalStateException, TwitterException{
		String user=TwitterManager.getInstance().getTwitter().getScreenName();
		return user.equals(status.getInReplyToScreenName());
	}
}
