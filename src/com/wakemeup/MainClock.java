package com.wakemeup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainClock extends CustomWindow {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // Related to custom titlebar
        
        //setContentView(R.layout.activity_main_clock);
        
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title); // Related to custom titlebar
        Button alarms=(Button)findViewById(R.id.alarms);
        
        alarms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i=new Intent();
				i.setClass(MainClock.this,Alarms.class);
				startActivity(i);
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_clock, menu);
        return true;
    }
    
}
