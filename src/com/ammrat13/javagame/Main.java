package com.ammrat13.javagame;

import javax.swing.*;

/**
 * This class is responsible for starting the game.
 *
 * @author Ammar Ratnani
 */

public class Main {
	
	private static final int WIDTH = 1366;
	private static final int HEIGHT = 786;
	
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Get Home");
		
		GameManager gm = new GameManager(WIDTH, HEIGHT);
		gm.addKeyListener(gm);
		gm.setFocusable(true);
		mainFrame.add(gm);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setVisible(true);
	}
}
