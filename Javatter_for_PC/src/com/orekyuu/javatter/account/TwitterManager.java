package com.orekyuu.javatter.account;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Twitter管理クラス
 * @author orekyuu
 *
 */
public class TwitterManager {

	private static TwitterManager manager=new TwitterManager();
	private static Twitter twitter;

	private String consumerKey="NjmzppL9pYlSFykDoNg";
	private String consumerSecret="XsRMKLGEerg1uLPziq5VfPQgsxiOR6i1iZUCD8KxTI";

	private TwitterManager(){
		twitter=TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}

	/**
	 * インスタンスを取得します
	 * @return
	 */
	public static TwitterManager getInstance(){
		return manager;
	}

	/**
	 * Twitterインスタンスを取得します
	 * @return
	 */
	public Twitter getTwitter(){
		return twitter;
	}

	/**
	 * コンシューマキーを返します
	 * @return
	 */
	public String getConsumerKey(){
		return consumerKey;
	}

	/**
	 * コンシューマシークレットを返します
	 * @return
	 */
	public String getConsumerSecret(){
		return consumerSecret;
	}
}
