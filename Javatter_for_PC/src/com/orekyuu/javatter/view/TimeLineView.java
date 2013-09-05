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
import com.orekyuu.javatter.model.TimeLineModel;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.util.TweetObjectFactory;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

/**
 * タイムラインタブ描画クラス
 * @author orekyuu
 *
 */
public class TimeLineView
implements UserStreamViewObserver, IJavatterTab, AdjustmentListener
{
	private Component component;
	private JPanel timeline;
	private UserEventViewObserver observer;
	private List<TweetObjectBuilder> builders;
	private JScrollPane tp;

	private volatile Queue<Status> queue=new LinkedList<Status>();
	private boolean queueFlag;
	private boolean queueEvent;
	private JPanel last;

	/**
	 * @param observer ユーザーイベントリスナ
	 * @param builders TweetObjectBuilderのリスト
	 */
	public TimeLineView(UserEventViewObserver observer,List<TweetObjectBuilder> builders)
	{
		this.timeline = new JPanel();
		this.timeline.setBackground(BackGroundColor.color);
		this.timeline.setLayout(new BoxLayout(this.timeline, 3));
		tp = new JScrollPane(22, 31);
		tp.setViewportView(this.timeline);
		tp.getVerticalScrollBar().setUnitIncrement(20);
		tp.getVerticalScrollBar().addAdjustmentListener(this);
		this.component = tp;
		this.observer = observer;
		this.builders=builders;
	}

	@Override
	public void update(UserStreamLogic model)
	{
		if ((model instanceof TimeLineModel)) {
			if(tp.getVerticalScrollBar().getValue()==0&&!queueFlag){
				addObject(model.getStatus());
			}else{
				queue.add(model.getStatus());
				setNumber(queue.size());
			}
		}
	}

	private synchronized void setNumber(int num){
		JTabbedPane tab=(JTabbedPane) component.getParent();
		for(int i=0;i<tab.getTabCount();i++){
			if(tab.getComponentAt(i) == this.component){
				if(num!=0){
					tab.setTitleAt(i, "TimeLine("+num+")");
				}else{
					tab.setTitleAt(i, "TimeLine");
				}
			}
		}
	}

	private JPanel createObject(Status status){
		TweetObjectFactory factory = new TweetObjectFactory(status,builders);
		return (JPanel) factory.createTweetObject(this.observer).getComponent();
	}

	private synchronized void addObject(Status status){
		JPanel panel = createObject(status);
		panel.updateUI();
		if (this.timeline.getComponentCount() == 1000) this.timeline.remove(999);
		this.timeline.add(panel, 0);
		this.timeline.updateUI();
		last = panel;
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		if(arg0.getValue()==0){
			if(queueEvent){
				return;
			}
			queueEvent = true;
			Thread th=new Thread(){
				@Override
				public void run(){
					queueFlag=true;
					JPanel lastPanel = last;
					while(!queue.isEmpty()){
						addObject(queue.poll());
					}
					setNumber(0);
					queueFlag=false;
					if(lastPanel != null){
						tp.validate();
						tp.getVerticalScrollBar().setValue(lastPanel.getLocation().y);
					}
				}
			};
			th.start();
		}
		else{
			queueEvent = false;
		}
	}

	@Override
	public Component getComponent()
	{
		return this.component;
	}

}
