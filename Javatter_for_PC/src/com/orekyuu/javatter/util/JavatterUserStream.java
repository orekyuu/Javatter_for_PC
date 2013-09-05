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
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onDeletionNotice(arg0);
		}
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onScrubGeo(arg0, arg1);
		}
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onStallWarning(arg0);
		}
	}

	@Override
	public void onStatus(Status arg0) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onStatus(arg0);
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onTrackLimitationNotice(arg0);
		}
	}

	@Override
	public void onException(Exception arg0) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onException(arg0);
		}
	}

	@Override
	public void onBlock(User arg0, User arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onBlock(arg0,arg1);
		}
	}

	@Override
	public void onDeletionNotice(long arg0, long arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onDeletionNotice(arg0,arg1);
		}
	}

	@Override
	public void onDirectMessage(DirectMessage arg0) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onDirectMessage(arg0);
		}
	}

	@Override
	public void onFavorite(User arg0, User arg1, Status arg2) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onFavorite(arg0,arg1,arg2);
		}
	}

	@Override
	public void onFollow(User arg0, User arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onFollow(arg0,arg1);
		}
	}

	@Override
	public void onFriendList(long[] arg0) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onFriendList(arg0);
		}
	}

	@Override
	public void onUnblock(User arg0, User arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUnblock(arg0,arg1);
		}
	}

	@Override
	public void onUnfavorite(User arg0, User arg1, Status arg2) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUnfavorite(arg0,arg1,arg2);
		}
	}

	@Override
	public void onUserListCreation(User arg0, UserList arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserListCreation(arg0,arg1);
		}
	}

	@Override
	public void onUserListDeletion(User arg0, UserList arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserListDeletion(arg0,arg1);
		}
	}

	@Override
	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserListMemberAddition(arg0,arg1,arg2);
		}
	}

	@Override
	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserListMemberDeletion(arg0, arg1, arg2);
		}
	}

	@Override
	public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserListSubscription(arg0, arg1, arg2);
		}
	}

	@Override
	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserListUnsubscription(arg0, arg1, arg2);
		}
	}

	@Override
	public void onUserListUpdate(User arg0, UserList arg1) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserListUpdate(arg0, arg1);
		}
	}

	@Override
	public void onUserProfileUpdate(User arg0) {
		int size=controllers.size();
		for(int i=0;i<size;i++){
			controllers.get(i).onUserProfileUpdate(arg0);
		}
	}

}
