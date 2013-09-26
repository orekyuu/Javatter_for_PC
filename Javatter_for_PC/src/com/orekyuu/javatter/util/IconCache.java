package com.orekyuu.javatter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	public static final File cacheDir=new File("cache");
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
		ImageIcon icon=null;
		icon=getLocalIcon(url);
		if(icon!=null){
			return icon;
		}
		icon=new ImageIcon(url);
		icon=new ImageIcon(icon.getImage().getScaledInstance(48, -1, 4));
		cacheMap.put(url, icon);
		if(JavatterConfig.getInstance().getUseLocalCache()){
			saveLocalIcon(url, icon);
		}
		return icon;
	}

	private ImageIcon getLocalIcon(URL url){
		String fileName=url.toString().replace('/', '_').replace(':', '_');
		File cache=new File(cacheDir, fileName);
		if(cache.exists()){
			ObjectInputStream is=null;
			try{
				is = new ObjectInputStream(new FileInputStream(cache));
				ImageIcon icon = (ImageIcon)is.readObject();
				cacheMap.put(url, icon);
				return icon;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					is.close();
				}catch(Exception e){
				}
			}
		}
		return null;
	}

	private void saveLocalIcon(URL url, ImageIcon icon){
		String fileName=url.toString().replace('/', '_').replace(':', '_');
		File cache=new File(cacheDir, fileName);
		cacheDir.mkdirs();
		ObjectOutputStream os=null;
		try{
			os = new ObjectOutputStream(new FileOutputStream(cache));
			os.writeObject(icon);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				os.close();
			}catch(Exception e){
			}
		}
	}
}
