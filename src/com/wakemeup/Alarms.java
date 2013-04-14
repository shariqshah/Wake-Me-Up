package com.wakemeup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Alarms extends CustomWindow {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        
        //this.title.setText("Add New");
        this.icon.setImageResource(R.drawable.add_icon);
        
        
    }

}
