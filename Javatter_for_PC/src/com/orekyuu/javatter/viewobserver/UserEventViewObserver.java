package com.orekyuu.javatter.viewobserver;

import twitter4j.Status;

/**
 * ユーザーイベントを受け取って描画することを表すインターフェース
 * @author orekyuu
 *
 */
public interface UserEventViewObserver {

	public void onUserEvent(String type,Status status);
}
