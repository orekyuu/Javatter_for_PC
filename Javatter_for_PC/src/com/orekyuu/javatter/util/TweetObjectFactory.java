package com.orekyuu.javatter.util;

import java.util.List;

import twitter4j.Status;

import com.orekyuu.javatter.controller.TweetObjectController;
import com.orekyuu.javatter.main.Main;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;

/**
 * TweetObjectのファクトリ
 * @author orekyuu
 *
 */
public class TweetObjectFactory
{
	private Status status;
	private List<TweetObjectBuilder> builders;

	/**
	 * @param status 対象のStatus
	 * @param builders TweetObjectBuilderのリスト
	 */
	public TweetObjectFactory(Status status,List<TweetObjectBuilder> builders)
	{
		this.status = status;
		this.builders=builders;
	}

	/**
	 * TweetObjecctを作成します
	 * @param observer
	 * @return
	 */
	public TweetObject createTweetObject(UserEventViewObserver observer){
		TweetObject object=new TweetObject(status, builders);
		object.setUserEventViewObserver(observer);
		TweetObjectController controller=new TweetObjectController(status);
		controller.setLogic(object);
		Main.getMainController().getUserStream().addUserStreamController(controller);
		object.create();
		return object;
	}
}