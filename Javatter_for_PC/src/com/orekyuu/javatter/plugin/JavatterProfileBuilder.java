package com.orekyuu.javatter.plugin;

import javax.swing.JPanel;

import twitter4j.User;

/**
 * プロフィールに変更を加えるクラスを表すインターフェース
 * @author orekyuu
 *
 */
public interface JavatterProfileBuilder {

	/**
	 * プロフィールパネルが作成された時
	 * @param panel
	 */
	public void createdProfilePanel(JPanel panel,User user);

	/**
	 * フォロー・フォロワーを表すオブジェクトが作成された時
	 * @param panel
	 */
	public void createdFollowObjectPanel(JPanel panel,User user);
}
