package com.orekyuu.javatter.view;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * プラグインコンフィグのデフォルト
 * @author orekyuu
 *
 */
public class PluginDefaultConfigView implements IJavatterTab {

	@Override
	public Component getComponent() {
		return new JPanel();
	}

}
