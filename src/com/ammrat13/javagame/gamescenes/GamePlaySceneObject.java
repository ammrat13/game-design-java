package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.util.Vec;

import java.awt.image.BufferedImage;

/**
 * This interface provides the methods an object in the game scene must
 * implement
 *
 * @author Ammar Ratnani
 */

public interface GamePlaySceneObject {
	void update();				// To be called every frame
	void keyDown(int kCode);	// To be called when a key is down
	Vec getPos();
	BufferedImage render();
	Vec renderOffset();
}
