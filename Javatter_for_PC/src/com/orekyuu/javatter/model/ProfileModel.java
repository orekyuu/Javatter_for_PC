package com.orekyuu.javatter.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.viewobserver.ProfileViewObserver;

public class ProfileModel {

	private ProfileViewObserver view;
	private Queue<Status> timeline=new LinkedList<Status>();
	private Stack<Long> follow=new Stack<Long>();
	private Stack<Long> follower=new Stack<Long>();
	private Twitter twitter=TwitterManager.getInstance().getTwitter();

	public void setView(ProfileViewObserver view){
		this.view=view;
	}

	public void setUser(User user){
		System.out.println("model");
		view.userUpdate(user);
	}

	public void addTimeline(Status status){
		timeline.add(status);
	}

	public void notifyTimeLine(){
		view.timeLineUpdate(timeline);
	}

	public void addFollow(long user){
		follow.add(user);
	}

	public void notifyFollow(int count){
		if(count>100)new IllegalArgumentException("読み込む数は100以下にしてください :"+count);
		if(follow.isEmpty())return ;
		Queue<User> queue=new LinkedList<User>();

		long[] list=new long[count>follow.size()?follow.size():count];
		for(int i=0;i<list.length;i++){
			list[i]=follow.pop();
		}

		try {
			queue.addAll(twitter.lookupUsers(list));
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		view.followUserUpdate(queue);
	}

	public void addFollower(long user){
		follower.add(user);
	}

	public void notifyFollower(int count){
		if(count>100)new IllegalArgumentException("読み込む数は100以下にしてください :"+count);
		if(follower.isEmpty())return ;
		Queue<User> queue=new LinkedList<User>();

		long[] list=new long[count>follow.size()?follow.size():count];
		for(int i=0;i<list.length;i++){
			list[i]=follower.pop();
		}

		try {
			queue.addAll(twitter.lookupUsers(list));
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		view.followerUserUpdate(queue);
	}
}
