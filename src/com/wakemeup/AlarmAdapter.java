package com.wakemeup;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AlarmAdapter extends ArrayAdapter<Alarm> {

	
	ArrayList <Alarm>alarmList;
	private Context context;
	
	public AlarmAdapter(ArrayList<Alarm> alarms,Context context)
	{
		super(context,R.layout.alarm_list_row,alarms);
		
		this.alarmList=alarms;
		this.context=context;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		 // First let's verify the convertView is not null
	    if (convertView==null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.alarm_list_row, parent, false);
	    }
	    
	    //Now Fill each row with correct values.
	    TextView alarmTime=(TextView) convertView.findViewById(R.id.alarmTime);
	    TextView alarmDays=(TextView) convertView.findViewById(R.id.alarmDays);
	    ToggleButton alarmStatus=(ToggleButton) convertView.findViewById(R.id.alarmToggle);
	    
	    
	    
	    //Get particular alarm at that position and use it to populate row.
	    Alarm alarm=alarmList.get(position);
	    
	    //Converting alarm time from int to string
	    String alarmTimeString=Integer.toString(alarm.alarmHour);
	    alarmTimeString+=" : "+Integer.toString(alarm.alarmMinute);
	    alarmTime.setText(alarmTimeString);
	    
	    //Get Number of days from arraylist into a string and set value
	    String alarmDaysString = "";
	    for(int i=0; i<alarm.alarmDays.size();i++)
	    {
	    	
	    	if(i==(alarm.alarmDays.size()-1))
	    	{
	    		//Don't place comma for final value
	    		alarmDaysString+=alarm.alarmDays.get(i).substring(0, 3);
	    	}
	    	else
	    	{
	    		alarmDaysString+=alarm.alarmDays.get(i).substring(0, 3)+", ";
	    	}
	    	
	    }
	    if(alarmDaysString.equals(""))
	    {
	    	Log.d("Days String", "alarmDaysString is EMPTY");
	    }
	    else
	    {
	    	Log.d("Days String", alarmDaysString);
	    }
	    
	    alarmDays.setText(alarmDaysString);
	    
	    //Set Toggle Button status
	    
	    alarmStatus.setChecked(alarm.alarmStatus);
	    
		
		
		
		return convertView;
	}
	
	
}
