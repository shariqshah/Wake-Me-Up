package com.wakemeup;

import android.os.Bundle;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class Alarms extends CustomWindow {
	
	Calendar dateTime=Calendar.getInstance();
	ArrayList<String>daysSelected=new ArrayList();
	Resources res;
	String[] days;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        
        //this.title.setText("Add New");
        this.icon.setImageResource(R.drawable.add_icon);
        
        res=getResources();
        days=res.getStringArray(R.array.days);
        
        icon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("Add Alarm", "Add alarm imageview was clicked!");
				new TimePickerDialog(v.getContext(), tListener, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();
				
			}
		});
       
        
    }
	
	 
    TimePickerDialog.OnTimeSetListener tListener=new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			
			daysPickerDialog();
		}
	};
	
	
	public void daysPickerDialog()
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("Select Days");
		
		
		builder.setMultiChoiceItems(R.array.days,null,new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				
				if(isChecked)
				{
					daysSelected.add(days[which]); //If day is selected add it to the selected days.
				}
				
				
			}
		}	);
		
		builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(daysSelected.isEmpty())
				{
					//If none of the days were selected show error. User must select atleast one.
					Toast.makeText(getApplicationContext(), "Must select atleast one day", Toast.LENGTH_SHORT).show();
				}
				else
				{
					//Proceed with saving the alarm and setting it
				}
				
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(daysSelected.isEmpty()!=true)
				{
					//empty the days selected if it contains any items
					daysSelected.clear();
				}
				else
				{
					dialog.dismiss();
				}
					
				
			}
		});
		
		builder.show();
		//AlertDialog dialogue=builder.create();
		//dialogue.show();
		//Log.d("Dialog", "DialogBox has been shown!");
	}

}










