package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.orekyuu.javatter.controller.PluginController;
import com.orekyuu.javatter.util.SaveData;
import com.orekyuu.javatter.util.SaveDataManager;

/**
 * Javatterプラグインのコンフィグ画面
 * @author orekyuu
 *
 */
public class JavatterPluginConfigTab implements IJavatterTab, ActionListener {

	private JCheckBox check;
	private JButton button;
	private SaveData save;
	private PluginController controller;
	public JavatterPluginConfigTab(PluginController pluginController) {
		controller=pluginController;
	}

	@Override
	public Component getComponent() {
		save=SaveDataManager.getInstance().getSaveData("JavatterConfig");
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		check=new JCheckBox("Pluginを読み込む(次回起動に適用)");
		check.setSelected(save.getBoolean("isLoad"));
		check.addActionListener(this);
		panel.add(check);

		button=new JButton("プラグインを再読み込み");
		button.addActionListener(this);
		panel.add(button);
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==check)
			save.setBoolean("isLoad", check.isSelected());
		else
			controller.reloadPlugin();
	}

}
