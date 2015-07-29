package com.changhong.appcontrol;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRouter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	protected Button sendButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sendButton = (Button)this.findViewById(R.id.button_send);
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("yangtong","ButtonClick");
				Intent intent = new Intent();
				intent.setAction("com.changhong.appcontrol.transfer");
				sendBroadcast(intent);
			}
		});
	}


}
