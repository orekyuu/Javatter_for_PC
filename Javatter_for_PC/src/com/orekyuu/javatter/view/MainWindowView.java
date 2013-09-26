package com.orekyuu.javatter.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.controller.MainWindowController;
import com.orekyuu.javatter.main.Main;
import com.orekyuu.javatter.util.ImageManager;
import com.orekyuu.javatter.util.ImageUploader;
import com.orekyuu.javatter.util.TwitterUtil;
import com.orekyuu.javatter.viewobserver.ImagePreviewViewObserber;
import com.orekyuu.javatter.viewobserver.TweetViewObserver;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;

/**
 * メインウィンドウ描画クラス
 * @author orekyuu
 *
 */
public class MainWindowView implements TweetViewObserver, ActionListener, UserEventViewObserver, ImagePreviewViewObserber
{
	private JFrame window;
	private JButton tweetButton;
	private JButton javaButton;
	private JTextArea textArea;
	private JLabel image;
	private MainWindowController tweetController;
	private JTabbedPane tab;
	private JTabbedPane menuTab;
	private TwitterUtil util;

	/* ******** ステータスバー ******** */
	/** ステータスバーパネル. */
	private JPanel statusBarPanel;

	/** ステータスバーアイコン. */
	private static JLabel statusBarIcon;

	/** ステータスバーテキスト. */
	private static JLabel statusBarText;

	/**
	 * ツイートを書くためのテキストエリアを返す
	 * @return
	 */
	public JTextArea getTweetTextArea(){
		return textArea;
	}

	/**
	 * Javatterのフレームを返す
	 * @return
	 */
	public JFrame getMainFrame(){
		return window;
	}

	/**
	 * 新しいWindowを作成する
	 * @throws HeadlessException
	 * @throws IllegalStateException
	 * @throws TwitterException
	 */
	public void create() throws HeadlessException, IllegalStateException, TwitterException
	{
		this.util = new TwitterUtil();

		Toolkit.getDefaultToolkit().setDynamicLayout(true);

		this.window = new JFrame();
		setTitle("未認証");
		this.window.setSize(700, 600);
		this.window.setIconImage(ImageManager.getInstance().getImage("javatter"));
		this.window.setDefaultCloseOperation(3);
		this.window.setResizable(false);

		Container container = this.window.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel tweetPanel = new JPanel();
		tweetPanel.setLayout(new BorderLayout());

		this.textArea = new JTextArea(5, 40);
		this.textArea.setLineWrap(true);
		this.textArea.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e)
			{
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if ((e.getModifiersEx() & 0x80) != 0) {
					if (e.getKeyCode() == 10) {
						MainWindowView.this.tweetButton.doClick();
					}
					if (e.getKeyCode() == 74) {
						MainWindowView.this.javaButton.doClick();
					}
				}
			}
		});
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(this.textArea, "Center");
		this.tweetButton = new JButton("Tweet");
		this.tweetButton.addActionListener(this);
		p.add(this.tweetButton, "South");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		Image img = ImageManager.getInstance().getImage("preview");
		this.image = new JLabel(new ImageIcon(img));
		image.addMouseListener(new MouseListener() {

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
				clear();
			}
		});
		new DropTarget(this.image, new ImageUploader(this.util, this));
		buttonPanel.add(this.image, "Center");
		this.javaButton = new JButton("Javaビーム");
		this.javaButton.addActionListener(this);
		buttonPanel.add(this.javaButton, "South");

		tweetPanel.add(p, "Center");
		tweetPanel.add(buttonPanel, "After");

		this.tab = new JTabbedPane();

		this.menuTab = new JTabbedPane();
		this.menuTab.setPreferredSize(new Dimension(250, 600));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(this.tab, "Center");
		mainPanel.add(tweetPanel, "First");
		mainPanel.setBorder(new BevelBorder(0));

		JPanel menuPanel = new JPanel();
		menuPanel.add(this.menuTab);
		menuPanel.setBorder(new BevelBorder(0));

		statusBarPanel = new JPanel();
		statusBarPanel.setLayout(new BorderLayout());
		statusBarPanel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { }
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseReleased(MouseEvent e) {
				if (-1 < e.getX() && -1 < e.getY()
						&& e.getX() < e.getComponent().getWidth()
						&& e.getY() < e.getComponent().getHeight()) {
					new StatusLogView(window);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) { }

		});
		statusBarIcon = new JLabel((Icon) null, JLabel.CENTER);
		statusBarIcon.setPreferredSize(new Dimension(21, 21));
		statusBarText = new JLabel();
		statusBarText.setFont(new Font("Meiryo UI", Font.PLAIN, 12));
		statusBarPanel.add(statusBarIcon, BorderLayout.WEST);
		statusBarPanel.add(statusBarText, BorderLayout.CENTER);

		container.add(mainPanel, "Center");
		container.add(this.menuTab, "After");
		container.add(statusBarPanel, "Last");

		this.window.setVisible(true);
	}

	/**
	 * ユーザーストリームタブをクリアする
	 */
	public void clearTab() {
		this.tab.removeAll();
	}

	/**
	 * ユーザーストリームタブに新規タブを追加
	 * @param title
	 * @param jTab
	 */
	public void addUserStreamTab(String title, IJavatterTab jTab)
	{
		this.tab.addTab(title, jTab.getComponent());
	}

	/**
	 * メニュータブに新規タブを追加
	 * @param title
	 * @param jTab
	 */
	public void addMenuTab(String title, IJavatterTab jTab)
	{
		this.menuTab.addTab(title, jTab.getComponent());
	}

	public void actionPerformed(ActionEvent arg0)
	{
		if (arg0.getSource().equals(this.tweetButton)) {
			try {
				this.tweetController.onTweet(this.textArea.getText(), this.util);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			this.textArea.setText("");
			MainWindowView.this.clear();
		}
		if (arg0.getSource().equals(this.javaButton)) {
			try {
				this.tweetController.shotJavaBeam(this.textArea.getText());
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			this.textArea.setText("");
			MainWindowView.this.clear();
		}
	}

	/**
	 * コントローラを設定
	 */
	public void setTweetController(MainWindowController controller)
	{
		this.tweetController = controller;
	}

	/**
	 * ユーザーイベントを発生させる
	 */
	public void onUserEvent(String type, Status status)
	{
		Twitter twitter = TwitterManager.getInstance().getTwitter();
		if (type.equals("reply")) {
			StringBuilder builder = new StringBuilder();
			builder.append(this.textArea.getText());
			builder.append("@");
			builder.append(status.getUser().getScreenName());
			builder.append(" ");
			this.textArea.setText(builder.toString());
			this.util.setReplyID(status);
		} else if (type.equals("rt")) {
			try {
				this.util.rt(twitter, status);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else if (type.equals("fav")) {
			this.util.fav(twitter, status);
		} else if(type.equals("unfav")){
			this.util.unfav(twitter, status);
		} else {
			throw new IllegalArgumentException("タイプ:" + type + "は存在していません");
		}
	}

	/**
	 * タイトルを設定
	 * @param screenName ログインしているアカウント名
	 */
	public void setTitle(String screenName)
	{
		this.window.setTitle("Javatter(" + screenName + ") #"+Main.getJavatterVersion());
	}

	/**
	 * プレビューに画像を設定する
	 */
	public void change(File file)
	{
		try {
			Image img = ImageIO.read(file).getScaledInstance(100, 100, 4);
			this.image.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * プレビューをクリアする
	 */
	public void clear()
	{
		Image img = ImageManager.getInstance().getImage("preview").getScaledInstance(100, 100, 4);
		this.image.setIcon(new ImageIcon(img));
		util.setImage(null);
	}

	/**
	 * ステータスバーにアイコンとテキストをセットする.
	 * デフォルトで用意されている標準アイコンの他21x21px以内のアイコンを使用可能.<br />
	 * 標準アイコンは<code>com.orekyuu.javatter.util.ImageManager</code>から取得できる.<br />
	 * 成功アイコン:<code>"status_apply"</code> エラーアイコン:<code>"status_error"</code>
	 * @param icon アイコン<br />
	 * @param text テキスト
	 */
	public void setStatus(ImageIcon icon, String text) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm.ss - ");
		Date date = new Date();
		text = format.format(date) + text;

		statusBarIcon.setIcon(icon);
		statusBarText.setText(" " + text);
		StatusLogView.addLog(text);

		/* アイコンを5秒でクリアする,つもりだったがなんか違う感があるのでコメントアウト
		Timer timer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statusBarIcon.setIcon(null);
			}
		});
		timer.setRepeats(false);
		timer.start();
		*/
	}
}