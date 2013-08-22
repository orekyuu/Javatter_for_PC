package com.orekyuu.javatter.viewobserver;

import twitter4j.Status;
import twitter4j.User;

public interface PopupViewObserver {

	public void onFav(User user,Status status);

	public void onRT(User rtUser,Status rt);

	public void onUnFav(User user,Status status);

	public void onFollow(User user);

	public void onBlock(User user);
}
