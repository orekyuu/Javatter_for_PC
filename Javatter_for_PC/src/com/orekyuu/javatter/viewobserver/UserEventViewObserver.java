package com.orekyuu.javatter.viewobserver;

import twitter4j.Status;

public interface UserEventViewObserver {

	public void onUserEvent(String type,Status status);
}
