package com.worldwords.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;


public class Launch extends JFrame{
  private Thread startThread;
  private JProgressBar bar;
  private JButton launchBtn ;
   
  public Launch(){      
    this.setSize(300, 80);
    this.setTitle("*** It is raining Words ***");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);      
      
    startThread = new Thread(new Traitement());
    bar  = new JProgressBar();
    bar.setMaximum(500);
    bar.setMinimum(0);
    bar.setStringPainted(true);
      
    this.getContentPane().add(bar, BorderLayout.CENTER);
      
    launchBtn = new JButton("Start");
    launchBtn.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
    	  launch();
      }
    });      
    this.getContentPane().add(launchBtn, BorderLayout.SOUTH);      
    startThread.start();      
    this.setVisible(true);      
  }

  public void launch() {
	  this.setVisible(false);
	  Window window = new Window();
  }
  
  class Traitement implements Runnable{   
    public void run(){
      launchBtn.setEnabled(false);
      for(int val = 0; val <= 500; val++){
        bar.setValue(val);
        try {
          startThread.sleep(10);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
        e.printStackTrace();
        }
      }
      launchBtn.setEnabled(true);
    }   
  }
}