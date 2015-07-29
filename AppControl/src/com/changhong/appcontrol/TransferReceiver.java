package com.changhong.appcontrol;

import com.changhong.appcontrol.transfer.Transfer;
import com.changhong.appcontrol.transfer.TransferCallBack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * AppControl入口，负责从服务器端下载信令信息
 * @author YangTong
 *
 */
public class TransferReceiver extends BroadcastReceiver implements TransferCallBack{

	
	int failureCount=0;
	Transfer transfer;
	
	public static final String JSON_URL = "http://192.168.1.105";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO 主要逻辑都在这里实现
		Log.i("yangtong","TransferReceiver onReceive");
		
		failureCount = intent.getIntExtra("failureCount", 0);
		
		transfer = new Transfer(context, JSON_URL);
		transfer.downloadControlInfo(this);
	}

	
	/**
	 * 数据下载完成
	 */
	@Override
	public void downloadFinish(String appControlInfo) {
		// TODO 失败的话继续下一次请求，成功的话则开始进行后续的分离等操作
		Log.i("yangtong","Info >>"+appControlInfo);
		
	}

}
