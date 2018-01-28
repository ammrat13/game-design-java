package com.ammrat13.javagame.gamescenes;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * This interface provides the methods a game scene must implement
 *
 * @author Ammar Ratnani
 */

public interface GameScene {
	/**
	 * Called when the scene is reloaded after being switch away. Differs from
	 * the constructor, which is called only at the start of the game.
	 */
	void start();
	
	/**
	 * Called when the scene goes out of focus.
	 */
	void stop();
	
	/**
	 * To be called every frame.
	 *
	 * @param dt The time that has passed since the last frame (ms)
	 * @param kCodes The set of all keys that are pressed down
	 */
	void update(int dt, Set<Integer> kCodes);
	
	/**
	 * @return The image of this scene to be drawn.
	 */
	BufferedImage render();
}
