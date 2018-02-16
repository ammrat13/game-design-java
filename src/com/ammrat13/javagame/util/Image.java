package com.ammrat13.javagame.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * This class provides utility methods for dealing with images.
 *
 * @author Ammar Ratnani
 */
public class Image {
	
	/**
	 * This method will take in an image and return the result of that image
	 * being flipped vertically.
	 * @param image The image to flip
	 * @return The flipped image or {@code null}
	 */
	public static BufferedImage flipVert(BufferedImage image){
		if(image != null) {
			// Copied from StackOverflow
			AffineTransform at = new AffineTransform();
			at.concatenate(AffineTransform.getScaleInstance(1, -1));
			at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
			
			BufferedImage newImage = new BufferedImage(
					image.getWidth(), image.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = newImage.createGraphics();
			g.transform(at);
			g.drawImage(image, 0, 0, null);
			g.dispose();
			return newImage;
		}
		return null;
	}
	
}
