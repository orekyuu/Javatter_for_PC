package com.orekyuu.javatter.view;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import twitter4j.Status;
import twitter4j.User;

import com.orekyuu.javatter.util.ImageManager;
import com.orekyuu.javatter.viewobserver.PopupViewObserver;

/**
 * ポップアップの描画クラス
 * @author orekyuu
 *
 */
public class PopupView implements PopupViewObserver {

	private TrayIcon icon;
	public PopupView(){
		try{
			icon = new TrayIcon(ImageManager.getInstance().getImage(ImageManager.ICON));
			SystemTray.getSystemTray().add(icon);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onFav(User user,Status status) {
		icon.displayMessage(user.getName()+"にふぁぼられました", status.getText(), MessageType.NONE);
	}

	@Override
	public void onRT(User rtUser, Status rt) {
		icon.displayMessage(rtUser.getName()+"にRTされました", rt.getText(), MessageType.NONE);
	}

	@Override
	public void onFollow(User user) {
		icon.displayMessage(user.getName()+"にフォローされました", "", MessageType.NONE);
	}

	@Override
	public void onBlock(User user) {
		icon.displayMessage(user.getName()+"にブロックされました", "", MessageType.NONE);
	}

	@Override
	public void onUnFav(User user, Status status) {
		icon.displayMessage(user.getName()+"にあんふぁぼされました", status.getText(), MessageType.NONE);
	}

}
