package com.wakemeup;

import android.os.Bundle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;


public class AlarmList extends CustomWindow {
	
	Calendar dateTime=Calendar.getInstance();
	//ArrayList<String>daysSelected=new ArrayList<String>();
	Resources res;
	String[] days;
	int counter=1;
	private ListView alarmList;
	AlarmAdapter adapter;
	static final ArrayList <Alarm> alarms=new ArrayList<Alarm>();
	private int alarmCount=0;
	private int alarmHour;
	private int alarmMinute;
	private int optionSelected=-1;
	private static int[] RETURN_CODES={0,1};
	private static int REQUEST_CODE_CUSTOM=1;
	private static int REQUEST_CODE_MATH=1;
	private ArrayList<String>daysSelected=new ArrayList<String>();
	private int glob_alarmID;
	
	
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
				dateTime=Calendar.getInstance();
				Log.d("Add Alarm", "Add alarm imageview was clicked!");
				new TimePickerDialog(v.getContext(), tListener, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();
				
			}
		});
        
       
        //Populate the listview
        alarmList=(ListView) findViewById(R.id.alarmList);
        adapter=new AlarmAdapter(alarms, getApplicationContext());
		alarmList.setAdapter(adapter);
       
        
    }
	
	 
    TimePickerDialog.OnTimeSetListener tListener=new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			
			
			
			
			//Fix for android jelly bean bug with OnTimeSetListener being called twice. First check for OS version, if jelly bean then apply fix.
			//If counter value is 1, call the date picker dialog else don't call
			//and set the value back to 1 for the next time.
			int currentApiVersion=android.os.Build.VERSION.SDK_INT;
			if(currentApiVersion>=16)
			{
				if(counter==1)
				{
					counter++;
					alarmHour=hourOfDay;
					alarmMinute=minute;
					
					daysPickerDialog();
				}
				else if(counter==2)
				{
					counter=1;
				}
			}
			else
			{
				alarmHour=hourOfDay;
				alarmMinute=minute;
				daysPickerDialog();
			}
			
			
		}
	};
	
	
	public void daysPickerDialog()
	{
		 
		//daysSelected.clear();
		if(daysSelected.isEmpty())
		{
			Log.d("Days List", "It is empty indeed!");
		}
		
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
					daysPickerDialog();
				}
				else
				{
					//Proceed with saving the alarm and setting it
					alarmOptionsDialog();
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

	public void alarmOptionsDialog()
	{
		AlertDialog.Builder builder=new Builder(this);
		final CharSequence[] options={"None","Math Questions","Custom Questions"};
		
		
		builder.setTitle("Extra Options");
		builder.setSingleChoiceItems(options, -1,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(options[which].equals("None"))
				{
					optionSelected=0;
				}
				else if(options[which].equals("Math Questions"))
				{
					optionSelected=1;
				}
				else if(options[which].equals("Custom Questions"))
				{
					optionSelected=2;
				}
				
			}
		});
		
		builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				switch(optionSelected)
				{
				case 0:
					//Set Normal Alarm.
					setAlarmCaller();
					break;
				case 1:
					//Set Alarm with Math question Option.
					setAlarmCaller();
					break;
				case 2:
					Intent in=new Intent();
					in.setClass(AlarmList.this, QuestionForm.class);
					//int alarmID=(int)System.currentTimeMillis();
					glob_alarmID=(int)System.currentTimeMillis();
					in.putExtra("alarmID", glob_alarmID); 
					startActivityForResult(in, REQUEST_CODE_CUSTOM);
					
					break;
				}
			}
		});
		
		builder.show();
	}

	void saveAlarms()
	{
		try
		{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File("Alarms.bin")));
			for(int i=0;i<alarms.size();i++)
			{
				oos.writeObject(alarms.get(i));
			}
			oos.flush();
			oos.close();
		}
		catch(Exception e)
		{
			Log.d("Serialization", e.toString());
		}
		
		
		
		//Write alarm count to another file
		try
		{
			OutputStreamWriter writer=new OutputStreamWriter(openFileOutput("Alarm Count.txt", Context.MODE_PRIVATE));
			writer.write(Integer.toString(alarmCount));
			writer.close();
		}
		catch(Exception e)
		{
			Log.d("Writing Alarm Count", e.toString());
		}
	
	}
	
	
	public void loadAlarms()
	{
		//Load Alarm Count first
		try
		{
			InputStream istream=openFileInput("Alarm Count.txt");
			if(istream!=null)
			{
				InputStreamReader reader= new InputStreamReader(istream);
				BufferedReader br=new BufferedReader(reader);
				String recieve=br.readLine();
				
				istream.close();
				alarmCount=Integer.parseInt(recieve);
				
				
			}
			
			
		}
		catch(Exception e)
		{
			Log.d("Reading Alarm Count", e.toString());
		}
		
		
		
		
		try
		{
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream("Alarms.bin"));
			for(int i=0;i<alarmCount;i++)
			{
				alarms.add((Alarm) ois.readObject());
			}
			ois.close();
		}
		catch(Exception e)
		{
			Log.d("Deserialization", e.toString());
		}
	}
	
	void setAlarm(String day)
	{
		Log.d("DAY ",day);
		Calendar alarmTime=Calendar.getInstance();
		alarmTime.set(Calendar.HOUR_OF_DAY, alarmHour);
		alarmTime.set(Calendar.MINUTE, alarmMinute);
		alarmTime.set(Calendar.SECOND, 0);
		alarmTime.set(Calendar.DAY_OF_WEEK, getDay(day));
		
		int alarmID=0;
		if(optionSelected==2)
		{
			alarmID=glob_alarmID;
		}
		else
		{
			alarmID=(int)System.currentTimeMillis();
		}
		
		
		Intent intent=new Intent(this,AlarmRecieverActivity.class);
		intent.putExtra("alarmID", alarmID);
		intent.putExtra("alarmOption", optionSelected);
		PendingIntent pendingIntent=PendingIntent.getActivity(this, alarmID, intent,PendingIntent.FLAG_ONE_SHOT);
		
		
		AlarmManager alarmManager=(AlarmManager)getSystemService(Activity.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, pendingIntent);
	}
	
	//Returns the integer corresponding to the day of week in the string parameter
	int getDay(String day)
	{
		if(day.equals(days[0]))
		{
			return Calendar.MONDAY;
		}
		else if(day.equals(days[1]))
		{
			return Calendar.TUESDAY;
		}
		else if(day.equals(days[2]))
		{
			return Calendar.WEDNESDAY;
		}
		else if(day.equals(days[3]))
		{
			return Calendar.THURSDAY;
		}
		else if(day.equals(days[4]))
		{
			return Calendar.FRIDAY;
		}
		else if(day.equals(days[5]))
		{
			return Calendar.SATURDAY;
		}
		else if(day.equals(days[6]))
		{
			return Calendar.SUNDAY;
		}
		else
		{
			throw new RuntimeException("Invalid day specified!");
		}
	}

	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent Data)
	{
		if(requestCode==REQUEST_CODE_CUSTOM)
		{
			switch (resultCode)
			{
			case 0:
				//User Pressed Cancel
				Toast.makeText(getApplicationContext(), "Alarm not set!", Toast.LENGTH_SHORT).show();
				resetVariables();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "Alarm set!", Toast.LENGTH_SHORT).show();
				setAlarmCaller();
				break;
			};
		}
	}
	
	void setAlarmCaller()
	{
		for(int i=0;i<daysSelected.size();i++)
		{
			Log.d("ALARMCALLER", daysSelected.get(i));
			setAlarm(daysSelected.get(i));
			alarmCount++;
		}
		
		Alarm tempAlarm=new Alarm();
		tempAlarm.setAlarm(alarmHour, alarmMinute, daysSelected,true);
		alarms.add(tempAlarm);
		adapter.notifyDataSetChanged();
		resetVariables();

		//TODO Call serialization code here
	}
	
	void resetVariables()
	{
		alarmHour=-1;
		alarmMinute=-1;
		daysSelected.clear();
		optionSelected=-1;
		//adapter.notifyDataSetChanged();
	}
}










