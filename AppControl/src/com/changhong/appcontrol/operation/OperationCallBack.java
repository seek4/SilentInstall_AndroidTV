package com.changhong.appcontrol.operation;

public interface OperationCallBack {

	
	/**
	 * 
	 * @param result 安装是否成功
	 */
	public void installFinish(boolean result);
	
	/**
	 * 
	 * @param result 更新是否成功
	 */
	public void updateFinish(boolean result);
	
	
	/**
	 * 
	 * @param result 卸载是否成功
	 */
	public void uninstallFinish(boolean result);
}
