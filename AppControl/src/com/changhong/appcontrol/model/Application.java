package com.changhong.appcontrol.model;

public class Application {
	public final String appName;
	public final int appID;
	public final int orgID;
	public final int appType;
	public final int appVersion;
	public final int appPriority;
	public final int installType;
	public final String cerID;
	public final String appUrl;
	
	public Application(String appName,int appID,int orgID,
			int appType,int appVersion,int appPriority,int installType,String cerID,String appUrl){
		this.appName = appName;
		this.appID = appID;
		this.orgID = orgID;
		this.appType = appType;
		this.appVersion = appVersion;
		this.appPriority = appPriority;
		this.installType = installType;
		this.cerID = cerID;
		this.appUrl = appUrl;
	}
}
