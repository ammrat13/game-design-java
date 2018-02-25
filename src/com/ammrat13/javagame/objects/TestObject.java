package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * An object that does nothing. For testing purposes.
 *
 * @author Ammar Ratnani
 */

public class TestObject implements GamePlaySceneObject {
	
	/** The fixed position of the object. */
	private Vec x;
	
	/**
	 * Constructs the object.
	 * @param gps The game play scene passed in from above
	 * @param x The position of the object
	 */
	public TestObject(GamePlayScene gps, Vec x){
		this.x = x.copy();
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt, Set<Integer> kCodes){}
	
	/** {@inheritDoc} */
	@Override
	public Vec getPos(){return x;}
	
	/** {@inheritDoc} */
	@Override
	public double getRadius(){
		return 5;
	}
	
	/** {@inheritDoc} */
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0,0,10,10);
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec renderOffset(){return new Vec(-5,-5);}
	
	/** {@inheritDoc} */
	@Override
	public BufferedImage mapRender() {
		BufferedImage ret = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 4, 4);
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec mapRenderOffset() {
		return new Vec(-2, -2);
	}
	
	/** {@inheritDoc} */
	@Override
	public int getZ(){
		return -2;
	}
	
}
