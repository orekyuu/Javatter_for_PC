package com.orekyuu.javatter.plugin;

/**
 * ユーザーがつぶやいた時のイベントリスナ
 * @author orekyuu
 *
 */
public interface ITweetListener {

	/**
	 * ツイートした時のイベント
	 * @param tweetText ツイートする文字列
	 * @return
	 */
	public String onTweet(String tweetText);
}
