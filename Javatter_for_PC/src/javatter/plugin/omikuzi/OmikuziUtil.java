package javatter.plugin.omikuzi;

import java.util.Random;

import twitter4j.Status;
import twitter4j.TwitterException;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.util.SaveData;
import com.orekyuu.javatter.util.TwitterUtil;

public class OmikuziUtil {

	private String user;

	private TwitterUtil util;
	private SaveData data;

	public OmikuziUtil(SaveData data){
		try {
			this.data=data;
			user=TwitterManager.getInstance().getTwitter().getScreenName();
			util=new TwitterUtil();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private String omikuziMessage(){
		Random rand=new Random();
		String[] list=data.getString("OmikuziText").split("\n");
		int i=rand.nextInt(list.length);
		return list[i];
	}

	public void tweet(Status status){
		if(data.getBoolean("omikuzi")){
			if(status.getText().startsWith("@"+user)&&status.getText().indexOf("おみくじ")!=-1){
				util.setReplyID(status);
				try {
					util.tweet(TwitterManager.getInstance().getTwitter(), "@"+status.getUser().getScreenName()+" "+omikuziMessage());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
