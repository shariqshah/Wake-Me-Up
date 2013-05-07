package com.wakemeup;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomQuestion extends Activity {

	Button stopAlarm;
	TextView question;
	EditText answer;
	int alarmID;
	ArrayList<String> allQuestions=new ArrayList<String>();
	ArrayList<String> allAnswers=new ArrayList<String>();
	int chosenOne;
	public static final int RESULT_OK=1;
	dbHandler db=new dbHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_custom_question);
		
		stopAlarm=(Button) findViewById(R.id.stopAlarmCQ);
		question=(TextView) findViewById(R.id.questionCQ);
		answer=(EditText) findViewById(R.id.answerCQ);
		
		initialize();
		
		stopAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(answer.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "No answer given!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String givenAnswer=answer.getText().toString();
					if(givenAnswer.equals(allAnswers.get(chosenOne)))
					{
						Intent resultIntent=new Intent();
						setResult(RESULT_OK,resultIntent);
						finish();
					}
					else
					{
						initialize();
						Toast.makeText(getApplicationContext(), "Wrong Answer!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		
	}
	
	void initialize()
	{
		alarmID=getIntent().getIntExtra("alarmID", 0);
		
		Log.d("CQ","Value of ALARMID SENT : "+String.valueOf(alarmID));
		ArrayList<QnA>quesAns =db.getAlarm(alarmID);
		
		//if(questionData!=null && questionData.moveToFirst())
		if(quesAns!=null)
		{
			for(int i=0;i<quesAns.size();i++)
			{
				allQuestions.add(quesAns.get(i).getQuestion());
				allAnswers.add(quesAns.get(i).getAnswer());
			}
			

			Random rand=new Random();
			int num=allQuestions.size();
			Log.d("CQ","Value of NUM : "+String.valueOf(num));
			if(num>0)
			{
				chosenOne=rand.nextInt(num)+0;
			}
			else
			{
				chosenOne=num;
			}
			
			question.setText(allQuestions.get(chosenOne));
			
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No Questions found in Database!", Toast.LENGTH_SHORT).show();
			//throw new RuntimeException("No Questions Found!");
		}
		
	}

	@Override
	public void onBackPressed()
	{
		//Do Nothing!
		//TODO Find a better way to handle this.
	} 


}
