package com.orekyuu.javatter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * セーブデータ管理クラス
 * @author orekyuu
 *
 */
public class SaveDataManager{
	private static SaveDataManager instance=new SaveDataManager();
	private Map<String,SaveData> data=new HashMap<String, SaveData>();

	private SaveDataManager(){
	}

	/**
	 * セーブデータを初期化
	 */
	public void init(){
		try {
			loadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * セーブデータを登録
	 * すでに存在している場合は上書きしません
	 * @param saveData
	 */
	public void registerSaveData(SaveData saveData){
		if(data.get(saveData.getDataName())==null){
			data.put(saveData.getDataName(), saveData);
			saveData.load();
		}
	}

	/**
	 * マネージャのインスタンスを取得
	 * @return
	 */
	public static SaveDataManager getInstance(){
		return instance;
	}

	/**
	 * 指定のプラグインのセーブデータを取得
	 * @param key
	 * @return
	 */
	public SaveData getSaveData(String key){
		return data.get(key);
	}

	private void loadAll() throws Exception{
		for(Entry<String, SaveData> e:data.entrySet()){
			e.getValue().load();
		}
	}

	public void saveAll() throws Exception{
		for(Entry<String, SaveData> e:data.entrySet()){
			e.getValue().save();
		}
	}
}
