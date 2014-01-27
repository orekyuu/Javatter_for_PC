package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import twitter4j.Status;

import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.model.ReplyModel;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.util.TweetObjectFactory;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

/**
 * リプライタブ描画クラス
 * 
 * @author orekyuu
 * 
 */
public class ReplyView extends TimeLineView implements UserStreamViewObserver, IJavatterTab, AdjustmentListener{

	/**
	 * @param observer
	 *            ユーザーイベントリスナ
	 * @param builders
	 *            TweetObjectBuilderのリスト
	 */
	public ReplyView(UserEventViewObserver observer, List<TweetObjectBuilder> builders){
		super(observer, builders);
		title = "Reply";
	}

	@Override
	public void update(UserStreamLogic model){
		if (model instanceof ReplyModel){
			addObject(model.getStatus());
		}
	}

}
