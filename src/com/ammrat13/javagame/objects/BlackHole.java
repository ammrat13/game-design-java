package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;

/**
 * An object that attempts to suck the player in.
 *
 * @author Ammar Ratnani
 */

public class BlackHole implements GamePlaySceneObject {
	
	/** The gameplay scene passed in from above. */
	private final GamePlayScene gps;
	
	/** The value specifying the dimensions of the black hole. */
	private final int L = 50;
	/** The value specifying the dimensions of the black hole in map mode. */
	private final int LMAP = 10;
	/** The value specifying the thickness of the outline of the black hole. */
	private final int MAR = 5;
	
	/** The value specifying the value {@code G*M} of the black hole. */
	public final double GM;
	
	/** The position of the object. */
	private final Vec x;
	
	/**
	 * Constructs the black hole.
	 * @param gps The gameplay scene passed in from above
	 * @param x The position of the portal
	 * @param GM The {@code G*M} of this black hole
	 */
	public BlackHole(GamePlayScene gps, Vec x, double GM){
		this.gps = gps;
		this.x = x;
		this.GM = GM;
	}
	
	/** {@inheritDoc} */
	@Override
	public void start(){
	
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop(){
	
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt, Set<Integer> kCodes){
		ArrayList<GamePlaySceneObject> spaceships = gps.getObjsOfClass("Spaceship");
		for(GamePlaySceneObject gpso : spaceships){
			if(gpso.getPos().add(x.mul(-1)).abs() <= getRadius()+gpso.getRadius()) {
				gps.resetOnLoad();
				gps.gm.pubVars.put("Lost", null);
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
	public BufferedImage mapRender() {
		BufferedImage ret = new BufferedImage(LMAP, LMAP, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.WHITE);
		g2d.drawOval(0, 0, LMAP, LMAP);
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec mapRenderOffset() {
		return new Vec(-LMAP/2, -LMAP/2);
	}
	
	/** {@inheritDoc} */
	@Override
	public int getZ(){
		return 1;
	}
	
}
