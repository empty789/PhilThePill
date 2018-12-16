package util;

import java.util.ArrayList;

public class Question {
	
	private int id;
	private String question;
	private ArrayList<String> answers;
	private String topic;
	private boolean correct;
	
	public Question(int id, String question, String a1, String a2, String a3, String topic) {
		super();
		this.id = id;
		this.question = question;
		answers = new ArrayList<String>();
		answers.add(a1);
		answers.add(a2);
		answers.add(a3);
		this.topic = topic;
	}
	
	public int getId() {
		return id;
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
