package com.orekyuu.javatter.model;

import twitter4j.Status;

import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

/**
 * リプライのモデル
 * @author orekyuu
 *
 */
public class ReplyModel implements UserStreamLogic{
	private UserStreamViewObserver view;
	private Status status;

	@Override
	public void onStatus(Status arg0) {

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
		this.status=status;
		view.update(this);
	}

	@Override
	public void onRetweetTweet(Status status) {

	}
}
