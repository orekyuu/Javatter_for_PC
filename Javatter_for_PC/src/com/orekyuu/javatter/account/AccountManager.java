package com.orekyuu.javatter.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import twitter4j.auth.AccessToken;

public class AccountManager {

	public static AccountManager instance=new AccountManager();
	public final File file;
	private AccessToken token;

	private AccountManager(){
		file=new File("SaveData/login.dat");
	}

	public static AccountManager getInstance(){
		return instance;
	}

	public boolean isLogined(){
		return file.exists();
	}

	public AccessToken getAccessToken(){
		if(isLogined()){
			token=load();
			return token;
		}
		return null;
	}

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

	public void setAccessToken(AccessToken token){
		this.token=token;
		save(token);
	}
}
