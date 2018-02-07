package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Sound;
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
	
	/** The gameplay scene passed in from above. */
	private GamePlayScene gps;
	
	/** The position of the ship. */
	private Vec x;
	/** The velocity of the ship. */
	private Vec v;
	/** The acceleration of the ship. */
	private static final double a = 0.0001;
	
	/** The angle of the ship above the right in radians. */
	private double theta;
	/** The maximum angular velocity of the ship. */
	private static final double omega = 0.1;
	
	/** Whether the ship is firing its engines. */
	private boolean f;
	
	/** The parameter determining the size of the ship. */
	private static final int L = 70;
	
	/** The name of the file for the sound for when the engines are firing. */
	private static final String FIRING_SOUND = "res/FiringEffect.wav";
	/** The sound clip for when the engines are firing. */
	private Clip firingSoundClip;
	
	/**
	 * Constructs the spaceship.
	 * @param gps The game play scene passed in from above
	 * @param xi The initial position of the ship
	 * @param vi The initial velocity of the ship
	 * @param t The initial angle of the ship
	 */
	public Spaceship(GamePlayScene gps, Vec xi, Vec vi, double t){
		this.gps = gps;
		
		x = xi.copy();
		v = vi.copy();
		theta = t;
		f = false;
		
		// Sound
		firingSoundClip = Sound.getSoundClip(FIRING_SOUND);
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec getPos(){
		return x;
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt, Set<Integer> kCodes){
		// Rotation
		if(kCodes.contains(KeyEvent.VK_LEFT))
			theta += omega;
		if(kCodes.contains(KeyEvent.VK_RIGHT))
			theta -= omega;
		
		// Acceleration and firing sound
		if(kCodes.contains(KeyEvent.VK_UP)){
			f = true;
			v = v.add(new Vec(Math.cos(theta), Math.sin(theta)).mul(a*dt));
			
			// If we are near the end of the clip, rewind
			if(firingSoundClip.getFramePosition() >= firingSoundClip.getFrameLength() - 1000)
				firingSoundClip.setFramePosition(0);
			firingSoundClip.start();
		} else {
			f = false;
			firingSoundClip.stop();
		}
		
		x = x.add(v.mul(dt));
	}
	
	/** {@inheritDoc} */
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
	
	/** {@inheritDoc} */
	@Override
	public Vec renderOffset(){
		return new Vec(-L, -L);
	}
	
	/** {@inheritDoc} */
	@Override
	public int getZ(){
		return 0;
	}
	
}
