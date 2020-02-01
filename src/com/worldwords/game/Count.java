package com.worldwords.game;

public class Count implements Runnable{
	private final int PAUSE = 1000 ;
	private int countDown ;
	private String str;
	private boolean isRunning ;
	public Count() {
		
		this.countDown = 3 ;
		this.str ="3";
		this.isRunning = true ;
	}
	public int getPAUSE() {
		return PAUSE;
	}
	public String getStr() {
		return str;
	}
	public boolean isRunning() {
		return this.isRunning;
	}
	public int getCountDown() {
		return this.countDown;
	}
	
	public void setCountDown(int compteurTemps) {
		this.countDown = compteurTemps;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning) {
			try {
				Thread.sleep(PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			countDown -- ;
			str = ""+countDown ;
			
			//Seulement pour se rassurer que le Thread se ferme 
			if(countDown == -2) {
				isRunning = false ;
			}
		}
	}

}
