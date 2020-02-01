package com.wordwords.components;

import com.worldwords.game.Scene;

public class Rain implements Runnable{

	private static final int PAUSE = 10 ;
	private Word word ;
	private boolean isRunnable = true;
	private int speed = 1  ;
	public Rain(Word word , Scene scene ) {
		
		this.word = word ;		
		Thread go = new Thread(this);
		go.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunnable) {
				word.setPosY(word.getPosY()+ speed);
			try {
				Thread.sleep(PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setIsRunnable (boolean isRunnable){
		this.isRunnable = isRunnable ;
	}

}
