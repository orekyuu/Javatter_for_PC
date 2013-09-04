package com.orekyuu.javatter.plugin;

import javax.swing.JPanel;

import twitter4j.Status;

/**
 * TweetObjectに変更を加えるクラスを表すインターフェース
 * @author orekyuu
 *
 */
public interface TweetObjectBuilder {

	/**
	 * ボタン用のPanelが作られた時に呼ばれる
	 * @param panel ボタンのPanel
	 * @param status ツイート情報
	 */
	public void createdButtonPanel(JPanel panel,Status status);

	/**
	 * ユーザーネーム・発言内容・ボタンをまとめたPanelが作られた時に呼ばれる
	 * @param panel テキスト用パネル
	 * @param status ツイート情報
	 */
	public void createdTextAreaPanel(JPanel panel,Status status);

	/**
	 * 画像用パネルが作られた時に呼ばれる
	 * @param panel
	 * @param status
	 */
	public void createdImagePanel(JPanel panel,Status status);

	/**
	 * テキスト用Panel・画像用Panel・ボタン用PanelをまとめたPanelが作られた時に呼ばれる
	 * @param panel
	 * @param status
	 */
	public void createdObjectPanel(JPanel panel,Status status);

}
