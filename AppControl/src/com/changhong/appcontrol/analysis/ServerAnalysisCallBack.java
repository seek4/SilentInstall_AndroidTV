package com.changhong.appcontrol.analysis;

import java.util.List;

import com.changhong.appcontrol.model.InstallApp;
import com.changhong.appcontrol.model.UninsApp;

public interface ServerAnalysisCallBack {

	public void analysisFinish(List<InstallApp> installApps,List<InstallApp> updateApps,
				List<UninsApp> uninsApps);
}
