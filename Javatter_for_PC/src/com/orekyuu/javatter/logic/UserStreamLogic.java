package com.orekyuu.javatter.logic;

import twitter4j.Status;

import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

/**
 * ユーザーストリームを表すロジック
 * @author orekyuu
 *
 */
public interface UserStreamLogic{

	/**
	 * Viewに渡すためのStatusを返す
	 * @return
	 */
	public Status getStatus();

	/**
	 * タイムラインのツイートが来た時のイベント
	 * @param status
	 */
	public void onStatus(Status status);

	/**
	 * リプライツイートのイベント
	 * @param status
	 */
	public void onReplyTweet(Status status);

	/**
	 * リツイートされた時のイベント
	 * @param status
	 */
	public void onRetweetTweet(Status status);

	/**
	 * Viewを設定する
	 * @param view
	 */
	public void setView(UserStreamViewObserver view);

}
