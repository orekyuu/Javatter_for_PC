package com.orekyuu.javatter.controller;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.logic.TweetObjectLogic;

/**
 * UserStreamのイベントによってTweetObjectに変更を加える
 * @author orekyuu
 *
 */
public class TweetObjectController extends UserStreamController{

	private Status status;
	private TweetObjectLogic logic;
	private User statususer;
	private long user;
	/**
	 * 監視対象のStatus
	 * @param status
	 */
	public TweetObjectController(Status status){
		this.status=status;
		this.statususer=status.getUser();
		try {
			this.user=TwitterManager.getInstance().getTwitter().getId();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TweetObjectLogicのセッタ
	 * @param logic
	 */
	public void setLogic(TweetObjectLogic logic){
		this.logic=logic;
	}

	@Override
	public void onFavorite(User source, User target, Status favoritedStatus) {
		if(status.getId()==favoritedStatus.getId()||(status.isRetweet()&&status.getRetweetedStatus().getId()==status.getId())){
			if(source.getId()==user){
				logic.onFavoriteByMe();
			}else{
				logic.onFavorite(source);
			}
		}
	}

	@Override
	public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
		if(status.getId()==unfavoritedStatus.getId()||(status.isRetweet()&&status.getRetweetedStatus().getId()==status.getId())){
			if(source.getId()==user){
				logic.unFavoriteByMe();
			}else{
				logic.unFavorite(source);
			}
		}
	}

	@Override
	public void onDeletionNotice(long directMessageId, long userId) {
		if(directMessageId==status.getId()){
			logic.deleteTweet();
		}
	}

	@Override
	public void onUserProfileUpdate(User updatedUser) {
		if(statususer.getId()==updatedUser.getId()){
			logic.updateProfile(updatedUser);
		}
	}

	@Override
	public void onStatus(Status s){
		if(s.isRetweet()&&status.getId()==s.getRetweetedStatus().getId()){
			if(s.getUser().getId()==user){
				logic.onRTByMe();
			}else{
				logic.onRT(s.getUser());
			}
		}
	}
}
