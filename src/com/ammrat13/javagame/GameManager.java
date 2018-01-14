package com.ammrat13.javagame;

import java.awt.*;
import javax.swing.*;

/**
 * This class will manage all aspects of the game, including scene cordination.
 * It will start on a given scene, and transition between them.
 *
 * @author Ammar Ratnani
 */

public class GameManager extends JPanel {
	
	public final int WIDTH;
	public final int HEIGHT;
	
	public GameManager(int w, int h){
		WIDTH = w;
		HEIGHT = h;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
}
