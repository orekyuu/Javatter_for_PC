package com.orekyuu.javatter.viewobserver;

import java.util.Queue;

import com.orekyuu.javatter.controller.ProfileController;

import twitter4j.Status;
import twitter4j.User;

public interface ProfileViewObserver {

	public void userUpdate(User user);

	public void timeLineUpdate(Queue<Status> timeline);

	public void followUserUpdate(Queue<User> follow);

	public void followerUserUpdate(Queue<User> queue);

	public void setController(ProfileController controller);

	public void create();
}
