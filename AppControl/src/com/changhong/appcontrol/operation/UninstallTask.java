package com.changhong.appcontrol.operation;

import com.changhong.appcontrol.model.UninsApp;

import android.R.integer;
import android.os.AsyncTask;

public class UninstallTask extends AsyncTask<UninsApp, integer, Boolean> {

	OperationCallBack callBack;
	
	public UninstallTask(OperationCallBack callBack){
		this.callBack = callBack;
	}
	
	@Override
	protected Boolean doInBackground(UninsApp... params) {
		return OperationUtils.uninsPackage(0, params[0].packageName);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		callBack.uninstallFinish(result);
	}

	
	
}
