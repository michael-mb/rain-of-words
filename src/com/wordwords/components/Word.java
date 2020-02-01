package com.wordwords.components;

import java.awt.Color;
import java.awt.Font;

public class Word {
	
	private int posX ;
	private int posY ;
	private String value ;
	private Font font ;	
	
	public Word (String value , int posX , int posY , Font font ) {
		
		this.value = value ;
		this.font = font ;
		this.posX = posX ;
		this.posY = posY ;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	public String getValue() {
		return this.value;
	}
	public Font getFont() {
		return this.font;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}

	
	public void setPosY(int posY) {
		this.posY = posY;
	}


}
