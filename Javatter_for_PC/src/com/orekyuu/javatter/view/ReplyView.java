package com.orekyuu.javatter.view;

import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.orekyuu.javatter.logic.UserStreamLogic;
import com.orekyuu.javatter.model.ReplyModel;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.util.TweetObjectFactory;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;
import com.orekyuu.javatter.viewobserver.UserStreamViewObserver;

public class ReplyView
implements UserStreamViewObserver, IJavatterTab
{
	private Component component;
	private JPanel panel;
	private UserEventViewObserver observer;
	private JScrollPane tp;
	private List<TweetObjectBuilder> builders;

	public ReplyView(UserEventViewObserver observer,List<TweetObjectBuilder> builders)
	{
		this.panel = new JPanel();
		this.panel.setBackground(BackGroundColor.color);
		this.panel.setLayout(new BoxLayout(this.panel, 3));
		this.tp = new JScrollPane(22, 31);
		this.tp.setViewportView(this.panel);
		this.tp.getVerticalScrollBar().setUnitIncrement(20);
		this.component = this.tp;
		this.observer = observer;
		this.builders=builders;
	}

	public void update(UserStreamLogic model)
	{
		if ((model instanceof ReplyModel)) {
			TweetObjectFactory factory = new TweetObjectFactory(model.getStatus(),builders);
			if (this.panel.getComponentCount() == 1000) this.panel.remove(999);
			this.panel.add(factory.createTweetObject(this.observer), 0);
			this.panel.updateUI();
		}
	}

	public Component getComponent()
	{
		return this.component;
	}
}