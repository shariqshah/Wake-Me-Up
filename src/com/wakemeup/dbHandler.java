package com.wakemeup;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbHandler extends SQLiteOpenHelper {

	private static String dbName="alarmDb";
	
	public  dbHandler(Context c)
	{
		super(c,dbName,null,1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String query;
		try
		{
			query="create table alarm (id integer primary key autoincrement, alarm_id integer, question text, answer text)";
			db.execSQL(query);
			Log.d("DB Init", "Success!");
		}
		catch(Exception e)
		{
			Log.d("Database Initialization : ",e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("drop table if exists alarm");
		
		onCreate(db);
		
	}
	
	public void addQuestion(int alarmID,String question,String answer)
	{
		String query;
		SQLiteDatabase db=this.getWritableDatabase(); 
		try
		{
			query="Insert into alarm (alarm_id, question, answer) values ("+String.valueOf(alarmID)+",'"+question+"','"+answer+"')";
			db.execSQL(query);
			Log.d("DB ADD", question+" : "+answer);
			
			
		}
		catch(Exception e)
		{
			Log.d("DB Adding Record",e.toString());
		}
	}
	
	public ArrayList<QnA> getAlarm(int alarmID)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		String query="select * from alarm where alarm_id = "+String.valueOf(alarmID);
		Log.d("DB SELECT QUERY", query);
		Cursor results;// = null;
		ArrayList<QnA> quesAns=new ArrayList<QnA>();
		if(db.isOpen())
		{
			Log.d("DB SELECT STATE", "TRUE");
		}
		else
		{
			Log.d("DB SELECT STATE", "FALSE");
		}

			results=db.rawQuery(query, null);

		
		if(results.getCount()>0)
		{			
			while(results.moveToNext())
			{
				QnA temp=new QnA(results.getString(2), results.getString(3));
				quesAns.add(temp);
			}
			results.close();
			db.close();
			Log.d("DB QA SIZE",String.valueOf(quesAns.size()));
			return quesAns;
		}
		else
		{
			results.close();
			db.close();
			return null;
		}
		
	}
	
	public boolean deleteAlarm(int alarmID)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		int returnCode=-1;
		
		try
		{
			returnCode=db.delete("alarm", "alarm_id="+alarmID, null);
		}
		catch(Exception e)
		{
			Log.d("DB Delete",e.toString());
		}
		
		if(returnCode==-1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
}













