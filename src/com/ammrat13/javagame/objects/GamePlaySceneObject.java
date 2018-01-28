package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.util.Vec;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * This interface provides the methods an object in the game scene must
 * implement
 *
 * @author Ammar Ratnani
 */

public interface GamePlaySceneObject {
	/**
	 * This method will be used to update the object every frame.
	 * @param dt The time that has passed since the last frame (ms)
	 * @param kCodes The set of keys that are pressed down
	 */
	void update(int dt, Set<Integer> kCodes);
	
	/**
	 * Gets the position of the object.
	 * @return The position of the object in the scene
	 */
	Vec getPos();
	
	/**
	 * Renders the object.
	 * @return The image of this object to be rendered into the scene
	 */
	BufferedImage render();
	
	/**
	 * Returns the image's offset. Since images have their origin in the
	 * top-left corner, an offset is required so the object's position is at
	 * the center of the image.
	 * @return The vector to add to to the top-left corner to get to the center
	 */
	Vec renderOffset();
	
	/**
	 * Determines the drawing order on the scene. Lower z-values are drawn
	 * first.
	 * @return The z-value of the object
	 */
	int getZ();
}
