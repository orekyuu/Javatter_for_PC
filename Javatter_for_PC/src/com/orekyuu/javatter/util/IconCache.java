package com.orekyuu.javatter.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class IconCache {

	private static IconCache icon=new IconCache();
	private Map<URL,ImageIcon> cacheMap=new HashMap<URL,ImageIcon>();

	private IconCache(){

	}

	public static IconCache getInstance(){
		return icon;
	}

	public ImageIcon getIcon(URL url){
		if(cacheMap.containsKey(url)){
			return cacheMap.get(url);
		}

		ImageIcon icon=new ImageIcon(url);
		cacheMap.put(url, new ImageIcon(icon.getImage().getScaledInstance(48, -1, 4)));
		return icon;
	}
}
