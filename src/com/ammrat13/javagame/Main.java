package com.ammrat13.javagame;

import javax.swing.*;

/**
 * This class is responsible for starting the game.
 *
 * @author Ammar Ratnani
 */

public class Main {
	
	/** The width of the display window. */
	private static final int WIDTH = 1366;
	
	/** The height of the display window. */
	private static final int HEIGHT = 786;
	
	/**
	 * The main method for the entire project.
	 * @param args Command line arguments
	 */
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("Get Home");
		
		// The game manager is the only thing that goes on the window
		GameManager gm = new GameManager(WIDTH, HEIGHT);
		gm.addKeyListener(gm);
		gm.setFocusable(true);
		mainFrame.add(gm);
		
		// Start
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setVisible(true);
	}
}
