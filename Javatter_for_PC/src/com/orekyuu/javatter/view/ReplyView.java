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
 * @author orekyuu
 *
 */
public class ReplyView
implements UserStreamViewObserver, IJavatterTab, AdjustmentListener
{
	private Component component;
	private JPanel panel;
	private UserEventViewObserver observer;
	private JScrollPane tp;
	private List<TweetObjectBuilder> builders;

	private volatile Queue<Status> queue=new LinkedList<Status>();
	private boolean queueFlag;
	private boolean queueEvent;
	private JPanel last;

	/**
	 * @param observer ユーザーイベントリスナ
	 * @param builders TweetObjectBuilderのリスト
	 */
	public ReplyView(UserEventViewObserver observer,List<TweetObjectBuilder> builders)
	{
		this.panel = new JPanel();
		this.panel.setBackground(BackGroundColor.color);
		this.panel.setLayout(new BoxLayout(this.panel, 3));
		this.tp = new JScrollPane(22, 31);
		this.tp.setViewportView(this.panel);
		this.tp.getVerticalScrollBar().setUnitIncrement(20);
		this.tp.getVerticalScrollBar().addAdjustmentListener(this);
		this.component = this.tp;
		this.observer = observer;
		this.builders=builders;
	}

	@Override
	public void update(UserStreamLogic model)
	{
		if (model instanceof ReplyModel) {
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
					tab.setTitleAt(i, "Reply("+num+")");
				}else{
					tab.setTitleAt(i, "Reply");
				}
			}
		}
	}

	private JPanel createObject(Status status){
		TweetObjectFactory factory = new TweetObjectFactory(status,builders);
		return (JPanel) factory.createTweetObject(this.observer).getComponent();
	}

	private synchronized void addObject(Status status){
		JPanel jpanel = createObject(status);
		jpanel.updateUI();
		if (this.panel.getComponentCount() == 1000) this.panel.remove(999);
		this.panel.add(jpanel, 0);
		this.panel.updateUI();
		last = jpanel;
	}

	@Override
	public Component getComponent()
	{
		return this.component;
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
}
