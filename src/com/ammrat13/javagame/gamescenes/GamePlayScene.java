package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.GameManager;
import com.ammrat13.javagame.objects.GamePlaySceneObject;
import com.ammrat13.javagame.objects.Spaceship;
import com.ammrat13.javagame.util.Image;
import com.ammrat13.javagame.util.Parse;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * This scene contains the game itself.
 *
 * @author Ammar Ratnani
 */

public class GamePlayScene implements GameScene {
	
	/** The game manager passed in from above. */
	public GameManager gm;
	
	/** A flag storing whether we reset on load. */
	private boolean resetOnLoad = true;
	
	/** A list of the objects in the game scene. */
	private ArrayList<GamePlaySceneObject> gpsos;
	/** The pointer to the main player. */
	public Spaceship player;
	
	/** The file for the level itself. */
	private final String LVLFILE = "res/level.lvl";
	
	/** This will store the keys we have down currently so we don't double count. */
	private Set<Integer> kCodes;
	
	/**
	 * Constructs the scene. Takes the game manager as input.
	 * @param gm The game manager passed in from above
	 */
	public GamePlayScene(GameManager gm){
		this.gm = gm;
		start();
	}
	
	/**
	 * Returns all the objects with a class matching the regex specified
	 * @param cName The name of the class
	 * @return A list of all such objects
	 */
	public ArrayList<GamePlaySceneObject> getObjsOfClass(String cName){
		ArrayList<GamePlaySceneObject> ret = new ArrayList<>();
		for(GamePlaySceneObject gpso : gpsos){
			if(gpso.getClass().getSimpleName().matches(cName))
				ret.add(gpso);
		}
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public void start(){
		kCodes = new HashSet<>(gm.keysDown);
		
		// Only do this if we need to reset
		if(resetOnLoad) {
			// Scene setup
			gpsos = Parse.parseLvl(this, LVLFILE);
			player = (Spaceship) gpsos.get(0);
			// Sort the objects by z value, so the ones with less get rendered first
			gpsos.sort(new Comparator<>() {
				@Override
				public int compare(GamePlaySceneObject o1, GamePlaySceneObject o2) {
					return Integer.compare(o1.getZ(), o2.getZ());
				}
			});
		}
		resetOnLoad = false;
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop(){}
	
	/** {@inheritDoc} */
	@Override
	public void resetOnLoad(){
		resetOnLoad = true;
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt){
		if(!gm.keysDown.contains(KeyEvent.VK_ESCAPE))
			kCodes.remove(KeyEvent.VK_ESCAPE);
		
		// We only need to check the escape key
		if(gm.keysDown.contains(KeyEvent.VK_ESCAPE) && !kCodes.contains(KeyEvent.VK_ESCAPE)) {
			kCodes.add(KeyEvent.VK_ESCAPE);
			gm.setActive("PauseScene");
		}
		
		// Update each object
		for(GamePlaySceneObject gpso : gpsos)
			gpso.update(dt, gm.keysDown);
	}
	
	/** {@inheritDoc} */
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
		
		// Draw the predicted lines before everything else
		g2d.setColor(Color.WHITE);
		ArrayList<Vec> pred = player.predict(2000, gm.UPFREQ);
		for(int i=1; i<pred.size(); i++){
			Vec x1 = pred.get(i-1).add(fPosCorr);
			Vec x2 = pred.get(i).add(fPosCorr);
			g2d.drawLine(
					(int) x1.x, (int) x1.y,
					(int) x2.x, (int) x2.y
			);
		}
		
		// Draw all the objects in increasing z order
		for(GamePlaySceneObject gpso : gpsos){
			g2d.drawImage(
					Image.flipVert(gpso.render()),
					(int) (gpso.getPos().add(gpso.renderOffset()).add(fPosCorr)).x,
					(int) (gpso.getPos().add(gpso.renderOffset()).add(fPosCorr)).y,
					null
			);
		}
		
		return ret;
	}
	
}
