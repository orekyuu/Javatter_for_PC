package com.orekyuu.javatter.main;

import java.net.MalformedURLException;

import twitter4j.TwitterException;

import com.orekyuu.javatter.controller.MainWindowController;
import com.orekyuu.javatter.util.JavatterConfig;
import com.orekyuu.javatter.util.SaveDataManager;
import com.orekyuu.javatter.view.MainWindowView;

public class Main {

	public static void main(String[] args) throws MalformedURLException, TwitterException {

		SaveDataManager.getInstance().init();
		JavatterConfig.getInstance().init();

		MainWindowView view=new MainWindowView();
		MainWindowController controller=new MainWindowController(view);
		view.setTweetController(controller);

		view.create();
		controller.start();
	}
}
