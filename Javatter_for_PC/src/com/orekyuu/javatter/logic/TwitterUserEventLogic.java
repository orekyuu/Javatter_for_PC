package com.orekyuu.javatter.logic;

/**
 * ユーザーイベントを表すインターフェース
 * @author orekyuu
 */
public interface TwitterUserEventLogic {

	/**
	 * Rtイベントを起こすソース
	 * @param obj
	 */
	public void setRtButton(Object obj);

	/**
	 * Favイベントを起こすソース
	 * @param obj
	 */
	public void setFavButton(Object obj);

	/**
	 * Replyイベントを起こすソース
	 * @param obj
	 */
	public void setReplyButton(Object obj);
}
