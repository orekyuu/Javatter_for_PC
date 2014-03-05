package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import twitter4j.Status;

import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.model.TimeLineModel;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.util.TweetObjectFactory;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

/**
 * タイムラインタブ描画クラス
 * 
 * @author orekyuu
 * 
 */
public class TimeLineView implements UserStreamViewObserver, IJavatterTab, AdjustmentListener{
	private Component component;
	private TimelineTable timeline;
	private UserEventViewObserver observer;
	private List<TweetObjectBuilder> builders;
	private JScrollPane tp;

	private volatile Queue<JPanel> queue = new ConcurrentLinkedQueue<JPanel>();
	private boolean queueFlag;
	private boolean queueEvent;
	
	protected String title;

	/**
	 * @param observer
	 *            ユーザーイベントリスナ
	 * @param builders
	 *            TweetObjectBuilderのリスト
	 */
	public TimeLineView(UserEventViewObserver observer, List<TweetObjectBuilder> builders){
		this.timeline = new TimelineTable();
		this.timeline.setBackground(BackGroundColor.color);
		tp = new JScrollPane(22, 31);
		tp.setViewportView(this.timeline);
		tp.getVerticalScrollBar().setUnitIncrement(20);
		tp.getVerticalScrollBar().addAdjustmentListener(this);
		this.component = tp;
		this.observer = observer;
		this.builders = builders;
		this.title = "TimeLine";
	}

	@Override
	public void update(UserStreamLogic model){
		if ((model instanceof TimeLineModel)){
			addObject(model.getStatus());
		}
	}
	
	public void addObject(Status status){
		final JPanel p = createObject(status);
		if (tp.getVerticalScrollBar().getValue() == 0 && !queueFlag){
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					timeline.addTop(p);
				}
			});
		}else{
			queue.add(p);
			setNumber(queue.size());
		}
	}

	public synchronized void setNumber(int num){
		JTabbedPane tab = (JTabbedPane) component.getParent();
		for (int i = 0; i < tab.getTabCount(); i++){
			if (tab.getComponentAt(i) == this.component){
				if (num != 0){
					tab.setTitleAt(i, title + "(" + num + ")");
				}else{
					tab.setTitleAt(i, title);
				}
			}
		}
	}

	private JPanel createObject(Status status){
		TweetObjectFactory factory = new TweetObjectFactory(status, builders);
		return (JPanel) factory.createTweetObject(this.observer).getComponent();
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0){
		if (arg0.getValue() == 0){
			if (queueEvent){
				return;
			}
			queueEvent = true;
			Thread th = new Thread(){
				@Override
				public void run(){
					queueFlag = true;
					final Queue<JPanel> q = new LinkedList<JPanel>();
					while (!queue.isEmpty()){
						q.add(queue.poll());
					}
					SwingUtilities.invokeLater(new Runnable(){
						public void run(){
							final int c = q.size();
							while (!q.isEmpty()){
								timeline.addTop(q.poll());
							}
							for (int i = 0; i < c; i++){
								timeline.prepareRenderer(timeline.getCellRenderer(i, 0), i, 0);
							}
							SwingUtilities.invokeLater(new Runnable(){
								public void run(){
									tp.getVerticalScrollBar().setValue(timeline.getCellRect(c, 0, true).y);
								}
							});
							setNumber(0);
							queueFlag = false;
						}
					});
				}
			};
			th.start();
		}else{
			queueEvent = false;
		}
	}

	@Override
	public Component getComponent(){
		return this.component;
	}

}
