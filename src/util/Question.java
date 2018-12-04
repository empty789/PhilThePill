package util;

import java.util.ArrayList;

public class Question {
	
	private String question;
	private ArrayList<String> answers;
	private String topic;
	
	public Question(String question, String a1, String a2, String a3, String topic) {
		super();
		this.question = question;
		answers = new ArrayList<String>();
		answers.add(a1);
		answers.add(a2);
		answers.add(a3);
		this.topic = topic;
	}
	
	public ArrayList<String> getAnswers() {
		return answers;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getTopic() {
		return topic;
	}
}
