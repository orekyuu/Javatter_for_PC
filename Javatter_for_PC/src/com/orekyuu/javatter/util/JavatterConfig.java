package com.orekyuu.javatter.util;

import java.io.File;

public class JavatterConfig {

	private static JavatterConfig instance=new JavatterConfig();

	private SaveData savedata;

	private JavatterConfig(){

	}

	public void init(){
		savedata=new SaveData("JavatterConfig", new File("SaveData/JavatterConfig"));
		SaveDataManager.getInstance().registerSaveData(savedata);
		savedata.setDefaultValue("JavaBeamRT", false);
		savedata.setDefaultValue("thanks", false);
		savedata.setDefaultValue("isLoad", true);
	}

	public static JavatterConfig getInstance(){
		return instance;
	}

	public void setJavaBeamRT(boolean flag){
		savedata.setBoolean("JavaBeamRT", flag);
	}

	public boolean getJavaBeamRT(){
		return savedata.getBoolean("JavaBeamRT");
	}

	public void setThanks(boolean flag){
		savedata.setBoolean("thanks", flag);
	}

	public boolean getThanks(){
		return savedata.getBoolean("thanks");
	}
}
