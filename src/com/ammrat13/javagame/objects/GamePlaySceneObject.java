package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
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
	void update(int dt, Set<Integer> kCodes, GamePlayScene gps);		// To be called every frame
	Vec getPos();
	BufferedImage render();
	Vec renderOffset();
	int getZ();
}
