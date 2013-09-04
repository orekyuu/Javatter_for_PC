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

/**
 * プロフィールのController
 * @author orekyuu
 *
 */
public class ProfileController {

	private ProfileModel model;
	private ProfileViewObserver view;
	private String userName;
	private User user;

	private long follower=-1;
	private boolean followerHasNext=true;
	private long follow=-1;
	private boolean followHasNext=true;

	/**
	 *
	 * @param user プロフィールに表示するユーザー
	 */
	public ProfileController(User user){
		this.userName=user.getScreenName();
		this.user=user;
	}

	/**
	 * モデルを設定
	 * @param model
	 */
	public void setModel(ProfileModel model){
		this.model=model;
	}

	/**
	 * ウィンドウを初期化
	 */
	public void init(){
		Thread th=new Thread(){
			@Override
			public void run(){
				Twitter twitter=TwitterManager.getInstance().getTwitter();
				try {
					view.create();
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

	/**
	 * Viewを設定
	 * @param view
	 */
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
	 * @param count 読み込む個数
	 */
	public void loadFollow(int count){
		model.notifyFollow(count);
	}

	/**
	 * フォロワーリストを読み込みます
	 * @param count 読み込む個数
	 */
	public void loadFollower(int count){
		model.notifyFollower(count);
	}

	/**
	 * フォロワーを読み込む
	 * @param twitter Twitterインスタンス
	 * @param screenName 対象のアカウント名
	 * @return
	 */
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

	/**
	 * フォローしているユーザーを読み込む
	 * @param twitter Twitterインスタンス
	 * @param screenName 対象のアカウント名
	 * @return
	 */
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
