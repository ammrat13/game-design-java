package com.ammrat13.javagame;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * This interface provides the methods a game scene must implement
 *
 * @author Ammar Ratnani
 */

public interface GameScene {
	void start();				// For when starting the scene
	void stop();				// When the scene goes out of focus
	void update();				// To be called every frame
	void keyDown(int kCode);	// For when a key is down
	BufferedImage render();		// To be called on repaint
}
