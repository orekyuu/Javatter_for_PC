package com.orekyuu.javatter.util;

import java.io.File;

import javax.swing.ImageIcon;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.orekyuu.javatter.main.Main;
import com.orekyuu.javatter.view.MainWindowView;

/**
 * Twitterでのアクションを補助するクラス
 * @author orekyuu
 *
 */
public class TwitterUtil {

	private long replyID;
	private File file;

	/**
	 * 設定されたデータを使ってつぶやきます
	 * @param twitter Twitterインスタンス
	 * @param message つぶやく内容
	 * @throws TwitterException
	 */
	public void tweet(final Twitter twitter,final String message) throws TwitterException{
		if(message.length()==0)return;
		Thread th=new Thread(){
			@Override
			public void run(){
				boolean isComplete = true;
				try {
					StatusUpdate update=new StatusUpdate(message);
					if(replyID!=-1){
						update.setInReplyToStatusId(replyID);
						replyID=-1;
					}

					if(file!=null){
						update.media(file);
						file=null;
					}
					twitter.updateStatus(update);
				} catch (TwitterException e) {
					e.printStackTrace();
					if (e.getErrorCode() == 187) {
						// ツイート重複
						Main.getMainView().setStatus(
								new ImageIcon(ImageManager.getInstance().getImage("status_error")),
								"ツイートが重複しています");
					}
					isComplete = false;
				}
				if (isComplete) {

					Main.getMainView().setStatus(
							new ImageIcon(ImageManager.getInstance().getImage("status_apply")),
							"ツイートに成功しました : " + message);
				}
			}
		};
		th.start();
	}

	/**
	 * 指定されたStatusをリツイートします
	 * @param twitter Twitterインスタンス
	 * @param status リツイートするつぶやき
	 * @throws TwitterException
	 */
	public void rt(final Twitter twitter,final Status status) throws TwitterException{
		Thread th=new Thread(){
			@Override
			public void run(){
				try {
					if(canRetweet(status))
						twitter.retweetStatus(status.getId());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}

			private boolean canRetweet(Status status) throws IllegalStateException, TwitterException{
				if(status.getUser().getScreenName().equals(twitter.getScreenName()))
					return false;
				if(status.isRetweet()){
					return canRetweet(status.getRetweetedStatus());
				}
				return true;
			}
		};
		th.start();
	}

	/**
	 * リプライ先を設定
	 * @param status リプライ先のつぶやき
	 */
	public void setReplyID(Status status){
		replyID = status.getId();
	}

	/**
	 * お気に入りに追加します
	 * @param twitter Twitterインスタンス
	 * @param status お気に入りに登録するつぶやき
	 */
	public void fav(final Twitter twitter,final Status status){
		Thread th=new Thread(){
			@Override
			public void run(){
				try {
					twitter.createFavorite(status.getId());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

	/**
	 * つぶやきに画像を添付する
	 * @param image 添付したい画像のパス
	 */
	public void setImage(File image){
		file=image;
	}

	/**
	 * お気に入りから削除します
	 * @param twitter
	 * @param status
	 */
	public void unfav(final Twitter twitter,final Status status) {
		Thread th=new Thread(){
			@Override
			public void run(){
				try {
					twitter.destroyFavorite(status.getId());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

}
