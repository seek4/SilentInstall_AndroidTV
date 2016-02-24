package com.changhong.appcontrol.model;

public class InstallApp {
	public String appName;
	public String packageName;
	/**1.静默安装2.自动安装，完成后通知3.用户选择是否安装*/
	public int installType;
	public String version;
	public String appUrl;
}
