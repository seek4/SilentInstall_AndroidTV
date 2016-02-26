package com.changhong.appcontrol.analysis;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.changhong.appcontrol.model.LocalApp;

public class LocalAnalysis {

	/**本地所有应用*/
	public List<LocalApp> localApps;
	/**用户应用*/
	public List<LocalApp> usrApps;	
	/**需卸载的应用*/
	public List<LocalApp> uninsApps;
	private Context context;
	public LocalAnalysis(Context context){
		this.context = context;
		//getLocalApps();
	}
	
	/**
	 * 得到当前所有的本地应用及用户应用,保存在localApps,
	 * 如果是用户应用，保存在usrApps.
	 */
	public void getLocalApps(){
		localApps = new ArrayList<LocalApp>();
		usrApps = new ArrayList<LocalApp>();
		PackageManager pm = context.getPackageManager();
		List <ApplicationInfo> appInfos = pm.getInstalledApplications(0);
		LocalApp app = null;
		for(ApplicationInfo appInfo:appInfos){
			app = new LocalApp();
			app.packageName = appInfo.packageName;
			app.appName = getAppName(appInfo.packageName);			
			app.version = getVersion(appInfo.packageName);
			if(isUsrApp(appInfo.flags)){
				Log.i("yangtong","usrApp name>>"+app.appName);
				usrApps.add(app);
			}
			localApps.add(app);
		}
		if(localApps!=null){
			Log.i("yangtong","usrApps >>"+usrApps.size());
			Log.i("yangtong","localApps >>"+localApps.size());
		}
	}
	
//	public void startAnalysis(LocalAnalysisCallBack callBack,List<ServerApp> serverApps){
//		uninsApps = new ArrayList<LocalApp>();
//		AnalysisTask analysisTask = new AnalysisTask(callBack,serverApps);
//		analysisTask.execute();
//	}
	
//	private class AnalysisTask extends AsyncTask<String, integer, List<LocalApp>>{
//
//		LocalAnalysisCallBack callBack;
//		List<ServerApp> serverApps;
//		
//		public AnalysisTask(LocalAnalysisCallBack callBack,List<ServerApp> serverApps) {
//			// TODO Auto-generated constructor stub
//			this.callBack = callBack;
//			this.serverApps = serverApps;
//		}
//		
//		@Override
//		protected List<LocalApp> doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			boolean isExist = false;
//			for(LocalApp localApp:usrApps){
//				isExist = false;
//				for(ServerApp serverApp:serverApps){
//					if(serverApp.packageName.equals(localApp.packageName)){
//						isExist = true;
//						break;
//					}
//				}
//				if(!isExist){				 
//					uninsApps.add(localApp);
//				}
//			}
//			return uninsApps;
//		}
//
//		@Override
//		protected void onPostExecute(List<LocalApp> result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			callBack.analysisFinish(result);
//		} 
//				
//	}
	
	private boolean isUsrApp(int flags){
		return !((flags&ApplicationInfo.FLAG_SYSTEM)!=0)&&
				!((flags&ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)!=0);
	}
	
	private String getAppName(String packageName){
		PackageManager pm = context.getPackageManager();
		String name = null;
		try {
			name = pm.getApplicationLabel(pm.getApplicationInfo(packageName, 
					PackageManager.GET_META_DATA)).toString();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	private String getVersion(String packageName){
		PackageManager pm = context.getPackageManager();
		try {
			return pm.getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
