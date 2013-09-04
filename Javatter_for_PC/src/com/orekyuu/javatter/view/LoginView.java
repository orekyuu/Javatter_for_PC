package com.orekyuu.javatter.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import twitter4j.TwitterException;

import com.orekyuu.javatter.controller.LoginController;
import com.orekyuu.javatter.logic.TwitterLoginLogic;
import com.orekyuu.javatter.viewobserver.LoginViewObserver;

/**
 * ログイン画面描画クラス
 * @author orekyuu
 *
 */
public class LoginView implements LoginViewObserver,ActionListener{

	private LoginController controller;
	private JTextField pinArea;
	private JDialog dialog;

	@Override
	public void openView(URL url) {
		try {
			Desktop.getDesktop().browse(url.toURI());
			createWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createWindow(){
		pinArea=new JTextField(10);
		JButton button=new JButton("認証");
		button.addActionListener(this);
		JPanel panel=new JPanel();
		panel.add(pinArea,BorderLayout.PAGE_START);
		panel.add(button,BorderLayout.PAGE_END);

		dialog=new JDialog();
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.add(panel);
		dialog.setSize(400, 200);
		dialog.setVisible(true);
	}

	@Override
	public void update(TwitterLoginLogic logic) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			controller.onAuthentication(pinArea.getText());
			dialog.dispose();
		} catch (TwitterException e1) {
			e1.printStackTrace();
			onFailure();
		}
	}

	public void onFailure(){
		dialog.dispose();
		JDialog d=new JDialog(new JFrame(), "認証失敗");
		d.add(new JLabel("再起動して認証しなおしてください。"));
		d.setSize(300,70);
		d.setModalityType(ModalityType.APPLICATION_MODAL);
		d.setVisible(true);
	}

	@Override
	public void setController(LoginController controller) {
		this.controller=controller;
	}

}
