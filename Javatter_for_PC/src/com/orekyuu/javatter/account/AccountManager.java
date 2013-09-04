package com.orekyuu.javatter.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import twitter4j.auth.AccessToken;

/**
 * アカウント管理クラス
 * @author orekyuu
 *
 */
public class AccountManager {

	private static AccountManager instance=new AccountManager();
	private final File file;
	private AccessToken token;

	private AccountManager(){
		file=new File("SaveData/login.dat");
	}

	/**
	 * インスタンスを取得する
	 * @return
	 */
	public static AccountManager getInstance(){
		return instance;
	}

	/**
	 * 認証済みかどうか
	 * @return
	 */
	public boolean isLogined(){
		return file.exists();
	}

	/**
	 * アクセストークンを取得します
	 * @return
	 */
	public AccessToken getAccessToken(){
		if(isLogined()){
			token=load();
			return token;
		}
		return null;
	}

	/**
	 * ファイルからアクセストークンを取得
	 * @return
	 */
	private AccessToken load(){
		ObjectInputStream os=null;
		AccessToken t=null;;
		try {
			os=new ObjectInputStream(new FileInputStream(file));
			t=(AccessToken) os.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}

	/**
	 * アクセストークンをファイルに保存
	 * @param token
	 */
	private void save(AccessToken token){
		ObjectOutputStream os=null;
		try {
			os=new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(token);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	 * アクセストークンを保存
	 * @param token
	 */
	public void setAccessToken(AccessToken token){
		this.token=token;
		save(token);
	}
}
