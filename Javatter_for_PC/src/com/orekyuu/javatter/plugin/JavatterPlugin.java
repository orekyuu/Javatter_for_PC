package com.orekyuu.javatter.plugin;

import java.io.File;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.orekyuu.javatter.account.TwitterManager;
import com.orekyuu.javatter.controller.MainWindowController;
import com.orekyuu.javatter.controller.UserStreamController;
import com.orekyuu.javatter.util.SaveData;
import com.orekyuu.javatter.util.SaveDataManager;
import com.orekyuu.javatter.view.IJavatterTab;
import com.orekyuu.javatter.view.MainWindowView;
import com.orekyuu.javatter.view.PluginDefaultConfigView;

public abstract class JavatterPlugin
{
	/**
	 * JavatterのWindow
	 */
	private MainWindowView view;
	/**
	 * JavatterのWindow操作クラス
	 */
	private MainWindowController controller;

	/**
	 * ログインしているユーザーのTwitterインスタンス
	 */
	protected Twitter twitter;
	/**
	 * ログインしているユーザー名
	 */
	protected String userName;

	/**
	 * セーブデータの補助クラス
	 */
	private SaveData savedata;
	/**
	 * プラグインのセーブディレクトリ
	 */
	private File dir;
	/**
	 * プラグインのコンフィグ画面
	 */
	private IJavatterTab configTab;

	public JavatterPlugin(){
		twitter=TwitterManager.getInstance().getTwitter();
		try {
			userName=twitter.getScreenName();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		dir=new File("SaveData/"+getPluginName());
		registerSaveData();
		configTab=getPluginConfigViewObserver();
	}

	/**
	 * セーブデータを登録
	 */
	private void registerSaveData(){
		savedata=new SaveData(getPluginName(),dir);
		SaveDataManager.getInstance().registerSaveData(savedata);
	}

	/**
	 * このプラグインのセーブフォルダのFileを返す
	 * @return
	 */
	protected File getSaveDir(){
		return dir;
	}

	/**
	 * このプラグインのセーブデータ補助クラスを返す
	 * @return
	 */
	protected SaveData getSaveData(){
		return savedata;
	}

	/**
	 * コンフィグ描画クラスを返す
	 * @return
	 */
	protected IJavatterTab getPluginConfigView(){
		return configTab;
	}

	/**
	 * Javatter描画クラスと操作クラスを設定
	 * @param view
	 * @param controller
	 */
	public void setMainViewAndController(MainWindowView view, MainWindowController controller)
	{
		this.view = view;
		this.controller = controller;
	}

	/**
	 * プラグインを初期化
	 */
	public final void initPlugin() {
		init();
	}

	/**
	 * コンフィグ用のViewを返します。<br>
	 * コンフィグを作りたい場合はオーバーライドして独自のConfigViewを返すようにしてください
	 * @return
	 */
	protected IJavatterTab getPluginConfigViewObserver(){
		return new PluginDefaultConfigView();
	}

	/**
	 * Javatterが開始するときに動きます
	 */
	public abstract void init();

	/**
	 * プラグインの名前を設定してください
	 */
	public abstract String getPluginName();

	/**
	 * プラグインのバージョンを設定してください
	 * @return
	 */
	public abstract String getVersion();

	/**
	 * プラグインリストでのツールチップに表示する内容<br>
	 * nullの場合はツールチップを表示しない
	 * @return
	 */
	public String getHelpMessage(){
		return null;
	}

	/**
	 * Javatter左側のユーザーストリーム用のタブに新しく追加します
	 * @param title タブに表示される名前
	 * @param tab 表示するタブ
	 */
	public void addUserStreamTab(String title, IJavatterTab tab)
	{
		this.view.addUserStreamTab(title, tab);
	}

	/**
	 * Javatter右側のメニュータブに新しくタブを追加します
	 * @param title タブに表示される名前
	 * @param tab 表示するタブ
	 */
	public void addMenuTab(String title, IJavatterTab tab)
	{
		this.view.addMenuTab(title, tab);
	}

	/**
	 * ユーザーストリームを受け取るためのコントローラを登録します
	 * @param userstream ユーザーストリームを受け取るためのコントローラ
	 */
	public void addUserStreamListener(UserStreamController userstream)
	{
		this.controller.getUserStream().addUserStreamController(userstream);
	}

	/**
	 * ツイート時のイベントのリスナを登録
	 * @param listener
	 */
	public void addTweetListener(ITweetListener listener){
		this.controller.addTweetListener(listener);
	}

	/**
	 * ツイート内容のオブジェクトに変更を加えるクラスを登録
	 * @param builder
	 */
	public void addTweetObjectBuider(TweetObjectBuilder builder){
		this.controller.addTweetObjectBuilder(builder);
	}

	/**
	 * Javatterを表示しているViewを返す
	 * @return
	 */
	protected MainWindowView getMainView(){
		return view;
	}
}