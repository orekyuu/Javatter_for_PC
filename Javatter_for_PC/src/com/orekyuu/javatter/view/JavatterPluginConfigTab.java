package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.orekyuu.javatter.util.SaveData;
import com.orekyuu.javatter.util.SaveDataManager;

/**
 * Javatterプラグインのコンフィグ画面
 * @author orekyuu
 *
 */
public class JavatterPluginConfigTab implements IJavatterTab, ActionListener {

	private JCheckBox check;
	private SaveData save;
	@Override
	public Component getComponent() {
		save=SaveDataManager.getInstance().getSaveData("JavatterConfig");
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JPanel isLoad=new JPanel();
		isLoad.add(new JLabel("Pluginを読み込む(次回起動に適応)"));
		check=new JCheckBox();
		check.setSelected(save.getBoolean("isLoad"));
		check.addActionListener(this);
		isLoad.add(check);

		panel.add(isLoad);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		save.setBoolean("isLoad", check.isSelected());
	}

}
