package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

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

public class TimeLineView
implements UserStreamViewObserver, IJavatterTab, AdjustmentListener
{
	private Component component;
	private JPanel timeline;
	private UserEventViewObserver observer;
	private List<TweetObjectBuilder> builders;
	private JScrollPane tp;

	private Queue<Status> queue=new LinkedList<Status>();

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

	public void update(UserStreamLogic model)
	{
		if ((model instanceof TimeLineModel)) {
			if(tp.getVerticalScrollBar().getValue()==0){
				addObject(model.getStatus());
			}else{
				queue.add(model.getStatus());
				setNumber(queue.size());
			}
		}
	}

	private void setNumber(int num){
		JTabbedPane tab=(JTabbedPane) component.getParent();
		Pattern p=Pattern.compile("^TimeLine(\\(\\d+\\))?$");
		for(int i=0;i<tab.getTabCount();i++){
			if(p.matcher(tab.getTitleAt(i)).matches()){
				if(num!=0){
					tab.setTitleAt(i, "TimeLine("+num+")");
				}else{
					tab.setTitleAt(i, "TimeLine");
				}
			}
		}
	}

	private void addObject(Status status){
		TweetObjectFactory factory = new TweetObjectFactory(status,builders);
		JPanel panel = factory.createTweetObject(this.observer);
		panel.updateUI();
		if (this.timeline.getComponentCount() == 1000) this.timeline.remove(999);
		this.timeline.add(panel, 0);
		this.timeline.updateUI();
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		if(arg0.getValue()==0){
			while(!queue.isEmpty()){
				addObject(queue.poll());
			}
			setNumber(0);
		}
	}

	@Override
	public Component getComponent()
	{
		return this.component;
	}

}