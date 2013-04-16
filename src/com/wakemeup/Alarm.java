package com.wakemeup;

import java.io.Serializable;
import java.util.ArrayList;

public class Alarm implements Serializable{
	
	int alarmHour;
	int alarmMinute;
	ArrayList<String> alarmDays;
	Boolean alarmStatus;
	
	public Alarm()
	{
		this.alarmHour=0;
		this.alarmMinute=0;
		this.alarmDays=new ArrayList<String>();
		this.alarmStatus=false;
		
	}
	
	public void setAlarm(int hour,int minute,ArrayList<String> days,Boolean status)
	{
		this.alarmHour=hour;
		this.alarmMinute=minute;
		this.alarmDays=days;
		this.alarmStatus=status;
	}
	
	public Alarm getAlarm()
	{
		return this;
	}

}
