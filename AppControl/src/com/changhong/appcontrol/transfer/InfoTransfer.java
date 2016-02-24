package com.changhong.appcontrol.transfer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.AsyncTask;

public class InfoTransfer {

	public InfoTransfer(){		
	}
	
	public void GetControlInfo(String urlString,TransferCallBack callBack){
		GetInfoTask getInfoTask = new GetInfoTask(callBack);
		getInfoTask.execute(urlString);
	}
	
	public boolean isInfoEffective(String jsonInfo){
		try {
			new JSONObject(jsonInfo);//	如果可以解析成JsonObject，则默认其传输成功
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public class GetInfoTask extends AsyncTask<String, integer, String>{
			
			TransferCallBack callBack;
			
			public GetInfoTask(TransferCallBack callBack) {
				// TODO Auto-generated constructor stub
				this.callBack = callBack;
			}
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String jsonString = "";
				try {
					URL url = new URL(params[0]);
					InputStream is = url.openStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line = null;
					while((line=br.readLine())!=null){
						jsonString+=line;
					}
					is.close();				
				} catch (Exception e) {
					e.printStackTrace();			
				}
				return jsonString;
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				callBack.transferFinish(result);
			}
	}
			
}
