package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlaySceneObject;
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
	
	private Vec x;
	private Vec v;
	
	public TestMovingObject(Vec xi, Vec vi){
		x = xi.copy();
		v = vi.copy();
	}
	
	@Override
	public void update(int dt, Set<Integer> kCodes){
		System.out.println(dt);
		if(kCodes.contains(KeyEvent.VK_LEFT))
			v = v.add(new Vec(-.2, 0).mul(dt/1000.0));
		if(kCodes.contains(KeyEvent.VK_RIGHT))
			v = v.add(new Vec(.2, 0).mul(dt/1000.0));
		if(kCodes.contains(KeyEvent.VK_UP))
			v = v.add(new Vec(0, .2).mul(dt/1000.0));
		if(kCodes.contains(KeyEvent.VK_DOWN))
			v = v.add(new Vec(0, -.2).mul(dt/1000.0));
		x = x.add(v.mul(dt));
	}
	
	@Override
	public Vec getPos(){return x;}
	
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.RED);
		g2d.fillRect(0,0,10,10);
		
		return ret;
	}
	
	@Override
	public Vec renderOffset(){return new Vec(-5,-5);}
	
}
