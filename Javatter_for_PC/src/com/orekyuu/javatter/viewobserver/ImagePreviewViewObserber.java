package com.orekyuu.javatter.viewobserver;

import java.io.File;

/**
 * 画像プレビューを表すインターフェース
 * @author orekyuu
 *
 */
public interface ImagePreviewViewObserber {
	/**
	 * プレビューを変更
	 * @param paramFile 表示する画像のパス
	 */
	public void change(File paramFile);

	/**
	 * プレビューをクリア
	 */
	public void clear();
}
