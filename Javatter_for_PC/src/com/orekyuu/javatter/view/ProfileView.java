package com.orekyuu.javatter.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.controller.ProfileController;
import com.orekyuu.javatter.main.Main;
import com.orekyuu.javatter.model.ProfileModel;
import com.orekyuu.javatter.plugin.JavatterPluginLoader;
import com.orekyuu.javatter.plugin.JavatterProfileBuilder;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.util.IconCache;
import com.orekyuu.javatter.util.TweetObjectFactory;
import com.orekyuu.javatter.viewobserver.ProfileViewObserver;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;

/**
 * プロフィールウィンドウ描画クラス
 * @author orekyuu
 *
 */
public class ProfileView implements ProfileViewObserver,AdjustmentListener, UserEventViewObserver, ActionListener{

	private JDialog frame;
	private JTabbedPane tab;
	private JPanel userInfoPanel;
	private JPanel timelinePanel;
	private JPanel followPanel;
	private JPanel followerPanel;
	private ProfileController controller;

	private JScrollPane followScroll;
	private JScrollPane followerScroll;
	private List<TweetObjectBuilder> builders;
	private User user;


	public ProfileView(List<TweetObjectBuilder> builders) {
		this.builders=builders;
	}

	@Override
	public void userUpdate(User user) {
		this.user=user;
		userInfoPanel.add(createProfilePanel(user));
		userInfoPanel.updateUI();
	}

	@Override
	public void create() {
		frame=new JDialog(new JFrame(),"プロフィール");
		frame.setSize(390, 500);
		tab=new JTabbedPane();
		frame.getContentPane().add(tab);

		userInfoPanel=new JPanel();
		addTab("User Info", userInfoPanel);

		timelinePanel=new JPanel();
		timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
		JScrollPane timelineScroll=new JScrollPane(22, 31);
		timelineScroll.setViewportView(timelinePanel);
		timelineScroll.getVerticalScrollBar().setUnitIncrement(20);
		addTab("Timeline", timelineScroll);

		followPanel=new JPanel();
		followPanel.setLayout(new BoxLayout(followPanel, BoxLayout.Y_AXIS));
		followScroll=new JScrollPane(22, 31);
		followScroll.getVerticalScrollBar().addAdjustmentListener(this);
		followScroll.setViewportView(followPanel);
		followScroll.getVerticalScrollBar().setUnitIncrement(20);
		addTab("Following", followScroll);

		followerPanel=new JPanel();
		followerPanel.setLayout(new BoxLayout(followerPanel, BoxLayout.Y_AXIS));
		followerScroll=new JScrollPane(22, 31);
		followerScroll.getVerticalScrollBar().addAdjustmentListener(this);
		followerScroll.setViewportView(followerPanel);
		followerScroll.getVerticalScrollBar().setUnitIncrement(20);
		addTab("Follower", followerScroll);

		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void addTab(String title,JComponent component){
		tab.addTab(title, component);
	}


	@Override
	public void timeLineUpdate(Queue<Status> timeline) {
		timelinePanel.removeAll();
		while(!timeline.isEmpty()){
			TweetObjectFactory factory=new TweetObjectFactory(timeline.poll(), builders);
			timelinePanel.add(factory.createTweetObject(this).getComponent());
		}
	}

	@Override
	public void followUserUpdate(Queue<User> user) {
		while(!user.isEmpty()){
			followPanel.add(createUserObject(user.poll()));
			followPanel.updateUI();
		}
	}

	public Queue<User> createUserList(Queue<Long> queue) throws TwitterException{
		Twitter twitter=TwitterManager.getInstance().getTwitter();
		int count=(int) (Math.floor(queue.size()/100));
		Queue<User> user=new LinkedList<User>();
		for(int i=0;i<count;i++){
			long list[]=new long[queue.size()>100?100:queue.size()];
			for(int j=0;j<list.length;j++){
				list[j]=queue.poll();
			}
			user.addAll(twitter.lookupUsers(list));
		}
		return user;
	}


	@Override
	public void followerUserUpdate(Queue<User> user) {
		while(!user.isEmpty()){
			followerPanel.add(createUserObject(user.poll()));
			followerPanel.updateUI();
		}
	}

	@Override
	public void setController(ProfileController controller) {
		this.controller=controller;
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if(e.getValue()==0)return ;
		if(e.getSource()==followerScroll.getVerticalScrollBar()&&e.getValue()==(followerPanel.getHeight()-followerScroll.getViewport().getHeight())){
			controller.loadFollower(50);
		}
		if(e.getSource()==followScroll.getVerticalScrollBar()&&e.getValue()==(followPanel.getHeight()-followScroll.getViewport().getHeight())){
			controller.loadFollow(50);
		}
	}

	@Override
	public void onUserEvent(String type, Status status) {
		MainWindowView view=Main.getMainView();
		view.onUserEvent(type, status);
	}

	private JPanel createProfilePanel(final User user){
		try{
			JPanel panel=new JPanel();
			panel.setBackground(BackGroundColor.color);
			panel.setLayout(new BorderLayout());

			JPanel pageStart=new JPanel();
			pageStart.setBackground(BackGroundColor.color);
			pageStart.setLayout(new BoxLayout(pageStart, BoxLayout.LINE_AXIS));
			Image img=IconCache.getInstance().getIcon(new URL(user.getProfileImageURL().replaceAll("_normal", ""))).getImage().getScaledInstance(64, -1, 3);
			JLabel icon=new JLabel(new ImageIcon(img));
			pageStart.add(icon);

			JPanel info=new JPanel();
			info.setBackground(BackGroundColor.color);
			info.setLayout(new BoxLayout(info,BoxLayout.PAGE_AXIS));
			String space="                    ";
			info.add(new JLabel(space+"フォロー数："+user.getFriendsCount()));
			info.add(new JLabel(space+"フォロワー数："+user.getFollowersCount()));
			info.add(new JLabel(space+"お気に入り数："+user.getFavouritesCount()));
			pageStart.add(info);

			panel.add(pageStart,BorderLayout.PAGE_START);

			JPanel center=new JPanel();
			center.setLayout(new BoxLayout(center,BoxLayout.PAGE_AXIS));
			{
				JPanel p=new JPanel();
				p.setLayout(new BorderLayout());
				p.add(new JLabel("ユーザー名"),BorderLayout.LINE_START);
				p.add(new JLabel(user.getName()),BorderLayout.LINE_END);
				center.add(p);
			}

			{
				JPanel p=new JPanel();
				p.setLayout(new BorderLayout());
				p.add(new JLabel("アカウント"),BorderLayout.LINE_START);
				JLabel l=new JLabel("<html><a href=https://twitter.com/"+user.getScreenName()+">@"+user.getScreenName()+"</a></html>");
				l.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {

					}

					@Override
					public void mousePressed(MouseEvent e) {

					}

					@Override
					public void mouseExited(MouseEvent e) {

					}

					@Override
					public void mouseEntered(MouseEvent e) {

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Desktop.getDesktop().browse(new URL("https://twitter.com/"+user.getScreenName()).toURI());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				l.setCursor(new Cursor(Cursor.HAND_CURSOR));
				p.add(l,BorderLayout.LINE_END);
				center.add(p);
			}
			{
				JTextArea area=new JTextArea(user.getDescription());
				area.setBackground(BackGroundColor.color);
				area.setEditable(false);
				area.setLineWrap(true);
				center.add(area);
			}
			panel.add(center,BorderLayout.CENTER);

			Twitter twitter=TwitterManager.getInstance().getTwitter();
			boolean flag=twitter.showFriendship(twitter.getScreenName(), user.getScreenName()).isSourceFollowingTarget();
			JToggleButton follow=new JToggleButton(flag?"フォロー中":"フォローする", flag);
			follow.addActionListener(this);
			panel.add(follow,BorderLayout.PAGE_END);

			for(JavatterProfileBuilder b:JavatterPluginLoader.getProfileBuilder()){
				b.createdProfilePanel(panel,user);
			}
			return panel;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private JPanel createUserObject(final User user){
		try {
			JPanel panel=new JPanel();
			panel.setBackground(BackGroundColor.color);
			panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
			panel.setAlignmentX(0.0F);
			panel.setAlignmentY(0.0F);
			panel.setMaximumSize(new Dimension(375, 2147483647));

			Image img=IconCache.getInstance().getIcon(new URL(user.getProfileImageURL())).getImage().getScaledInstance(48, -1, 3);
			JLabel label=new JLabel(new ImageIcon(img));
			label.setAlignmentX(0);
			label.setAlignmentY(0);
			label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			label.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseExited(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseClicked(MouseEvent e) {
					ProfileView view=new ProfileView(builders);
					ProfileModel model=new ProfileModel();
					ProfileController controller=new ProfileController(user);
					view.setController(controller);
					model.setView(view);
					controller.setModel(model);
					controller.setView(view);

					controller.init();
				}
			});
			panel.add(label);

			StringBuilder builder=new StringBuilder();
			builder.append("@");
			builder.append(user.getScreenName());
			builder.append(" ");
			builder.append(user.getName());
			builder.append("\n");
			builder.append(user.getDescription());
			JTextArea area=new JTextArea(builder.toString());
			area.setAlignmentX(0);
			area.setAlignmentY(0);
			area.setBackground(BackGroundColor.color);
			area.setLineWrap(true);
			area.setEditable(false);
			panel.add(area);

			for(JavatterProfileBuilder b:JavatterPluginLoader.getProfileBuilder()){
				b.createdFollowObjectPanel(panel,user);
			}
			return panel;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JToggleButton button=(JToggleButton) arg0.getSource();
		Twitter twitter=TwitterManager.getInstance().getTwitter();
		if(button.isSelected()){
			button.setText("フォロー中");
			try {
				twitter.createFriendship(user.getId());
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}else{
			button.setText("フォローする");
			try {
				twitter.destroyFriendship(user.getId());
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

}
