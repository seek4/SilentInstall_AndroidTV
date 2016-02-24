package com.changhong.appcontrol.analysis;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * json版本号分析及控制
 * @author yangtong
 *
 */
public class VersionAnalysis {

	private SharedPreferences versionPref;
	public static final String PREF_VERSION = "control_version";
	
	public VersionAnalysis(Context context){
		this.versionPref = context.getSharedPreferences(PREF_VERSION,Context.MODE_PRIVATE);
	}
	
	/**
	 * 获取上一次保存的版本号。
	 * @return
	 */
	public String getLastVersion(){
		
		return versionPref.getString("version", null);
	}
	
	/**
	 * 版本号是否发生改变
	 * @param curVersion 此次解析出来的版本号
	 * @return
	 */
	public boolean isVersionChanged(String curVersion){
		if(curVersion.equals(getLastVersion())){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 将当前版本号保存到SharedPreferences
	 * @param curVersion
	 * @return
	 */
	public boolean saveCurVersion(String curVersion){
		SharedPreferences.Editor editor = versionPref.edit();
		editor.putString("version", curVersion);
		editor.commit();
		Log.i("yangtong","Save Cur Version "+curVersion);
		return true;
	}
	
}
