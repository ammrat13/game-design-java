package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;

/**
 * This class represents a key required to hit the exit portal.
 *
 * @author Ammar Ratnani
 */
public class Key implements GamePlaySceneObject {
	
	/** A parameter describing how big our key appears. */
	private final int L = 20;
	/** A parameter describing how big our key appears on the map screen. */
	private final int ML = 5;
	
	/** The game play scene passed in from above. */
	private GamePlayScene gps;
	
	/** Whether or not this key has been collected. */
	private boolean col;
	/** The position of this key. */
	private Vec x;
	
	public Key(GamePlayScene gps, Vec x, boolean c){
		this.gps = gps;
		this.x = x;
		col = c;
		
		if(!gps.gm.pubVars.containsKey("Keys"))
			gps.gm.pubVars.put("Keys", 0);
		
		if(!gps.gm.pubVars.containsKey("KeysReq"))
			gps.gm.pubVars.put("KeysReq", 1);
		else
			gps.gm.pubVars.put("KeysReq", (Integer)(gps.gm.pubVars.get("KeysReq"))+1);
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
		// Only check if the spaceship is close enough if we haven't been collected
		if(!col) {
			ArrayList<GamePlaySceneObject> spaceships = gps.getObjsOfClass("Spaceship");
			for(GamePlaySceneObject gpso : spaceships) {
				if(gpso.getPos().add(x.mul(-1)).abs() <= getRadius() + gpso.getRadius()) {
					gps.gm.pubVars.put("Keys", (Integer) (gps.gm.pubVars.get("Keys")) + 1);
					col = true;
				}
			}
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec getPos(){
		return x;
	}
	
	/** {@inheritDoc} */
	@Override
	public double getRadius(){
		return L;
	}
	
	/** {@inheritDoc} */
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(L, 2*L, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		if(!col) {
			// Top part
			g2d.setColor(new Color(255, 255, 122));
			g2d.fillOval(0, 0, L, L);
			g2d.setColor(Color.BLACK);
			g2d.fillOval(L / 5, L / 5, 3 * L / 5, 3 * L / 5);
			
			// Bottom Part
			g2d.setColor(new Color(255, 255, 122));
			g2d.fillRect(4 * L / 10, 9 * L / 10, 2 * L / 10, 11 * L / 10);
			g2d.fillRect(L / 2, 16 * L / 10, L / 4, L / 4);
		}
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec renderOffset(){
		return new Vec(-L/2, -L);
	}
	
	/** {@inheritDoc} */
	@Override
	public BufferedImage mapRender(){
		BufferedImage ret = new BufferedImage(ML, ML, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		if(!col) {
			g2d.setColor(new Color(255, 255, 122));
			g2d.fillOval(0, 0, ML, ML);
		}
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec mapRenderOffset(){
		return new Vec(-ML/2, -ML/2);
	}
	
	/** {@inheritDoc} */
	@Override
	public int getZ(){
		return -1;
	}
	
}
