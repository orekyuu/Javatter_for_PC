package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.orekyuu.javatter.controller.ConfigController;
import com.orekyuu.javatter.model.ConfigModel;
import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.util.JavatterConfig;
import com.orekyuu.javatter.viewobserver.ConfigViewObserver;

/**
 * コンフィグの描画クラス
 * @author orekyuu
 *
 */
public class ConfigView implements IJavatterTab,ConfigViewObserver,ActionListener{

	private ConfigController controller;
	private JCheckBox javaBeamRT;
	private JCheckBox thanks;
	private JCheckBox taskbar;
	private JCheckBox cache;

	@Override
	public Component getComponent() {
		JPanel panel = new JPanel();
		panel.setAlignmentX(0);
		panel.setAlignmentY(0);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBackground(BackGroundColor.color);

		javaBeamRT=new JCheckBox("JavaビームオートRT");
		javaBeamRT.addActionListener(this);
		panel.add(javaBeamRT);

		taskbar=new JCheckBox("タスクバーの通知を使用する");
		taskbar.addActionListener(this);
		panel.add(taskbar);

		thanks=new JCheckBox("ふぁぼありがとうございます");
		thanks.addActionListener(this);
		panel.add(thanks);

		cache=new JCheckBox("ローカルキャッシュを使用する");
		cache.addActionListener(this);
		panel.add(cache);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(javaBeamRT)){
			controller.javaBeamUpdate(javaBeamRT.isSelected());
		}
		if(e.getSource().equals(thanks)){
			controller.thanks(thanks.isSelected());
		}
		if(e.getSource().equals(taskbar)){
			controller.taskbarUpdate(taskbar.isSelected());
		}
		if(e.getSource().equals(cache)){
			controller.cacheUpdate(cache.isSelected());
		}
	}

	@Override
	public void setConfigController(ConfigController controller) {
		this.controller=controller;
	}

	@Override
	public void update(ConfigModel model) {
		JavatterConfig conf=JavatterConfig.getInstance();
		javaBeamRT.setSelected(conf.getJavaBeamRT());
		javaBeamRT.updateUI();
		thanks.setSelected(conf.getThanks());
		thanks.updateUI();
		taskbar.setSelected(conf.getUseTaskbar());
		taskbar.updateUI();
		cache.setSelected(conf.getUseLocalCache());
		cache.updateUI();
	}

}
