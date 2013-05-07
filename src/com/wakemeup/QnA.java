package com.wakemeup;

public class QnA {
	
	String question;
	String answer;
	
	public QnA(String q,String a)
	{
		this.question=q;
		this.answer=a;
	}
	
	public String getQuestion()
	{
		return this.question;
	}
	
	public String getAnswer()
	{
		return this.answer;
	}

}
