package com.worldwords.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.wordwords.components.Chrono;
import com.wordwords.components.Rain;
import com.wordwords.components.Score;
import com.wordwords.components.Word;

public class Scene extends JPanel{

	
	private static final String easyFilePath = "fichier/facile.txt";			
	private static final String normalFilePath = "fichier/moyen.txt";			
	private static final String hardFilePath = "fichier/difficile.txt" ;			
	private static final String scoreFilePath = "fichier/meilleursscore.txt";
	private String fileToRead ;
	
	private int maxHeight = 500 ; 		
	private int maxWidth = 800 ;  		  
	private Word currentWord ; 			
	private Word secondCurrentWord ;	
	private List<String> words ; 		
	private List<Word> wordList ; 		
	private List<Word> wordList2 ;		 
	private List<String> hallOfFame ;   
		
	private Font font ; 				
	private Font fontCount ;		
	private Font fontScore ;			
	private Font fontIntro ;
	private Rain rain; 				 
	private Rain rain2; 				
	private int randomPosition ;		 
	private boolean firstword = true;	
	private Count count ;			
		
	private int level ;				
	private Score score ;				
	private boolean isReady ;			
	private boolean gameOver;			
	private String name ;
	
	public Scene() {
		
		setBackground(Color.white);
		wordList = new ArrayList<>();
		// If the level chosen is "difficult", we must initialize the second list
		if(level == 3)
			wordList2 = new ArrayList<>();
		
		font = new Font("Arial", Font.BOLD, 21);
		fontCount = new Font("Arial", Font.BOLD, 45);
		fontScore =  new Font("Arial", Font.BOLD, 15);
		fontIntro = new Font("Arial", Font.BOLD, 25);
		count = new Count();
		score = new Score();
		isReady = false ;
		gameOver = false ;
		new Chrono(this);
	}
	
	public void initWordList() { 
		
		if(level != 3) {
			for (String m : words) {
				randomPosition = 25 + (int)(Math.random() * ((maxWidth - 45) + 1));
				if(firstword) {
					wordList.add(new Word(m,randomPosition , -100 , font));
					firstword = false ;
				}
				else
					wordList.add(new Word(m,randomPosition , 20 , font));
			}
			this.currentWord = wordList.get(0);	
		}
		// If the level chosen is "difficult"
		else {
			int middle = 0 ;
			for (String m : words) {
				randomPosition = 25 + (int)(Math.random() * ((maxWidth - 45) + 1));
				if( middle % 2 == 0) {
					wordList.add(new Word(m,randomPosition , -100 , font));
				}
				else {
					wordList2.add(new Word(m,randomPosition , 20 , font));
				}
				middle ++ ;
			}
			this.currentWord = wordList.get(0);
			this.secondCurrentWord = wordList2.get(0);
		}

	}
	
	public void paintComponent(Graphics g){
		
		 super.paintComponent(g);
		 Graphics2D g2 = (Graphics2D)g;
		 
		 if(!isReady) {
			 drawIntro(g, g2);
		 }
		 else {
			 g.setFont(fontScore);
			 g2.drawString(score.getMessage(), this.getWidth() - 500  ,20); // we display the score
			
			 if (count.isRunning()) // As long as we count, we don't display the words
				 drawDecompte(g , g2);
			 else if (gameOver) { 
				 drawScore(g , g2);
			 }
			 else
				 draw(g , g2); 
		 }
	}
	
	// Displays the current word
	public void draw(Graphics g , Graphics2D g2) {
		
		g.setColor(Color.black);
		g.setFont(currentWord.getFont());
		g2.drawString(currentWord.getValue(), currentWord.getPosX() , currentWord.getPosY());
		
		// If the level chosen is "difficult"
		if (level == 3) {
			g.setFont(secondCurrentWord.getFont());
			g2.drawString(secondCurrentWord.getValue(), secondCurrentWord.getPosX() , secondCurrentWord.getPosY());	
		}
		// If the word has reached the end of the container, we delete it! 
		if(!motVivant(currentWord)) {
			if(wordList.size()>1 ) {
				wordList.remove(currentWord);
				rain.setIsRunnable(false);
				pause();
			}
			
			else if (wordList.size()==1) {
				this.gameOver = true ;
				writeScore(); // we write the score in the file
				hallOfFame = readScore();
			}
			this.currentWord = wordList.get(0);
			rain = new Rain(currentWord , this);		
		}
		// If the level chosen is "difficult"
		if(level ==3 && !motVivant(secondCurrentWord)) {
			if(wordList2.size()>1 ) {
				wordList2.remove(secondCurrentWord);
				rain2.setIsRunnable(false);
				pause();
			}
			// if the last word goes, the game is over!
			else if (wordList2.size()==1 && wordList.size()==1) {
				this.gameOver = true ;
				writeScore(); // we write the score in the file
				hallOfFame = readScore();
			}
			this.secondCurrentWord = wordList2.get(0);
			rain2 = new Rain(secondCurrentWord,this);				
		}
		
	}
	
	// Displays the count
	public void drawDecompte(Graphics g , Graphics2D g2) {
		g.setColor(Color.black);
		g.setFont(fontCount);
		
		if(count.getCountDown()==0) {
			g2.drawString("** Start **", maxWidth/2 -95 , maxHeight / 2);
		}
		else {
			g2.drawString(count.getStr(), maxWidth/2 -15   , maxHeight / 2);
		}
		if(count.getCountDown()== -1) {
			
			// After the countdown we throw the words rain
			
			count.setIsRunning(false);
			rain = new Rain(currentWord , this);
			
			if(level == 3) {
				rain2 = new Rain(secondCurrentWord,this);
			}
		}
	}

	
	public void drawIntro(Graphics g , Graphics2D g2) {
		 String str = "Choose your level and launch the game " ;
		 g.setFont(fontIntro);
		 g2.drawString(str, maxWidth - 640  , maxHeight /2 - 100); 
	}
	
	// To display when the game is over
	public void drawScore(Graphics g , Graphics2D g2) {
		
		 String str = "The game is over , your score is : " + score.getScore() + " / " + score.getMaxLimit() ;
		 g.setFont(fontScore);
		 g2.drawString(str, maxWidth - 550  , maxHeight /2 -100);
		 
		 str = "The previous scores are : ";
		 g2.drawString(str, maxWidth - 550  , maxHeight /2 -70); 
		 		
		 int heigth = maxHeight /2  ;
		 for(String s : hallOfFame) {
			 g2.drawString(s, maxWidth - 550  , heigth );
			 heigth += 25 ;
		 }
		 
	}
	// Retrieve user input and process data
	public void gestionaireProgramme(String mot) {
		
		String validWord = validateWord(mot);
		
		if (level != 3 ) {
			if(compareWord(validWord)) { 
				score.setScore(score.getScore() + 1);
				// If we find the last word the game ends! 
				if(wordList.size()==1 ) {
					this.gameOver = true ;
					writeScore();
					hallOfFame = readScore();
				}
				else {
					
					wordList.remove(currentWord);
					rain.setIsRunnable(false);
					pause();	
					
					this.currentWord = wordList.get(0);
					rain = new Rain(currentWord , this);
				}
			}	
		}
		else {
			if(compareWord2(validWord)) {
				score.setScore(score.getScore() + 1);
				
				if(wordList.size()==1 ) {
					writeScore();
					hallOfFame = readScore();
				}
				else {
					wordList2.remove(secondCurrentWord);
					rain2.setIsRunnable(false);
					pause();
				}
				this.secondCurrentWord = wordList2.get(0);
				rain2 = new Rain(secondCurrentWord , this);
			}
			else if (compareWord(validWord)){
				score.setScore(score.getScore() + 1);

				if(wordList.size()==1 ) {
					writeScore();
					hallOfFame = readScore();
				}
				else {
					wordList.remove(currentWord);
					rain.setIsRunnable(false);
					pause();	
					
					this.currentWord = wordList.get(0);
					rain = new Rain(currentWord , this);
				}
			}
			
			if(wordList.size() == 1 && wordList2.size()==1)
			{
				gameOver  = true ;
			}
		}
	}
	
	// we read the file which contains our dictionary
	private List<String> loadFile(String filePath ) throws FileNotFoundException {
		File file= new File(filePath);
	      Scanner lecture = new Scanner(file);
	      int number = 0 ;
	      List<String> mots = new ArrayList<>();
	      
		      while(lecture.hasNextLine())
		      {  number ++ ;
		    	  String ligne = lecture.nextLine();
		    	  mots.add(validateWord(ligne));
		      } 
	      score.setMaxLimit(number);
	      lecture.close();
	      
	      return mots;
		}
	
	// used to write the score in the file at the end of the game
	public void writeScore() {
		Path path = Paths.get(scoreFilePath);
		String str = name + " with a score of : "+score.getScore()+" / "+score.getMaxLimit() + "; \n";
		try {
			Files.write(path, str.getBytes(Charset.defaultCharset()) , StandardOpenOption.APPEND );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<String> readScore() {
		
		Path path = Paths.get(scoreFilePath);
		List<String> str = null;
		try {
			str = Files.readAllLines(path, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str ;
	}
	
	// we compare the word with the current word
	public boolean compareWord(String word) {
		
		return this.currentWord.getValue().equals(word);
	}
	
	// we compare the word with the current word (case of difficult)
	public boolean compareWord2(String word) {
		
		return this.secondCurrentWord.getValue().equals(word);
	}
	
	// we validate the word
	public String validateWord(String mot) {
		
		String str = "";
		str = mot.strip();
		str.toLowerCase();
		return str ;
	}
	
	// If the word has reached the bottom of the screen
	private boolean motVivant(Word mot) {
		
		if (mot.getPosY() < maxHeight + 15 )
			return true ;
		return false ;
	}
	
	// PAUSE
	private void pause(){
	    try {
	      Thread.sleep(10);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	}
	
	public void start(int niveau) {
		this.level = niveau ;
		resetAll();
		isReady = true ;
		Thread startDecompte = new Thread (count);
		startDecompte.start();	
	}
	
	public void resetAll() {
		// we close the Previous Thread!
		count.setIsRunning(false);
		
		// initialize the new components!s
		
		name  = JOptionPane.showInputDialog(" Enter your name: :-) ");
		wordList = new ArrayList<>();
		if(level == 3)
			wordList2 = new ArrayList<>();
		count = new Count();
		score = new Score();
		gameOver = false ;
		
		if(level == 1) {
			fileToRead = easyFilePath;
		}
		else if (level == 2) {
			fileToRead = normalFilePath;
		}
		else {
			fileToRead = hardFilePath;
		}
		
		try {
			words = loadFile(fileToRead);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initWordList();
		
	}

}
