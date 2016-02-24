package com.changhong.appcontrol.transfer;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.changhong.appcontrol.ControlReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class TransferUtils {
		
	private  TransferUtils(){
	
	}

	
	public static final int REQUEST_DELAY = 2*60*1000;
	public static final String ACTION_CONTROL = "com.changhong.action.appcontrol";
	public static void requestAgain(final Context context,int delay){
		Log.i("yangtong", "start AppControl again 2 min later");
//		Doesn't work in android 5.0
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {				
//			@Override
//			public void run() {
//				Log.i("yangtong","start broadcast sended");
//				Intent intent = new Intent();
//				intent.setAction(ACTION_CONTROL);
//				context.sendBroadcast(intent);
//			}
//		}, delay);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis()+REQUEST_DELAY);
		
		Intent intent = new Intent(context, ControlReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);	
	}
	
	/**
	 * 	返回网络是否已连接
	 */
	public static boolean isNetworkAvaiable(Context context){
		boolean networkAvaiable = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
		if(networkInfos!=null&&networkInfos.length>0){
			for(NetworkInfo networkInfo:networkInfos){
				if(networkInfo.isConnected()){
					networkAvaiable=true;
					break;
				}
			}
		}
		return networkAvaiable;
	}
	
}
