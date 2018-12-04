package game;

import java.io.FileNotFoundException;
import java.io.IOException;

import controller.QuestionManager;
import view.MainWindow;

public class Main {

	
	
	
	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		try {
			QuestionManager qm = new QuestionManager();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
