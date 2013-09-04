package com.orekyuu.javatter.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
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

	@Override
	public Component getComponent() {
		JPanel panel = new JPanel();
		panel.setAlignmentX(0);
		panel.setAlignmentY(0);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBackground(BackGroundColor.color);

		{
			JPanel p=new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
			p.setBackground(BackGroundColor.color);

			JLabel label=new JLabel("JavaビームオートRT");
			label.setAlignmentX(0);
			p.add(label,BorderLayout.LINE_START);

			javaBeamRT=new JCheckBox();
			javaBeamRT.setAlignmentX(0);
			javaBeamRT.addActionListener(this);
			p.add(javaBeamRT);
			p.setAlignmentX(0);
			panel.add(p);
		}

		{
			JPanel p=new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
			p.setBackground(BackGroundColor.color);

			JLabel label=new JLabel("ふぁぼありがとうございます");
			label.setAlignmentX(0);
			p.add(label,BorderLayout.LINE_START);

			thanks=new JCheckBox();
			thanks.setAlignmentX(0);
			thanks.addActionListener(this);
			p.add(thanks);
			p.setAlignmentX(0);
			panel.add(p);
		}

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
	}

}
