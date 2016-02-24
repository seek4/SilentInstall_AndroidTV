package com.changhong.appcontrol.analysis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.AsyncTask;
import android.util.Log;

import com.changhong.appcontrol.model.ControlInfo;
import com.changhong.appcontrol.model.InstallApp;
import com.changhong.appcontrol.model.LocalApp;
import com.changhong.appcontrol.model.UninsApp;
public class ServerAnalysis {

	public ControlInfo controlInfo;
	/**json解析出来的installList*/
	public List<InstallApp> installList;
	/**json解析出来的uninsList*/
	public List<UninsApp> uninsList;
	
	/**与localApps对比过之后需执行安装操作的*/
	public List<InstallApp> installApps;
	/**与localApps对比过之后需执行升级操作的*/
	public List<InstallApp> updateApps;
	/**与localApps对比过之后需执行卸载操作的*/
	public List<UninsApp> uninsApps;
	
	public ServerAnalysis(String jsonString){
		controlInfo = analysisJson(jsonString);
		installList = controlInfo.installList;
		uninsList = controlInfo.uninsList;
		Log.i("yangtong","installList >>"+installList.size()+
				"  uninsList >>"+uninsList.size());
	}
	/**
	 * 根据localApps来解析出哪些应用需要安装，那些需要升级
	 * @param localApps
	 */
	public void startAnalysis(ServerAnalysisCallBack callBack,List<LocalApp> usrApps){
		installApps = new ArrayList<InstallApp>();
		updateApps = new ArrayList<InstallApp>();
		uninsApps = new ArrayList<UninsApp>();
		AnalysisTask analysisTask = new AnalysisTask(callBack, usrApps);
		analysisTask.execute();
	}
	
	
	private class AnalysisTask extends AsyncTask<String, integer, integer>{
		ServerAnalysisCallBack callBack;
		List<LocalApp> usrApps;
		
		public AnalysisTask(ServerAnalysisCallBack callBack,List<LocalApp> usrApps) {
			this.usrApps = usrApps;
			this.callBack = callBack;
		}
		
		@Override
		protected integer doInBackground(String... params) {
			boolean needUpdate = false;
			boolean needInstall = true;
			for(InstallApp installApp:installList){
				needUpdate = false;
				needInstall = true;
				for(LocalApp localApp:usrApps){
					if(localApp.packageName.equals(installApp.packageName)){
						if((localApp.version==null&&installApp.version!=null)||
								(installApp.version!=null&&!localApp.version.equals(installApp.version))){
							needInstall = false;
							needUpdate = true;
							break;
						}else {
							needInstall = false;
							break;
						}
					}
				}
				if(needInstall)
					installApps.add(installApp);
				if(needUpdate)
					updateApps.add(installApp);
			}
			
			for(UninsApp uninsApp:uninsList){
				for(LocalApp localApp:usrApps){
					if(localApp.packageName.equals(uninsApp.packageName)){
						uninsApps.add(uninsApp);
						break;
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(integer result) {
			super.onPostExecute(result);
			callBack.analysisFinish(installApps, updateApps,uninsApps);
		}
		
	}
	
	/**
	 * 根据jsonString解析出controlInfo
	 * @param jsonString
	 * @return
	 */
	private ControlInfo analysisJson(String jsonString){
		ControlInfo controlInfo = new ControlInfo();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject infoObject = jsonObject.getJSONObject("appControl");
			controlInfo.version = infoObject.getString("version");
			controlInfo.installList = new ArrayList<InstallApp>();
			controlInfo.uninsList = new ArrayList<UninsApp>();
			JSONObject installObject;
			InstallApp installApp;
			JSONArray installArray = infoObject.getJSONArray("installApps");
			for(int i=0;i<installArray.length();i++){
				installApp = new InstallApp();
				installObject = installArray.getJSONObject(i);
				installApp.appName = installObject.getString("appName");
				installApp.packageName = installObject.getString("packageName");
				installApp.installType = installObject.getInt("installType");
				installApp.appUrl = installObject.getString("appUrl");
				installApp.version = installObject.getString("version");
				controlInfo.installList.add(installApp);
			}
			
			JSONObject uninsObject;
			UninsApp uninsApp;
			JSONArray uninsArray = infoObject.getJSONArray("uninsApps");
			for(int i=0;i<uninsArray.length();i++){
				uninsApp = new UninsApp();
				uninsObject = uninsArray.getJSONObject(i);
				uninsApp.appName = uninsObject.getString("appName");
				uninsApp.packageName = uninsObject.getString("packageName");
				uninsApp.uninsType = uninsObject.getInt("uninsType");
				controlInfo.uninsList.add(uninsApp);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return controlInfo;
	}
}
