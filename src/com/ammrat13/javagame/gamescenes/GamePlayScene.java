package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.GameManager;
import com.ammrat13.javagame.objects.GamePlaySceneObject;
import com.ammrat13.javagame.objects.Spaceship;
import com.ammrat13.javagame.objects.TestObject;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

/**
 * This scene contains the game itself.
 *
 * @author Ammar Ratnani
 */

public class GamePlayScene implements GameScene {
	
	// Passed in from above
	public GameManager gm;
	
	// All the objects in the game scene
	private final ArrayList<GamePlaySceneObject> gpsos;
	// The main player
	private GamePlaySceneObject player;
	
	public GamePlayScene(GameManager gm){
		this.gm = gm;
		
		// Scene setup
		gpsos = new ArrayList<>();
		gpsos.add(new TestObject(0,0));
		// Both player and the reference in gpsos point to the same object
		player = new Spaceship(this, new Vec(0,0), new Vec(0,0), Math.toRadians(0));
		gpsos.add(player);
		
		// Sort the objects by z value, so the ones with less get rendered first
		gpsos.sort(new Comparator<>() {
			@Override
			public int compare(GamePlaySceneObject o1, GamePlaySceneObject o2) {
				return Integer.compare(o1.getZ(), o2.getZ());
			}
		});
		
		start();
	}
	
	/**
	 * Returns all the objects of the class specified.
	 *
	 * @param cName The name of the class
	 */
	public ArrayList<GamePlaySceneObject> getObjsOfClass(String cName){
		ArrayList<GamePlaySceneObject> ret = new ArrayList<>();
		for(GamePlaySceneObject gpso : gpsos){
			if(gpso.getClass().getSimpleName().equals(cName))
				ret.add(gpso);
		}
		return ret;
	}
	
	
	@Override
	public void start(){}
	
	@Override
	public void stop(){}
	
	@Override
	public void update(int dt, Set<Integer> kCodes){
		// Update each object
		for(GamePlaySceneObject gpso : gpsos)
			gpso.update(dt, kCodes);
	}
	
	@Override
	public BufferedImage render(){
		// Render all the objects
		BufferedImage ret = new BufferedImage(gm.WIDTH, gm.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0, gm.WIDTH, gm.HEIGHT);
		
		// Origin is center
		g2d.translate(gm.WIDTH/2,gm.HEIGHT/2);
		// Flip vertically
		g2d.transform(new AffineTransform(1, 0, 0, -1, 0, 0));
		
		// Make the player the one that is centered
		Vec fPosCorr = player.getPos().mul(-1);
		for(GamePlaySceneObject gpso : gpsos){
			g2d.drawImage(
					gpso.render(),
					(int) (gpso.getPos().add(gpso.renderOffset()).add(fPosCorr)).x,
					(int) (gpso.getPos().add(gpso.renderOffset()).add(fPosCorr)).y,
					null
			);
		}
		
		return ret;
	}
	
}
