package com.orekyuu.javatter.viewobserver;

import java.util.Queue;

import com.orekyuu.javatter.controller.ProfileController;

import twitter4j.Status;
import twitter4j.User;

/**
 * プロフィール描画クラスを表すインターフェース
 * @author orekyuu
 *
 */
public interface ProfileViewObserver {

	/**
	 * ユーザー情報を更新
	 * @param user
	 */
	public void userUpdate(User user);

	/**
	 * プロフィールのタイムラインにつぶやきを追加して更新
	 * @param timeline
	 */
	public void timeLineUpdate(Queue<Status> timeline);

	/**
	 * プロフィールのフォロータブに情報を追加して更新
	 * @param follow
	 */
	public void followUserUpdate(Queue<User> follow);

	/**
	 * プロフィールのフォロワータブに情報を追加して更新
	 * @param queue
	 */
	public void followerUserUpdate(Queue<User> queue);

	/**
	 * Controllerを設定
	 * @param controller
	 */
	public void setController(ProfileController controller);

	/**
	 * ウィンドウを作成する
	 */
	public void create();
}
