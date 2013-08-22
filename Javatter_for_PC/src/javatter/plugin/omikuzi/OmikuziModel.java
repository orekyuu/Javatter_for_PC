package javatter.plugin.omikuzi;

import twitter4j.Status;

import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.util.SaveData;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

public class OmikuziModel
implements UserStreamLogic
{
	private OmikuziUtil util;

	public OmikuziModel(SaveData saveData) {
		 util = new OmikuziUtil(saveData);
	}

	public Status getStatus()
	{
		return null;
	}

	public void onStatus(Status status)
	{
	}

	public void onReplyTweet(Status status)
	{
		this.util.tweet(status);
	}

	public void onRetweetTweet(Status status)
	{
	}

	public void setView(UserStreamViewObserver view)
	{
	}
}