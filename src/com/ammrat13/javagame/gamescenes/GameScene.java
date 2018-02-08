package com.ammrat13.javagame.gamescenes;

import java.awt.image.BufferedImage;

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
	 * Sets the scene to reset on the next load.
	 */
	void resetOnLoad();
	
	/**
	 * To be called every frame.
	 * @param dt The time that has passed since the last frame (ms)
	 */
	void update(int dt);
	
	/**
	 * Renders the scene.
	 * @return The image of this scene to be drawn.
	 */
	BufferedImage render();
}
