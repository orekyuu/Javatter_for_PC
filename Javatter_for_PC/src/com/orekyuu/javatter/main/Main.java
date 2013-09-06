package com.orekyuu.javatter.main;

import java.net.MalformedURLException;

import twitter4j.TwitterException;

import com.orekyuu.javatter.controller.MainWindowController;
import com.orekyuu.javatter.util.JavatterConfig;
import com.orekyuu.javatter.util.SaveDataManager;
import com.orekyuu.javatter.view.MainWindowView;

public class Main {

	private static MainWindowView view;
	private static MainWindowController controller;

	public static void main(String[] args) throws MalformedURLException, TwitterException {

		SaveDataManager.getInstance().init();
		JavatterConfig.getInstance().init();

		view=new MainWindowView();
		controller=new MainWindowController(view);
		view.setTweetController(controller);

		view.create();
		controller.start();
	}

	/**
	 * メインウィンドウのViewを返す
	 * @return
	 */
	public static MainWindowView getMainView(){
		return view;
	}

	/**
	 * メインウィンドウのControllerを返す
	 * @return
	 */
	public static MainWindowController getMainController(){
		return controller;
	}

	/**
	 * Javatterのバージョンを返す
	 * @return
	 */
	public static int getJavatterVersion(){
		return 4;
	}
}
