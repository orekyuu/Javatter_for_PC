package com.orekyuu.javatter.model;

import twitter4j.Status;

import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

/**
 * タイムラインのモデル
 * @author orekyuu
 *
 */
public class TimeLineModel implements UserStreamLogic {

	private Status status;
	private UserStreamViewObserver view;

	@Override
	public void onStatus(Status arg0) {
		status=arg0;
		view.update(this);
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setView(UserStreamViewObserver view) {
		this.view=view;
	}

	@Override
	public void onReplyTweet(Status status) {

	}

	@Override
	public void onRetweetTweet(Status status) {

	}

}
