package com.orekyuu.javatter.util;

import java.util.regex.Pattern;

public class JavaBeamUtil {

	public static boolean isJavaBeam(String str){
		//String regex="Javaビーム(ﾋﾞ)++w++$";
		String regex="^[JjＪｊ][AaＡａ][VvＶｖ][AaＡａ]([ビび]|ﾋﾞ)[ーｰ]{1,3}[ムﾑむ]([ビび]|ﾋﾞ){3,}[WwＷｗ]{3,}$";
		Pattern p=Pattern.compile(regex);
		return p.matcher(str).find();
	}
}
