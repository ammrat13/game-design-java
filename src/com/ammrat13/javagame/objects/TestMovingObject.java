package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlaySceneObject;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * An object that does nothing, but it moves with keys. For testing purposes.
 *
 * @author Ammar Ratnani
 */

public class TestMovingObject implements GamePlaySceneObject {
	
	private Vec pos;
	
	public TestMovingObject(double x, double y){
		pos = new Vec(x,y);
	}
	
	@Override
	public void update(){}
	
	@Override
	public void keyDown(int kCode){
		if(kCode == KeyEvent.VK_LEFT)
			pos = pos.add(new Vec(-.1,0));
		if(kCode == KeyEvent.VK_RIGHT)
			pos = pos.add(new Vec(.1,0));
		if(kCode == KeyEvent.VK_UP)
			pos = pos.add(new Vec(0,.1));
		if(kCode == KeyEvent.VK_DOWN)
			pos = pos.add(new Vec(0,-.1));
	}
	
	@Override
	public Vec getPos(){return pos;}
	
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
