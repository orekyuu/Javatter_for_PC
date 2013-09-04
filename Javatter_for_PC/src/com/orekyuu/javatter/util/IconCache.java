package com.orekyuu.javatter.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * アイコンのキャッシュ
 * @author orekyuu
 *
 */
public class IconCache {

	private static IconCache icon=new IconCache();
	private Map<URL,ImageIcon> cacheMap=new HashMap<URL,ImageIcon>();

	private IconCache(){

	}

	/**
	 * インスタンスを返す
	 * @return
	 */
	public static IconCache getInstance(){
		return icon;
	}

	/**
	 * アイコンを取得する
	 * @param url アイコンのURL
	 * @return
	 */
	public ImageIcon getIcon(URL url){
		if(cacheMap.containsKey(url)){
			return cacheMap.get(url);
		}

		ImageIcon icon=new ImageIcon(url);
		cacheMap.put(url, new ImageIcon(icon.getImage().getScaledInstance(48, -1, 4)));
		return icon;
	}
}
