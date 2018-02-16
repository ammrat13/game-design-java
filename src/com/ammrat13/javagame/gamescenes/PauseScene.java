package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.GameManager;
import com.ammrat13.javagame.objects.GamePlaySceneObject;
import com.ammrat13.javagame.util.Image;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * This scene is what the player sees when they pause the game. It has a map of
 * the entire level with the predicted path.
 *
 * @author Ammar Ratnani
 */
public class PauseScene implements GameScene {
	
	/** The game manager passed in from above. */
	private GameManager gm;
	
	/** The margins on the lose scene. */
	private final int MAR = 30;
	/** The center of the screen. */
	private final int CEN;
	
	/** The height of the title block. */
	private final int TBH = 120;
	/** The font of the title. */
	private final Font TFON = new Font(null, Font.BOLD, 70);
	
	/** The scale of the map as a decimal. */
	private double mSC = 0.3;
	
	/** This will store the keys we have down currently so we don't double count. */
	private Set<Integer> kCodes;
	
	/**
	 * Constructs the pause scene. Takes the game manager as input.
	 * @param gm The game manager passed in from above
	 */
	public PauseScene(GameManager gm){
		this.gm = gm;
		CEN = gm.WIDTH/2;
	}
	
	/** {@inheritDoc} */
	@Override
	public void start(){
		kCodes = new HashSet<>(gm.keysDown);
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop(){
	
	}
	
	/** {@inheritDoc} */
	@Override
	public void resetOnLoad(){
	
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt){
		if(!gm.keysDown.contains(KeyEvent.VK_ESCAPE))
			kCodes.remove(KeyEvent.VK_ESCAPE);
		
		// We only need to check the escape key
		if(gm.keysDown.contains(KeyEvent.VK_ESCAPE) && !kCodes.contains(KeyEvent.VK_ESCAPE)) {
			kCodes.add(KeyEvent.VK_ESCAPE);
			gm.setActive("GamePlayScene");
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(gm.WIDTH, gm.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		// Background
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0, gm.WIDTH, gm.HEIGHT);
		
		// Center the map
		g2d.translate(CEN, 2*MAR+TBH + (gm.HEIGHT-4*MAR-TBH)/2);
		g2d.transform(new AffineTransform(1, 0, 0, -1, 0, 0));
		
		// Get each game object and render it
		GamePlayScene gps = (GamePlayScene) gm.getScene("GamePlayScene");
		Vec pPosCor = gps.player.getPos().mul(-1);
		for(GamePlaySceneObject gpso : gps.getObjsOfClass("(?s).*")){
			g2d.drawImage(
				Image.flipVert(gpso.mapRender()),
				(int) gpso.getPos().add(pPosCor).mul(mSC).add(gpso.mapRenderOffset()).x,
				(int) gpso.getPos().add(pPosCor).mul(mSC).add(gpso.mapRenderOffset()).y,
				null
			);
		}
		
		// Do this second to cover everything out of bounds
		// Revert changes
		g2d.transform(new AffineTransform(1, 0, 0, -1, 0, 0));
		g2d.translate(-CEN, -2*MAR-TBH - (gm.HEIGHT-4*MAR-TBH)/2);
		
		// Title block
		g2d.setColor(Color.WHITE);
		g2d.drawRect(MAR, MAR,gm.WIDTH-2*MAR, TBH);
		g2d.setFont(TFON);
		g2d.drawString("Pause", CEN - 100, MAR + TBH/2 + 20);
		
		// Map outline
		g2d.drawRect(MAR, 2*MAR+TBH, gm.WIDTH-2*MAR, gm.HEIGHT-4*MAR-TBH);
		
		return ret;
	}

}
