package com.orekyuu.javatter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * セーブデータを表すクラス
 * @author orekyuu
 *
 */
public class SaveData{

	private Map<String,Integer> intMap;
	private Map<String,Float> floatMap;
	private Map<String,String> stringMap;
	private Map<String,Boolean> booleanMap;

	private String dataName;
	private File dir;
	private File file;

	public SaveData(String name,File dir){
		dataName=name;
		this.dir=dir;
		this.file=new File(dir,dataName+".dat");
	}

	protected String getDataName(){
		return dataName;
	}

	/**
	 * セーブデータのデフォルト値を設定します。<br>
	 * 指定のキーに対する値がない場合は第二引数の値で保存されます<br>
	 * JavatterPluginのinitメソッド内で必ず呼び出してください。<br>
	 * @param key
	 * @param value
	 */
	public void setDefaultValue(String key,int value){
		if(intMap.get(key)==null)intMap.put(key, value);
	}

	/**
	 * セーブデータのデフォルト値を設定します。<br>
	 * 指定のキーに対する値がない場合は第二引数の値で保存されます<br>
	 * JavatterPluginのinitメソッド内で必ず呼び出してください。<br>
	 * @param key
	 * @param value
	 */
	public void setDefaultValue(String key,float value){
		if(floatMap.get(key)==null)floatMap.put(key, value);
	}

	/**
	 * セーブデータのデフォルト値を設定します。<br>
	 * 指定のキーに対する値がない場合は第二引数の値で保存されます<br>
	 * JavatterPluginのinitメソッド内で必ず呼び出してください。<br>
	 * @param key
	 * @param value
	 */
	public void setDefaultValue(String key,String value){
		if(stringMap.get(key)==null)stringMap.put(key, value);
	}

	/**
	 * セーブデータのデフォルト値を設定します。<br>
	 * 指定のキーに対する値がない場合は第二引数の値で保存されます<br>
	 * JavatterPluginのinitメソッド内で必ず呼び出してください。<br>
	 * @param key
	 * @param value
	 */
	public void setDefaultValue(String key,boolean value){
		if(booleanMap.get(key)==null)booleanMap.put(key, value);
	}

	/**
	 * int型のデータを保存します
	 * @param key
	 * @param value
	 */
	public void setInt(String key,int value){
		intMap.put(key, value);
		save();
	}

	/**
	 * int型のデータを取り出します
	 * @param key
	 * @param value
	 */
	public int getInt(String key){
		return intMap.get(key);
	}

	/**
	 * float型のデータを保存します
	 * @param key
	 * @param value
	 */
	public void setFloat(String key,float value){
		floatMap.put(key, value);
		save();

	}

	/**
	 * float型のデータを取り出します
	 * @param key
	 * @param value
	 */
	public float getFloat(String key){
		return floatMap.get(key);
	}

	/**
	 * String型のデータを保存します
	 * @param key
	 * @param value
	 */
	public void setString(String key,String value){
		stringMap.put(key, value);
		save();
	}

	/**
	 * String型のデータを取り出します
	 * @param key
	 * @param value
	 */
	public String getString(String key){
		return stringMap.get(key);
	}

	/**
	 * boolean型のデータを保存します
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key,boolean value){
		booleanMap.put(key, value);
		save();
	}

	/**
	 * boolean型のデータを取り出します
	 * @param key
	 * @param value
	 */
	public boolean getBoolean(String key){
		return booleanMap.get(key);
	}

	/**
	 * 現在のデータを外部出力して保存します
	 */
	protected void save() {
		ObjectOutputStream os=null;
		try {
			if(!dir.exists()||!file.exists()){
				createNewFile();
				return ;
			}

			Map<String,Object> map=new HashMap<String, Object>();
			map.put("int", intMap);
			map.put("float", floatMap);
			map.put("string", stringMap);
			map.put("boolean", booleanMap);
			os=new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 新しくファイルを生成します
	 * @throws IOException
	 */
	private void createNewFile() throws IOException{
		if(!dir.exists())dir.mkdirs();
		if(!file.exists())file.createNewFile();
		intMap=new HashMap<String, Integer>();
		floatMap=new HashMap<String, Float>();
		stringMap=new HashMap<String, String>();
		booleanMap=new HashMap<String, Boolean>();
		save();
	}

	/**
	 * 保存されているデータをロードします
	 */
	@SuppressWarnings("unchecked")
	protected void load(){

		ObjectInputStream os=null;
		try{
			if(!dir.exists()||!file.exists()){
				createNewFile();
			}
			os=new ObjectInputStream(new FileInputStream(file));
			Map<String,Object> map=(Map<String, Object>) os.readObject();
			intMap=(Map<String, Integer>) map.get("int");
			floatMap=(Map<String, Float>) map.get("float");
			stringMap=(Map<String, String>) map.get("string");
			booleanMap=(Map<String, Boolean>) map.get("boolean");

		}catch(Exception e){
			e.printStackTrace();
		} finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
