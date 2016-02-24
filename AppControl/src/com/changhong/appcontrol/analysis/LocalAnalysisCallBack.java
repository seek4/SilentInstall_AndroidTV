package com.changhong.appcontrol.analysis;

import java.util.List;

import com.changhong.appcontrol.model.LocalApp;

public interface LocalAnalysisCallBack {

	public void analysisFinish(List<LocalApp> uninsApps);
	
}
