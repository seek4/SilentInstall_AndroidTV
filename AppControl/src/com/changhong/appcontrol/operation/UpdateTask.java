package com.changhong.appcontrol.operation;

import com.changhong.appcontrol.model.InstallApp;
import android.R.integer;
import android.os.AsyncTask;

public class UpdateTask extends AsyncTask<InstallApp, integer, Boolean> {

	OperationCallBack callBack;
	
	public UpdateTask(OperationCallBack callBack){
		this.callBack = callBack;
	}
	
	@Override
	protected Boolean doInBackground(InstallApp... params) {
		// TODO Auto-generated method stub
		return OperationUtils.updatePackage(params[0].installType, 
				params[0].packageName,params[0].appUrl);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		callBack.updateFinish(result);
	}
}
