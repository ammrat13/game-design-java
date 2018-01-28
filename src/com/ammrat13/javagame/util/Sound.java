package com.ammrat13.javagame.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * A class solely devoted to sound management. It provides utility methods for
 * dealing with sound.
 */

public class Sound {
	
	/**
	 * Gets the clip for a sound.
	 * @param soundPath The path of the sound to get the clip for
	 * @return The clip object for the sound, or null if failure
	 */
	
	public static Clip getSoundClip(String soundPath){
		// Source: https://www.geeksforgeeks.org/play-audio-file-using-java/
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			return clip;
		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
			e.printStackTrace();
		}
		return null;
	}

}
