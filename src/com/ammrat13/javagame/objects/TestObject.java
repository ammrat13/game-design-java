package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlaySceneObject;
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
	
	private Vec pos;
	
	public TestObject(double x, double y){
		pos = new Vec(x,y);
	}
	
	@Override
	public void update(int dt, Set<Integer> kCodes){}
	
	@Override
	public Vec getPos(){return pos;}
	
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0,0,10,10);
		
		return ret;
	}
	
	@Override
	public Vec renderOffset(){return new Vec(-5,-5);}
	
}
