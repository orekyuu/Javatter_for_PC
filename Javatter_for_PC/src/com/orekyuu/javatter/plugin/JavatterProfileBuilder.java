package com.orekyuu.javatter.plugin;

import javax.swing.JPanel;

public interface JavatterProfileBuilder {

	/**
	 * プロフィールパネルが作成された時
	 * @param panel
	 */
	public void createdProfilePanel(JPanel panel);

	/**
	 * フォロー・フォロワーを表すオブジェクトが作成された時
	 * @param panel
	 */
	public void createdFollowObjectPanel(JPanel panel);
}
