package com.orekyuu.javatter.logic;

import twitter4j.Status;

import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;


public interface UserStreamLogic{

	public Status getStatus();

	public void onStatus(Status status);

	public void onReplyTweet(Status status);

	public void onRetweetTweet(Status status);

	public void setView(UserStreamViewObserver view);

}
