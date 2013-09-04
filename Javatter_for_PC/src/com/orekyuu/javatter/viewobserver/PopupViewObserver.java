package com.orekyuu.javatter.viewobserver;

import twitter4j.Status;
import twitter4j.User;

/**
 * ポップアップ描画クラスを表すインターフェース
 * @author orekyuu
 *
 */
public interface PopupViewObserver {

	/**
	 * お気に入りされたことを描画
	 * @param user お気に入りしたユーザー
	 * @param status 対象のツイート
	 */
	public void onFav(User user,Status status);

	/**
	 * リツイートされたことを描画
	 * @param rtUser リツイートしたユーザー
	 * @param rt 対象のツイート
	 */
	public void onRT(User rtUser,Status rt);

	/**
	 * お気に入り削除されたことを描画
	 * @param user 削除したユーザー
	 * @param status 対象のツイート
	 */
	public void onUnFav(User user,Status status);

	/**
	 * フォローされたことを描画
	 * @param user 自分をフォローしたユーザー
	 */
	public void onFollow(User user);

	/**
	 * ブロックされたことを描画
	 * @param user 自分をブロックしたユーザー
	 */
	public void onBlock(User user);
}
