package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.GameManager;

import java.awt.*;
import java.awt.event.KeyEvent;
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
