package com.changhong.appcontrol;

import com.changhong.appcontrol.transfer.InfoTransfer;
import com.changhong.appcontrol.transfer.TransferCallBack;
import com.changhong.appcontrol.transfer.TransferUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/**
 * AppControl入口
 * @author yangtong
 *
 */
public class ControlReceiver extends BroadcastReceiver implements TransferCallBack{

	public static final String TAG = "yangtong";
	//public static final String SERVER_IP = "http://192.168.10.107";
	public static final String SERVER_IP = "http://192.168.103.211";
	//public static final String INFO_URL = "http://yangtong.me/control.json";
		
	private Context context;
	
	InfoTransfer transfer;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "ControlReceiver onReceive");
		this.context = context;
		if(!TransferUtils.isNetworkAvaiable(context)){//	无网络时2分钟后再次获取
			TransferUtils.requestAgain(context, TransferUtils.REQUEST_DELAY);
			return;
		}		
		transfer = new InfoTransfer();
		transfer.GetControlInfo(SERVER_IP+"/control.json", this);
	}
	


	/**
	 * 从服务器获取json数据结束后触发
	 */
	@Override
	public void transferFinish(String controlJson) {
		// 判断有效性，如果有效，则StartService进行处理
		Log.i(TAG,"controlInfo >>"+controlJson);
		if(transfer.isInfoEffective(controlJson)){
			// 开Service继续接下来的操作
			Intent intent = new Intent(context,ControlService.class);
			intent.setAction("com.changhong.service.appcontrol");
			intent.putExtra("info", controlJson);
			context.startService(intent);
		}else {
			// 过一段时间后重新请求数据
			TransferUtils.requestAgain(context, TransferUtils.REQUEST_DELAY);
		}
	}
	
}
