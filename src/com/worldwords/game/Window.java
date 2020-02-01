package com.worldwords.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class Window extends JFrame{

	// Menu Element
		private JMenuBar menuBar;
		
		private JMenu mFile ;
		private JMenuItem mReplay ;
		private JMenuItem mQuit ;
		
		private JMenu mLevel ;
		
		private ButtonGroup levelGroup;
		private JRadioButtonMenuItem mEasy ;
		private JRadioButtonMenuItem mNormal ;
		private JRadioButtonMenuItem mHard ;
		 
		private Scene scene ;
		private String tippedWord = "" ;
		
		private int level = 1;
	public Window() {
		// Initialize all Parts 
		initPart();
		initMenu();
		initWindow();
		initListener();
		
		this.setVisible(true);
	}
	
	private void initPart() {
		scene = new Scene();
		
		//Menu Element 
		menuBar = new JMenuBar();
		mFile = new JMenu("File");
		mReplay = new JMenuItem("Play / Replay");
		mQuit = new JMenuItem("Quit");
		mLevel = new JMenu("Level");
		
		levelGroup = new ButtonGroup();
		mEasy = new JRadioButtonMenuItem("Easy");
		mNormal = new JRadioButtonMenuItem("Normal");
		mHard = new JRadioButtonMenuItem("Hard");
		
		levelGroup.add(mEasy);
		levelGroup.add(mNormal);
		levelGroup.add(mHard);
	}
	
	private void initMenu() {
		
		mQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
		
		mFile.add(mReplay);
		mFile.add(mQuit);
		
		mLevel.add(mEasy);
		mEasy.setSelected(true);
		mLevel.add(mNormal);
		mLevel.add(mHard);
		
		menuBar.add(mFile);
		menuBar.add(mLevel);
		
		this.setJMenuBar(menuBar);
	}
	
	private void initWindow() {
		
		this.setTitle("Xx-> It is raining words <-xX!");
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(false);
		this.setLocationRelativeTo(null);

		this.setContentPane(scene);
	}
	
	private void initListener() {
		
		ClavierListener clavier = new ClavierListener();
		StartListener start = new StartListener();
		
		this.addKeyListener(clavier);
		mReplay.addActionListener(start);
		mQuit.addActionListener(new ActionListener(){
		     public void actionPerformed(ActionEvent event){
		          System.exit(0);
		        }
		      });
		
		mEasy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				level = 1 ;
			}
			
		});
		
		mNormal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				level = 2 ;
			}
			
		});
		
		mHard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				level = 3 ;
			}
			
		});
	}
	
	
	// Mes Classes Listener 
	
	  class ClavierListener implements KeyListener{

		    public void keyPressed(KeyEvent event) {
		    	//System.out.println("Code touche relâchée : " + event.getKeyCode() + " - caractère touche relâchée : " + event.getKeyChar());         
		    	//pause();   
		    }

		    public void keyReleased(KeyEvent event) {
		    	tippedWord += event.getKeyChar();
		    	
		    	if(event.getKeyCode() == 10) {
		    		//System.out.println("Mot saisit: "+ tippedWord);
		    		scene.gestionaireProgramme(tippedWord);
		    		tippedWord = "";
		    	}
		    	pause();                
		    }

		    public void keyTyped(KeyEvent event) {}   	
		  }
	  
	  class StartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			scene.start(level);
			
		}
		  
	  }
	  
	  private void pause(){
		    try {
		      Thread.sleep(10);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		  } 
}
