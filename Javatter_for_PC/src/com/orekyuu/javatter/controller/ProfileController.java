package com.orekyuu.javatter.controller;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.model.ProfileModel;
import com.orekyuu.javatter.viewobserver.ProfileViewObserver;

public class ProfileController {

	private ProfileModel model;
	private ProfileViewObserver view;
	private String userName;
	private User user;

	private long follower=-1;
	private boolean followerHasNext=true;
	private long follow=-1;
	private boolean followHasNext=true;
	public ProfileController(User user){
		this.userName=user.getScreenName();
		this.user=user;
	}

	public void setModel(ProfileModel model){
		this.model=model;
	}

	public void init(){
		Thread th=new Thread(){
			@Override
			public void run(){
				Twitter twitter=TwitterManager.getInstance().getTwitter();
				try {
					view.create();
					System.out.println("controller");
					model.setUser(user);
					for(Status s:twitter.getUserTimeline(userName,new Paging())){
						model.addTimeline(s);
					}
					model.notifyTimeLine();

					loadFollowList();
					loadFollowerList();

				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

	public void setView(ProfileViewObserver view){
		this.view=view;
	}

	private void loadFollowerList(){
		Twitter twitter=TwitterManager.getInstance().getTwitter();
		long[] list=getFollowerUsers(twitter, userName);
		if(list!=null&&list.length!=0){
			for(int i=list.length-1;i!=0;i--){
				model.addFollower(list[i]);
			}
		}
		model.notifyFollower(20);
	}

	private void loadFollowList(){
		Twitter twitter=TwitterManager.getInstance().getTwitter();
		long[] list=getFollowUsers(twitter, userName);
		if(list!=null&&list.length!=0){
			for(int i=list.length-1;i!=0;i--){
				model.addFollow(list[i]);
			}
		}
		model.notifyFollow(20);
	}

	/**
	 * フォローリストを読み込みます
	 * @param count
	 */
	public void loadFollow(int count){
		model.notifyFollow(count);
	}

	/**
	 * フォロワーリストを読み込みます
	 * @param count
	 */
	public void loadFollower(int count){
		model.notifyFollower(count);
	}

	public long[] getFollowerUsers(Twitter twitter, String screenName){
		long[] dataToReturn = null;
		try {
			if(followerHasNext){
				IDs ids=twitter.getFollowersIDs(screenName, follower);
				dataToReturn=ids.getIDs();
				follower=ids.getNextCursor();
				followerHasNext=ids.hasNext();
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return dataToReturn;
	}

	public long[] getFollowUsers(Twitter twitter, String screenName){
		long[] dataToReturn = null;
		try {
			if(followHasNext){
				IDs followIDs=twitter.getFriendsIDs(screenName, follow);
				dataToReturn=followIDs.getIDs();
				follow=followIDs.getNextCursor();
				followHasNext=followIDs.hasNext();
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return dataToReturn;
	}
}
