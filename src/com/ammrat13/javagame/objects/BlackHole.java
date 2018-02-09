package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * An object that attempts to suck the player in.
 *
 * @author Ammar Ratnani
 */

public class BlackHole implements GamePlaySceneObject {
	
	/** The gameplay scene passed in from above. */
	private GamePlayScene gps;
	
	/** The value specifying the dimensions of the black hole. */
	private final int L = 50;
	/** The value specifying the thickness of the outline of the black hole. */
	private final int MAR = 5;
	
	/** The value specifying the value {G*M} of the black hole. */
	public final double GM = 50.0;
	
	/** The position of the object. */
	private Vec x;
	
	/**
	 * Constructs the black hole.
	 * @param gps The gameplay scene passed in from above
	 * @param x The position of the portal
	 */
	public BlackHole(GamePlayScene gps, Vec x){
		this.gps = gps;
		this.x = x;
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt, Set<Integer> kCodes){
		Set<GamePlaySceneObject> spaceships = gps.getObjsOfClass("Spaceship");
		for(GamePlaySceneObject gpso : spaceships){
			if(gpso.getPos().add(x.mul(-1)).abs() <= getRadius()+gpso.getRadius()) {
				gps.resetOnLoad();
				gps.gm.setActive("LoseScene");
			}
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec getPos(){
		return x;
	}
	
	/** {@inheritDoc} */
	public double getRadius(){
		return L/8.0;
	}
	
	/** {@inheritDoc} */
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(L, L, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		// Concentric ovals
		g2d.setColor(Color.WHITE);
		g2d.fillOval(0, 0, L, L);
		g2d.setColor(Color.BLACK);
		g2d.fillOval(MAR, MAR, L-2*MAR, L-2*MAR);
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec renderOffset(){
		return new Vec(-L/2.0,-L/2.0);
	}
	
	/** {@inheritDoc} */
	@Override
	public int getZ(){
		return 1;
	}
	
}
