package com.orekyuu.javatter.view;

import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.model.TimeLineModel;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.util.TweetObjectFactory;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

public class TimeLineView
implements UserStreamViewObserver, IJavatterTab
{
	private Component component;
	private JPanel timeline;
	private UserEventViewObserver observer;
	private List<TweetObjectBuilder> builders;

	public TimeLineView(UserEventViewObserver observer,List<TweetObjectBuilder> builders)
	{
		this.timeline = new JPanel();
		this.timeline.setBackground(BackGroundColor.color);
		this.timeline.setLayout(new BoxLayout(this.timeline, 3));
		JScrollPane tp = new JScrollPane(22, 31);
		tp.setViewportView(this.timeline);
		tp.getVerticalScrollBar().setUnitIncrement(20);
		this.component = tp;
		this.observer = observer;
		this.builders=builders;
	}

	public void update(UserStreamLogic model)
	{
		if ((model instanceof TimeLineModel)) {
			TweetObjectFactory factory = new TweetObjectFactory(model.getStatus(),builders);
			JPanel panel = factory.createTweetObject(this.observer);
			panel.updateUI();
			if (this.timeline.getComponentCount() == 1000) this.timeline.remove(999);
			this.timeline.add(panel, 0);
			this.timeline.updateUI();
		}
	}

	public Component getComponent()
	{
		return this.component;
	}
}