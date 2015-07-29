package com.changhong.appcontrol.transfer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.R.integer;
import android.content.Context;
import android.os.AsyncTask;

public class Transfer {

	public Context context;
	public String urlString;
	
	public interface FinishCallBack{
		
	}
	
	public Transfer(Context context,String urlString){
		this.context = context;
		this.urlString = urlString;
	}
	
	public void downloadControlInfo(TransferCallBack callBack){
		
		GetInfoTask getInfoTask = new GetInfoTask(callBack);
		getInfoTask.execute(urlString);
		
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
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonString;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			callBack.downloadFinish(result);
		}
		
		
		
	}
	
}
