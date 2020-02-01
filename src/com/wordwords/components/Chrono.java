package com.wordwords.components;

import com.worldwords.game.Scene;

// TIME 
public class Chrono implements Runnable{

	private final int PAUSE = 3;
	private Scene scene ;
	private boolean repaint = true ;
	
	public Chrono (Scene scene) {
		this.scene = scene ;
		
		Thread myThread = new Thread(this);
		myThread.start();
	}
	
	public void setRepaint(boolean repaint) {
		this.repaint = repaint ; 
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(repaint){
			
			scene.repaint();
			try {
				Thread.sleep(PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		scene.repaint();
	}

}