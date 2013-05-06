package com.wakemeup;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class AlarmRecieverActivity extends Activity {
	
	private MediaPlayer mediaPlayer;
	private PowerManager.WakeLock wakeLock;
	private static int REQUEST_CODE_MATH=1;
	private static int REQUEST_CODE_CUSTOM=2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		PowerManager powerManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock=powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"My Tag");
		wakeLock.acquire();
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
				 WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				 | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		setContentView(R.layout.alarm_reciever_layout);
		
		Button stopAlarm=(Button) findViewById(R.id.stopAlarmBut);
		stopAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int alarmOption=getIntent().getIntExtra("alarmOption", -1);
				
				if(alarmOption==0)
				{
					//Stop Alarm and close the activity
					mediaPlayer.stop();
					finish();
				}
				else if(alarmOption==1)
				{
					//Math Question
					Intent in=new Intent();
					in.setClass(AlarmRecieverActivity.this,MathQuestions.class );
					startActivityForResult(in, REQUEST_CODE_MATH);
				}
				else if(alarmOption==2)
				{
					Intent in=new Intent();
					int alarmID=getIntent().getIntExtra("alarmID", 0);
					in.putExtra("alarmID",alarmID);
					in.setClass(AlarmRecieverActivity.this,CustomQuestion.class );
					startActivityForResult(in, REQUEST_CODE_CUSTOM);
				}
				
				
			}
		});
		
		startAlarmTone(this,getAlarmTone());
	}
	
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		if(wakeLock.isHeld())
		{
			wakeLock.release();
		}
		
		
		
	};
	
	
	
	private void startAlarmTone(Context context, Uri tone)
	{
		mediaPlayer=new MediaPlayer();
		try
		{
			mediaPlayer.setDataSource(context, tone);
			final AudioManager audioManager=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			if(audioManager.getStreamVolume(AudioManager.STREAM_ALARM) !=0)
			{
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mediaPlayer.prepare();
				mediaPlayer.start();
			}
		}
		catch(IOException e)
		{
			Log.d("StartAlarmTone()",e.toString());
		}
	}
	
	//Get Alarm tone. If not available, set it to default ringtone else set it to notification
	private Uri getAlarmTone()
	{
		Uri tone=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if(tone==null)
		{
			tone=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			if(tone==null)
			{
				tone=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			}
		}
		
		
		return tone;
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent resultIntent)
	{
		if(requestCode==REQUEST_CODE_MATH)
		{
			if(resultCode==1)
			{
				mediaPlayer.stop();
				finish();
			}
		}
		else if(requestCode==REQUEST_CODE_CUSTOM)
		{
			if(resultCode==1)
			{
				mediaPlayer.stop();
				finish();
			}
		}
	}
	
	@Override
	public void onBackPressed()
	{
		//Do Nothing!
		//TODO Find a better way to handle this.
	} 
}
