package com.orekyuu.javatter.model;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.util.JavaBeamUtil;
import com.orekyuu.javatter.util.JavatterConfig;
import com.orekyuu.javatter.util.TwitterUtil;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

/**
 * Javaビームのモデル
 * @author orekyuu
 *
 */
public class JavaBeamModel implements UserStreamLogic {

	private Status status;
	private TwitterUtil util=new TwitterUtil();

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void onStatus(Status status) {

		if(JavatterConfig.getInstance().getJavaBeamRT()&&JavaBeamUtil.isJavaBeam(status.getText())){
			this.status=status;
			Twitter twitter=TwitterManager.getInstance().getTwitter();
			try {
				util.rt(twitter, status);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onReplyTweet(Status status) {

	}

	@Override
	public void onRetweetTweet(Status status) {

	}

	@Override
	public void setView(UserStreamViewObserver view) {

	}

}
