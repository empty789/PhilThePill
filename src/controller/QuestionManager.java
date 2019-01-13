package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import util.Question;

public class QuestionManager {

	ArrayList<Question> questions;
	Random r;
	public QuestionManager() throws FileNotFoundException, IOException {
		questions = parse();
		r = new Random();
		//debug output
//		for(int i = 0; i < questions.size(); i++) {
//			System.out.println("*----------"+i+"------------*");
//			System.out.println(questions.get(i).getId());
//			System.out.println("topic: "+questions.get(i).getTopic());
//			System.out.println("q: "+questions.get(i).getQuestion());
//			System.out.println("a: "+questions.get(i).getAnswers().toString());
//			System.out.println();
//		}
	}

	public Question nextQuestion(String topic) {
		int rand = r.nextInt(questions.size());
		Question q = questions.get(rand);

		while(!q.getTopic().equals(topic)) {
			rand = r.nextInt(questions.size());
			q = questions.get(rand);
		}
		System.out.println(q.getTopic());
		questions.remove(rand);
		return q;
	}
	
	public void reset(){
		try {
			questions = parse();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Question> parse() throws FileNotFoundException, IOException{
		//get all files in questions directory
		int id = 1;
		ArrayList<Question> questions = new ArrayList<Question>();
		URI url = null;
		ArrayList<File> fileList = new ArrayList<File>();
		try {
			url = QuestionManager.class.getResource("/questions/").toURI();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//path to folder
		
		final File folder = new File(Paths.get(url).toString());
		//add found files to fileList
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	           fileList.add(fileEntry);
	        } 
	    }

		//look through each file found and parse questions
		for(int i = 0; i < fileList.size(); i++) {
			File f = fileList.get(i);
			//get topic from filename
			String topic = f.getName().replace(".q", "");
			
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			    String line;
			    int numLine = 0;
			    
			    String question = null;
			    String a1 = null, a2 = null, a3 = null;
			    while ((line = br.readLine()) != null) {
			    	numLine++;
			        if(numLine == 1) {
			        	question = line;
			        }else if(numLine == 2) {
			        	a1 = line;
			        }else if(numLine == 3) {
			        	a2 = line;
			        }else if(numLine == 4) {
			        	a3 = line;
			        	numLine = 0;
			        	questions.add(new Question(id, question, a1, a2, a3, topic));
			        	id++;
			        }
			        
			    }
			}
		}
		
		return questions;
	}
	
}
