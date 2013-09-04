package com.orekyuu.javatter.util;

import java.util.LinkedList;
import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

import com.orekyuu.javatter.account.AccountManager;
import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.controller.UserStreamController;

/**
 * Javatterのユーザーストリーム
 * @author orekyuu
 *
 */
public class JavatterUserStream implements UserStreamListener{

	private List<UserStreamController> controllers=new LinkedList<UserStreamController>();

	/**
	 * ユーザーストリームを追加
	 * @param controller
	 */
	public void addUserStreamController(UserStreamController controller){
		controllers.add(controller);
	}

	/**
	 * ユーザーストリームを開始する
	 */
	public void start(){
		TwitterManager m=TwitterManager.getInstance();
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer(m.getConsumerKey(), m.getConsumerSecret());
		twitterStream.setOAuthAccessToken(AccountManager.getInstance().getAccessToken());
		twitterStream.addListener(this);
		twitterStream.user();
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		for(UserStreamController c:controllers){
			c.onDeletionNotice(arg0);
		}
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		for(UserStreamController c:controllers){
			c.onScrubGeo(arg0, arg1);
		}
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		for(UserStreamController c:controllers){
			c.onStallWarning(arg0);
		}
	}

	@Override
	public void onStatus(Status arg0) {
		for(UserStreamController c:controllers){
			c.onStatus(arg0);
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		for(UserStreamController c:controllers){
			c.onTrackLimitationNotice(arg0);
		}
	}

	@Override
	public void onException(Exception arg0) {
		for(UserStreamController c:controllers){
			c.onException(arg0);
		}
	}

	@Override
	public void onBlock(User arg0, User arg1) {
		for(UserStreamController c:controllers){
			c.onBlock(arg0,arg1);
		}
	}

	@Override
	public void onDeletionNotice(long arg0, long arg1) {
		for(UserStreamController c:controllers){
			c.onDeletionNotice(arg0,arg1);
		}
	}

	@Override
	public void onDirectMessage(DirectMessage arg0) {
		for(UserStreamController c:controllers){
			c.onDirectMessage(arg0);
		}
	}

	@Override
	public void onFavorite(User arg0, User arg1, Status arg2) {
		for(UserStreamController c:controllers){
			c.onFavorite(arg0,arg1,arg2);
		}
	}

	@Override
	public void onFollow(User arg0, User arg1) {
		for(UserStreamController c:controllers){
			c.onFollow(arg0,arg1);
		}
	}

	@Override
	public void onFriendList(long[] arg0) {
		for(UserStreamController c:controllers){
			c.onFriendList(arg0);
		}
	}

	@Override
	public void onUnblock(User arg0, User arg1) {
		for(UserStreamController c:controllers){
			c.onUnblock(arg0,arg1);
		}
	}

	@Override
	public void onUnfavorite(User arg0, User arg1, Status arg2) {
		for(UserStreamController c:controllers){
			c.onUnfavorite(arg0,arg1,arg2);
		}
	}

	@Override
	public void onUserListCreation(User arg0, UserList arg1) {
		for(UserStreamController c:controllers){
			c.onUserListCreation(arg0,arg1);
		}
	}

	@Override
	public void onUserListDeletion(User arg0, UserList arg1) {
		for(UserStreamController c:controllers){
			c.onUserListDeletion(arg0,arg1);
		}
	}

	@Override
	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
		for(UserStreamController c:controllers){
			c.onUserListMemberAddition(arg0,arg1,arg2);
		}
	}

	@Override
	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
		for(UserStreamController c:controllers){
			c.onUserListMemberDeletion(arg0, arg1, arg2);
		}
	}

	@Override
	public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
		for(UserStreamController c:controllers){
			c.onUserListSubscription(arg0, arg1, arg2);
		}
	}

	@Override
	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
		for(UserStreamController c:controllers){
			c.onUserListUnsubscription(arg0, arg1, arg2);
		}
	}

	@Override
	public void onUserListUpdate(User arg0, UserList arg1) {
		for(UserStreamController c:controllers){
			c.onUserListUpdate(arg0, arg1);
		}
	}

	@Override
	public void onUserProfileUpdate(User arg0) {
		for(UserStreamController c:controllers){
			c.onUserProfileUpdate(arg0);
		}
	}

}
