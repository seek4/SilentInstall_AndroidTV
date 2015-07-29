package com.changhong.appcontrol.model;

import java.util.List;

public class AppControl {

	public int networkId;
	public int versionMajor;
	public int versionMinor;
	public int dataLength;
	public List<Certification> certifications;
	public List<Application> applications;
	public AppControl(){
		
	}
	public AppControl(int networkId,int versionMajor,int versionMinor,int dataLength){
		this.networkId = networkId;
		this.versionMajor = versionMajor;
		this.versionMinor = versionMinor;
		this.dataLength = dataLength;
	}
}
