package com.orekyuu.javatter.logic;

import twitter4j.User;

/**
 * TweetObjectの変更イベントの時の処理を表すインターフェース
 * @author orekyuu
 *
 */
public interface TweetObjectLogic {

	/**
	 * 使用者のアカウントのツイートが他人によってお気に入りされた時のイベント
	 * @param user お気に入り登録したユーザー
	 */
	public void onFavorite(User user);

	/**
	 * 使用者によってお気に入りした時のイベント
	 */
	public void onFavoriteByMe();

	/**
	 * 使用者のアカウントのツイートが他人によってお気に入り削除された時のイベント
	 * @param user お気に入り登録を解除したユーザー
	 */
	public void unFavorite(User user);

	/**
	 * 使用者によってお気に入り削除された時のイベント
	 */
	public void unFavoriteByMe();

	/**
	 * 使用者のアカウントのツイートが他人によってお気に入りされた時のイベント
	 * @param user リツイートしたユーザー
	 */
	public void onRT(User RTuser);

	/**
	 * 使用者によってRTされた時のイベント
	 */
	public void onRTByMe();

	/**
	 * 自身のStatusが削除された時のイベント
	 */
	public void deleteTweet();

	/**
	 * 自身のStatusのUserのプロフィールが更新された時のイベント
	 * @param user プロフィール更新したユーザー
	 */
	public void updateProfile(User user);
}
