package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.gamescenes.GamePlaySceneObject;
import com.ammrat13.javagame.util.Vec;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * This class is the main character.
 *
 * @author Ammar Ratnani
 */

public class Spaceship implements GamePlaySceneObject {
	
	private Vec x;
	private Vec v;
	private static final double a = 0.00003;
	
	private double theta; // Above the right in radians
	private static final double omega = 0.05;
	
	private boolean f;
	
	private static final int L = 70;
	
	private static final String FIRING_SOUND = "sound/FiringEffect.wav";
	private Clip firingSoundClip;
	
	public Spaceship(Vec xi, Vec vi, double theta){
		x = xi.copy();
		v = vi.copy();
		this.theta = theta;
		f = false;
	}
	
	@Override
	public Vec getPos(){
		return x;
	}
	
	@Override
	public void update(int dt, Set<Integer> kCodes, GamePlayScene gps){
		// Rotation
		if(kCodes.contains(KeyEvent.VK_LEFT))
			theta += omega;
		if(kCodes.contains(KeyEvent.VK_RIGHT))
			theta -= omega;
		
		// Acceleration
		if(kCodes.contains(KeyEvent.VK_UP)){
			f = true;
			v = v.add(new Vec(Math.cos(theta), Math.sin(theta)).mul(a*dt));
			
			// If we haven't even started playing sound
			if(firingSoundClip == null)
				firingSoundClip = gps.gm.getSoundClip(FIRING_SOUND);
			// If we are near the end of the clip, rewind
			if(firingSoundClip.getFramePosition() >= firingSoundClip.getFrameLength() - 1000)
				firingSoundClip.setFramePosition(0);
			firingSoundClip.start();
		} else {
			f = false;
			// Stop the sound if it exists
			if(firingSoundClip != null) {
				firingSoundClip.stop();
			}
		}
		
		x = x.add(v.mul(dt));
	}
	
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(2*L, 2*L, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		// Origin is center
		g2d.translate(L, L);
		g2d.rotate(theta);
		
		// Main body
		g2d.setColor(Color.WHITE);
		g2d.fillPolygon(new int[]{L/2,-L/6,-L/6}, new int[]{0,L/4,-L/4}, 3);
		g2d.fillPolygon(new int[]{0,-L/4,-L/4}, new int[]{L/8,9*L/40,L/40}, 3);
		g2d.fillPolygon(new int[]{0,-L/4,-L/4}, new int[]{-L/8,-9*L/40,-L/40}, 3);
		
		// Flame trails
		g2d.setColor(Color.RED);
		if(f){
			g2d.fillPolygon(new int[]{-L/4,-L/4,-L}, new int[]{L/5,L/20,L/8}, 3);
			g2d.fillPolygon(new int[]{-L/4,-L/4,-L}, new int[]{-L/5,-L/20,-L/8}, 3);
		}
		
		// Draw axis of rotation
		g2d.setColor(Color.BLACK);
		g2d.fillOval(-4,-4,8,8);
		
		return ret;
	}
	
	@Override
	public Vec renderOffset(){
		return new Vec(-L, -L);
	}
	
	@Override
	public int getZ(){
		return 0;
	}
	
}
