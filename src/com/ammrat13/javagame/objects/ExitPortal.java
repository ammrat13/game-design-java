package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;

/**
 * The object in the scene that takes the player to the exit.
 *
 * @author Ammar Ratnani
 */

public class ExitPortal implements GamePlaySceneObject {
	
	/** The gameplay scene passed in from above. */
	private GamePlayScene gps;
	
	/** The value specifying the dimensions of the portal. */
	private final int L = 50;
	/** The value specifying the dimensions of the portal in map mode. */
	private final int LMAP = 10;
	
	/** The position of the object. */
	private Vec x;
	
	/**
	 * Constructs the portal.
	 * @param gps The gameplay scene passed in from above
	 * @param x The position of the portal
	 */
	public ExitPortal(GamePlayScene gps, Vec x){
		this.gps = gps;
		this.x = x;
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
				// Flag that the player has won
				gps.gm.pubVars.put("Won", null);
				gps.gm.setActive("WinScene");
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
		
		g2d.setColor(new Color(255,255,122));
		g2d.fillOval(0, 0, L, L);
		
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
		
		g2d.setColor(new Color(255,255,122));
		g2d.fillOval(0, 0, LMAP, LMAP);
		
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
		return -1;
	}
	
}
