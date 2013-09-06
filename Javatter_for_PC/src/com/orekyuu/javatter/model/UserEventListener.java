package com.orekyuu.javatter.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import twitter4j.Status;

import com.orekyuu.javatter.logic.TwitterUserEventLogic;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;

/**
 * ユーザーイベントのモデル
 * @author orekyuu
 *
 */
public class UserEventListener implements TwitterUserEventLogic,ActionListener{

	private Object rt;
	private Object fav;
	private Object rep;
	private Status tweet;
	private UserEventViewObserver view;

	public UserEventListener(Status status,UserEventViewObserver view){
		tweet=status;
		this.view=view;
	}

	@Override
	public void setRtButton(Object obj) {
		rt=obj;
	}

	@Override
	public void setFavButton(Object obj) {
		fav=obj;
	}

	@Override
	public void setReplyButton(Object obj) {
		rep=obj;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(rt)){
			view.onUserEvent("rt", tweet);
			JToggleButton b=(JToggleButton) rt;
			b.setEnabled(false);
		}else if(e.getSource().equals(fav)){
			JToggleButton b=(JToggleButton) fav;
			if(b.isSelected()){
				view.onUserEvent("fav", tweet);
			}else{
				view.onUserEvent("unfav", tweet);
			}
		}else if(e.getSource().equals(rep)){
			view.onUserEvent("reply", tweet);
		}
	}

}
