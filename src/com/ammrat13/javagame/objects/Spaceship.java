package com.ammrat13.javagame.objects;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.util.Sound;
import com.ammrat13.javagame.util.Vec;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
	public Vec x;
	/** The velocity of the ship. */
	private Vec v;
	/** The acceleration of the ship. */
	private static final double aeng = 0.0001;
	
	/** The angle of the ship above the right in radians. */
	private double theta;
	/** The maximum angular velocity of the ship. */
	private static final double omega = 0.1;
	
	/** Whether the ship is firing its engines. */
	private boolean f;
	
	/** The parameter determining the size of the ship. */
	private static final int L = 70;
	/** The parameter determining the size of the ship on the map. */
	private static final int mL = 10;
	
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
		
		// Scoring
		gps.gm.pubVars.put("Score", 0);
		
		start();
	}
	
	/**
	 * Returns a list of position vectors that the spaceship will be at in the
	 * next {@code n} frames. Engines are not taken into account; only black
	 * holes are.
	 * @param n How far ahead to predict
	 * @param dt The frame step
	 * @return The list of predicted position vectors
	 */
	public ArrayList<Vec> predict(int n, int dt){
		ArrayList<Vec> ret = new ArrayList<>();
		Vec xc = x.copy();
		Vec vc = v.copy();
		
		ret.add(xc);
		for(int i=0;i<n;i++){
			// Acceleration logic
			for(GamePlaySceneObject gpso : gps.getObjsOfClass("BlackHole")){
				try {
					BlackHole bh = (BlackHole) gpso;
					Vec r = xc.add(bh.getPos().mul(-1));
					vc = vc.add(r.norm().mul( -1 * bh.GM/(r.abs()*r.abs()) ));
				} catch(ClassCastException e){
					e.printStackTrace();
				}
			}
			// Compute new position and update
			xc = xc.add(vc.mul(dt));
			ret.add(xc);
		}
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public void start(){
		// Sound
		firingSoundClip = Sound.getSoundClip("res/FiringEffect.wav");
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop(){
		firingSoundClip.stop();
		firingSoundClip = null;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec getPos(){
		return x;
	}
	
	/** {@inheritDoc} */
	@Override
	public double getRadius(){
		return L/2.0;
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
			// Increment the score
			gps.gm.pubVars.put("Score", (Integer) gps.gm.pubVars.get("Score") + 1 );
			
			f = true;
			v = v.add(new Vec(Math.cos(theta), Math.sin(theta)).mul(aeng*dt));
			if(firingSoundClip != null) {
				// If we are near the end of the clip, rewind
				if(firingSoundClip.getFramePosition() >= firingSoundClip.getFrameLength() - 10)
					firingSoundClip.setFramePosition(0);
				firingSoundClip.start();
			}
		} else {
			f = false;
			if(firingSoundClip != null)
				firingSoundClip.stop();
		}
		
		// Black Holes
		for(GamePlaySceneObject gpso : gps.getObjsOfClass("BlackHole")){
			try {
				BlackHole bh = (BlackHole) gpso;
				Vec r = x.add(bh.getPos().mul(-1));
				v = v.add(r.norm().mul( -1 * bh.GM/(r.abs()*r.abs()) ));
			} catch(ClassCastException e){
				e.printStackTrace();
			}
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
		g2d.rotate(-theta);
		
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
	public BufferedImage mapRender() {
		BufferedImage ret = new BufferedImage(mL, (int) (mL*Math.sqrt(3)/2), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		// A white triangle
		g2d.setColor(Color.WHITE);
		g2d.fillPolygon(new int[]{0,mL/2,mL}, new int[]{(int) (mL*Math.sqrt(3)/2),0,(int) (mL*Math.sqrt(3)/2)}, 3);
		
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public Vec mapRenderOffset() {
		return new Vec(-mL/2, (int) (-mL*Math.sqrt(3)/4));
	}
	
	/** {@inheritDoc} */
	@Override
	public int getZ(){
		return 0;
	}
	
}
