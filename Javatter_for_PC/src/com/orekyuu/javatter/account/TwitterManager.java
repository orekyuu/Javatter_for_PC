package com.orekyuu.javatter.account;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TwitterManager {

	private static TwitterManager manager=new TwitterManager();
	private static Twitter twitter;

	private String consumerKey="NjmzppL9pYlSFykDoNg";
	private String consumerSecret="XsRMKLGEerg1uLPziq5VfPQgsxiOR6i1iZUCD8KxTI";

	private TwitterManager(){
		twitter=TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}

	public static TwitterManager getInstance(){
		return manager;
	}

	public Twitter getTwitter(){
		return twitter;
	}

	public String getConsumerKey(){
		return consumerKey;
	}

	public String getConsumerSecret(){
		return consumerSecret;
	}
}
