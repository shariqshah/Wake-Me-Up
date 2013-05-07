package com.wakemeup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionForm extends Activity {

	Button nextQues;
	Button cancelQues;
	EditText question;
	EditText answer;
	Button done;
	dbHandler db=new dbHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_form);
		
		done=(Button) findViewById(R.id.doneQuestionBut);
		nextQues=(Button) findViewById(R.id.nextQuestionBut);
		cancelQues=(Button) findViewById(R.id.cancelQuestionBut);
		question=(EditText) findViewById(R.id.questionText);
		answer=(EditText) findViewById(R.id.answerText);
		
		cancelQues.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent resultIntent=new Intent();
				setResult(0,resultIntent);
				finish();
			}
		});
		
		nextQues.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(question.getText().equals("") || answer.getText().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Enter both the question and answer to proceed", Toast.LENGTH_SHORT).show();
				}
				else
				{
					int alarmID=getIntent().getIntExtra("alarmID", 0);
					
					db.addQuestion(alarmID, question.getText().toString(), answer.getText().toString());
					
					//Reset Fields
					question.setText("");
					answer.setText("");
				}
			}
		});
		
		done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(question.getText().equals("") || answer.getText().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Enter both the question and answer to proceed", Toast.LENGTH_SHORT).show();
				}
				else
				{
					int alarmID=getIntent().getIntExtra("alarmID", 0);
					
					db.addQuestion(alarmID, question.getText().toString(), answer.getText().toString());
					
					//Reset Fields
					question.setText("");
					answer.setText("");
					
					Intent resultIntent=new Intent();
					setResult(1,resultIntent);
					finish();
					
				}
				
			}
		});

	}

		
	
	
}
