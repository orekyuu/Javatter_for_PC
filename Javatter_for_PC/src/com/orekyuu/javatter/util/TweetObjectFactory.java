package com.orekyuu.javatter.util;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import twitter4j.Status;
import twitter4j.User;

import com.orekyuu.javatter.controller.ProfileController;
import com.orekyuu.javatter.model.ProfileModel;
import com.orekyuu.javatter.model.UserEventListener;
import com.orekyuu.javatter.plugin.TweetObjectBuilder;
import com.orekyuu.javatter.view.ProfileView;
import com.orekyuu.javatter.viewobserver.UserEventViewObserver;

public class TweetObjectFactory
{
	private Status status;
	private List<TweetObjectBuilder> builders;

	public TweetObjectFactory(Status status,List<TweetObjectBuilder> builders)
	{
		this.status = status;
		this.builders=builders;
	}

	public JPanel createTweetObject(UserEventViewObserver view) {
		JPanel base = new JPanel();
		base.setBackground(BackGroundColor.color);
		base.setAlignmentX(0.0F);
		base.setAlignmentY(0.0F);
		base.setMaximumSize(new Dimension(375, 2147483647));
		base.setLayout(new BorderLayout());

		base.add(createImage(), "Before");
		JPanel text = createText(view);
		base.add(text, "Center");

		for(TweetObjectBuilder b:builders){
			b.createdObjectPanel(base, status);
		}

		return base;
	}

	private JPanel createImage()
	{
		JPanel panel = new JPanel();
		panel.setBackground(BackGroundColor.color);
		panel.setLayout(new BoxLayout(panel, 3));
		MediaTracker tracker = new MediaTracker(panel);
		try {
			if (this.status.isRetweet()) {
				Status rt = this.status.getRetweetedStatus();
				panel.add(createImageLabel(rt, tracker, 48));
				panel.add(createImageLabel(status, tracker, 20));
			} else {
				panel.add(createImageLabel(status, tracker, 48));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		panel.setAlignmentX(0.0F);
		panel.setAlignmentY(0.0F);
		for(TweetObjectBuilder b:builders){
			b.createdImagePanel(panel, status);
		}

		return panel;
	}

	private JLabel createImageLabel(final Status status,MediaTracker tracker,int size) throws MalformedURLException{
		IconCache cache = IconCache.getInstance();
		ImageIcon icon = cache.getIcon(new URL(status.getUser().getProfileImageURL()));
		Image img = size==48?icon.getImage():icon.getImage().getScaledInstance(size, -1, 4);

		JLabel label=new JLabel(new ImageIcon(img));
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
				ProfileController controller=new ProfileController(status.getUser());
				view.setController(controller);
				model.setView(view);
				controller.setModel(model);
				controller.setView(view);

				controller.init();
			}
		});
		return label;
	}

	private JPanel createText(UserEventViewObserver view) {
		JPanel textPanel = new JPanel();
		textPanel.setMaximumSize(new Dimension(375, 2147483647));
		textPanel.setLayout(new BoxLayout(textPanel, 3));

		User user = this.status.isRetweet() ? this.status.getRetweetedStatus().getUser() : this.status.getUser();
		JLabel userName = new JLabel();
		userName.setMaximumSize(new Dimension(275, 2147483647));
		Font font = new Font("ＭＳ ゴシック", 1, 13);
		userName.setFont(font);
		userName.setText("@" + user.getScreenName() + " " + user.getName());
		textPanel.add(userName);

		String tweetText = this.status.isRetweet() ? this.status.getRetweetedStatus().getText() : this.status.getText();

		JTextPane textArea = new JTextPane();

		textArea.setContentType("text/html");
		textArea.setEditable(false);
		textArea.setText(createHTMLText(tweetText));
		textArea.setBackground(BackGroundColor.color);
		textArea.setAlignmentX(0.0F);
		textArea.setAlignmentY(0.0F);
		textArea.addHyperlinkListener(new HyperlinkListener()
		{
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					URL url = e.getURL();
					Desktop dp = Desktop.getDesktop();
					try {
						dp.browse(url.toURI());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		textPanel.add(textArea);

		textPanel.add(createButtons(view));
		textPanel.setAlignmentY(0.0F);
		textPanel.setAlignmentX(0.0F);
		textPanel.setBackground(BackGroundColor.color);

		for(TweetObjectBuilder b:builders){
			b.createdTextAreaPanel(textPanel, status);
		}


		return textPanel;
	}

	private String createHTMLText(String tweet) {
		String urlRegex = "(?<![\\w])https?://(([\\w]|[^ -~])+(([\\w\\-]|[^ -~])+([\\w]|[^ -~]))?\\.)+(aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|dd|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|ss|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|za|zm|zw)(?![\\w])(/([\\w\\.\\-\\$&%/:=#~!]*\\??[\\w\\.\\-\\$&%/:=#~!]*[\\w\\-\\$/#])?)?";
		Pattern p = Pattern.compile(urlRegex);
		Matcher matcher = p.matcher(tweet);
		String t = tweet;
		while (matcher.find()) {
			String s = matcher.group();
			t = t.replaceFirst(s, "<a href='" + s + "'>" + s + "</a>");
		}
		t = t.replaceAll("\n", "<br>");
		return t;
	}

	private JPanel createButtons(UserEventViewObserver view) {
		Status s=status.isRetweet()?status.getRetweetedStatus():status;
		UserEventListener model = new UserEventListener(this.status, view);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 2));

		JButton rep = new JButton("リプ");
		rep.addActionListener(model);
		model.setReplyButton(rep);
		panel.add(rep);

		JToggleButton rt = new JToggleButton("RT");
		rt.addActionListener(model);
		model.setRtButton(rt);
		rt.setEnabled(!s.isRetweetedByMe());
		panel.add(rt);

		JToggleButton fav = new JToggleButton("☆");
		fav.addActionListener(model);
		fav.setSelected(s.isFavorited());
		model.setFavButton(fav);
		panel.add(fav);

		panel.setAlignmentX(0.0F);
		panel.setAlignmentY(0.0F);

		for(TweetObjectBuilder b:builders){
			b.createdButtonPanel(panel, status);
		}

		return panel;
	}
}