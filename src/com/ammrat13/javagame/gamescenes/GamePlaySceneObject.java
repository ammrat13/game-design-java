package com.ammrat13.javagame.gamescenes;

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
	void update(int dt, Set<Integer> kCodes);		// To be called every frame
	Vec getPos();
	BufferedImage render();
	Vec renderOffset();
	int getZ();
}
