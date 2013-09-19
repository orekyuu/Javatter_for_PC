package com.orekyuu.javatter.util;

import java.io.File;

/**
 * Javatterのコンフィグクラス
 * @author orekyuu
 *
 */
public class JavatterConfig {

	private static JavatterConfig instance=new JavatterConfig();

	private SaveData savedata;

	private JavatterConfig(){

	}

	/**
	 * コンフィグを初期化
	 */
	public void init(){
		savedata=new SaveData("JavatterConfig", new File("SaveData/JavatterConfig"));
		SaveDataManager.getInstance().registerSaveData(savedata);
		savedata.setDefaultValue("JavaBeamRT", false);
		savedata.setDefaultValue("thanks", false);
		savedata.setDefaultValue("isLoad", true);
		savedata.setDefaultValue("useTaskbar", true);
		savedata.setDefaultValue("useLocalCache", true);
	}

	/**
	 * インスタンスを返す
	 * @return
	 */
	public static JavatterConfig getInstance(){
		return instance;
	}

	/**
	 * JavaビームRTのフラグを設定する
	 * @param flag
	 */
	public void setJavaBeamRT(boolean flag){
		savedata.setBoolean("JavaBeamRT", flag);
	}

	/**
	 * JavaビームRTの設定を返す
	 * @return
	 */
	public boolean getJavaBeamRT(){
		return savedata.getBoolean("JavaBeamRT");
	}

	/**
	 * お礼のフラグを設定する
	 * @param flag
	 */
	public void setThanks(boolean flag){
		savedata.setBoolean("thanks", flag);
	}

	/**
	 * お礼の設定を返す
	 * @return
	 */
	public boolean getThanks(){
		return savedata.getBoolean("thanks");
	}


	/**
	 * タスクバーのフラグを設定する
	 * @param flag
	 */
	public void setUseTaskbar(boolean flag){
		savedata.setBoolean("useTaskbar", flag);
	}

	/**
	 * タスクバーの設定を返す
	 * @return
	 */
	public boolean getUseTaskbar(){
		return savedata.getBoolean("useTaskbar");
	}

	/**
	 * ローカルキャッシュのフラグを設定する
	 * @param flag
	 */
	public void setUseLocalCache(boolean flag){
		savedata.setBoolean("useLocalCache", flag);
	}

	/**
	 * ローカルキャッシュの設定を返す
	 * @return
	 */
	public boolean getUseLocalCache(){
		return savedata.getBoolean("useLocalCache");
	}
}
