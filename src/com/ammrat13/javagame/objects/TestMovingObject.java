package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * An object that does nothing, but it moves with keys. For testing purposes.
 *
 * @author Ammar Ratnani
 */

public class TestMovingObject implements GamePlaySceneObject {
	
	/** The position of the object. */
	private Vec x;
	/** The velocity of the object. */
	private Vec v;
	/** The acceleration of the object. */
	private static final double a = 0.0001;
	
	/**
	 * Constructs the object.
	 * @param gps The game play scene passed in from above
	 * @param xi The initial position of the object
	 * @param vi The initial velocity of the object
	 */
	public TestMovingObject(GamePlayScene gps, Vec xi, Vec vi){
		x = xi.copy();
		v = vi.copy();
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt, Set<Integer> kCodes){
		System.out.println(dt);
		if(kCodes.contains(KeyEvent.VK_LEFT))
			v = v.add(new Vec(-a, 0).mul(dt));
		if(kCodes.contains(KeyEvent.VK_RIGHT))
			v = v.add(new Vec(a, 0).mul(dt));
		if(kCodes.contains(KeyEvent.VK_UP))
			v = v.add(new Vec(0, a).mul(dt));
		if(kCodes.contains(KeyEvent.VK_DOWN))
			v = v.add(new Vec(0, -a).mul(dt));
		x = x.add(v.mul(dt));
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec getPos(){
		return x;
	}
	
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
		
		g2d.setColor(Color.RED);
		g2d.fillRect(0,0,10,10);
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec renderOffset(){
		return new Vec(-5,-5);
	}
	
	/** {@inheritDoc} */
	@Override
	public BufferedImage mapRender() {
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec mapRenderOffset() {
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public int getZ(){
		return 0;
	}
	
}
