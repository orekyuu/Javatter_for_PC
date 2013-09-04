package com.orekyuu.javatter.viewobserver;

import com.orekyuu.javatter.view.IJavatterTab;

/**
 * プラグイン管理タブを表すインターフェース
 * @author orekyuu
 *
 */
public interface PluginViewObserver
{
	/**
	 * 新しいプラグインのコンフィグを追加する
	 * @param title タイトル
	 * @param tab タブ
	 */
	public void addPluginConfigTab(String title,IJavatterTab tab);

	/**
	 * 描画内容を更新
	 * @param array
	 * @param array2
	 */
	public void update(String[] array, String[] array2);
}