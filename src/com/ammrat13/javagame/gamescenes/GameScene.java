package com.ammrat13.javagame.gamescenes;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * This interface provides the methods a game scene must implement
 *
 * @author Ammar Ratnani
 */

public interface GameScene {
	void start();								// For when starting the scene
	void stop();								// When the scene goes out of focus
	void update(int dt, Set<Integer> kCodes);	// To be called every frame
	BufferedImage render();						// To be called on repaint
}
