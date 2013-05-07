package com.wakemeup;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomWindow extends Activity{
	
	//Title bar Icon and text
	protected TextView title;
	protected ImageView icon;
	
	@Override
	public void onCreate(Bundle saveInstanceState)
	{
		super.onCreate(saveInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //Related to custom title bar
		setContentView(R.layout.activity_main_clock);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title); //Related to custom title bar
		
		title=(TextView) findViewById(R.id.title);
		icon=(ImageView) findViewById(R.id.icon);
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		return null;
	}
}
