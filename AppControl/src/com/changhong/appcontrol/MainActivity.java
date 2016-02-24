package com.changhong.appcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final String ACTION_CONTROL = "com.changhong.action.appcontrol";
		
		Button btnTest = (Button)this.findViewById(R.id.btn_test);
		btnTest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(ACTION_CONTROL);
				sendBroadcast(intent);
			}
		});
	
		
	}
}
