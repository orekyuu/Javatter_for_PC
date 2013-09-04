package com.orekyuu.javatter.controller;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.model.PopupModel;

/**
 * ポップアップのController
 * @author orekyuu
 *
 */
public class PopupController extends UserStreamController{

	private PopupModel model;

	/**
	 * モデルを設定
	 * @param model
	 */
	public void setModel(PopupModel model){
		this.model=model;
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {

	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {

	}

	@Override
	public void onStallWarning(StallWarning arg0) {

	}

	@Override
	public void onStatus(Status arg0) {
		try {
			if(arg0.isRetweet() && arg0.getRetweetedStatus().getUser().getScreenName().equals(TwitterManager.getInstance().getTwitter().getScreenName())){
				Status status=arg0.getRetweetedStatus();
				User user=arg0.getUser();
				model.onRT(user, status);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {

	}

	@Override
	public void onException(Exception arg0) {

	}

	@Override
	public void onBlock(User arg0, User arg1) {
		try {
			if(!arg0.getScreenName().equals(TwitterManager.getInstance().getTwitter().getScreenName())){
				model.onBlock(arg0);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDeletionNotice(long arg0, long arg1) {

	}

	@Override
	public void onDirectMessage(DirectMessage arg0) {

	}

	@Override
	public void onFavorite(User arg0, User arg1, Status arg2) {
		try {
			if(!arg0.getScreenName().equals(TwitterManager.getInstance().getTwitter().getScreenName())){
				model.onFav(arg0, arg2);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFollow(User arg0, User arg1) {
		try {
			if(!arg0.getScreenName().equals(TwitterManager.getInstance().getTwitter().getScreenName()))
			model.onFollow(arg0);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFriendList(long[] arg0) {

	}

	@Override
	public void onUnblock(User arg0, User arg1) {

	}

	@Override
	public void onUnfavorite(User arg0, User arg1, Status arg2) {
		try {
			if(!arg0.getScreenName().equals(TwitterManager.getInstance().getTwitter().getScreenName())){
				model.onUnFav(arg0, arg2);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUserListCreation(User arg0, UserList arg1) {

	}

	@Override
	public void onUserListDeletion(User arg0, UserList arg1) {

	}

	@Override
	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {

	}

	@Override
	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {

	}

	@Override
	public void onUserListSubscription(User arg0, User arg1, UserList arg2) {

	}

	@Override
	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {

	}

	@Override
	public void onUserListUpdate(User arg0, UserList arg1) {

	}

	@Override
	public void onUserProfileUpdate(User arg0) {

	}

}
