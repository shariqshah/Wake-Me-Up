package com.wakemeup;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MathQuestions extends Activity {
	
	private static String[] operators={"+","-","x"};
	TextView num1Text;
	TextView num2Text;
	TextView opText;
	EditText resultTF;
	Button stopAlarm;
	
	private int number1=0;
	private int number2=0;
	private int result=0;
	private static int RESULT_OK=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_math_questions);
		
		num1Text=(TextView) findViewById(R.id.number1);
		num2Text=(TextView) findViewById(R.id.number2);
		opText=(TextView) findViewById(R.id.operator);
		resultTF=(EditText)findViewById(R.id.answerTextMQ);
		stopAlarm=(Button) findViewById(R.id.stopAlarmMQ);
		
		initializeView(); //Generate the Math problem and Set the text fields
		
		stopAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				
				if(resultTF.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Must enter an answer!", Toast.LENGTH_SHORT).show();
				}
				else 
				{
					int valueEntered=Integer.parseInt(resultTF.getText().toString());
					
					if(valueEntered!=result)
					{
						Toast.makeText(getApplicationContext(), "Wrong answer!", Toast.LENGTH_SHORT).show();
						initializeView();
					}
					else if(valueEntered==result)
					{
						Intent resultIntent=new Intent();
						setResult(RESULT_OK,resultIntent);
						finish();
					}
					
				}
				
			}
		});
		
		
		
		
	}
	
	void initializeView()
	{
		Random rand=new Random();
		
		number1=rand.nextInt(10)+1;
		num1Text.setText(String.valueOf(number1));
		
		number2=rand.nextInt(10)+1;
		num2Text.setText(String.valueOf(number2));
		
		int randOp=rand.nextInt(3)+0;
		resultTF.setText("");
		
		switch(randOp)
		{
		case 0:
			opText.setText(operators[randOp]);
			result=number1+number2;
			break;
		case 1:
			opText.setText(operators[randOp]);
			result=number1-number2;
			break;
		case 2:
			opText.setText(operators[randOp]);
			result=number1*number2;
			break;
		};
	}

	@Override
	public void onBackPressed()
	{
		//Do Nothing!
		//TODO Find a better way to handle this.
	} 

}
