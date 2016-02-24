package com.changhong.appcontrol.operation;

import com.changhong.appcontrol.model.InstallApp;
import android.R.integer;
import android.os.AsyncTask;

/**
 * 新建线程用来下载并安装某个应用
 * @author yangtong
 *
 */
public class InstallTask extends AsyncTask<InstallApp, integer, Boolean> {

	OperationCallBack callBack;
	
	public InstallTask(OperationCallBack callBack){
		this.callBack = callBack;
	}	
	
	@Override
	protected Boolean doInBackground(InstallApp... params) {	
		return OperationUtils.installPackage(params[0].installType, params[0].appUrl);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		callBack.installFinish(result);
	}

	
	
}
