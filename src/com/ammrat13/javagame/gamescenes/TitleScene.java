package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.GameManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is displays the title for the game.
 *
 * @author Ammar Ratnani
 */

public class TitleScene implements GameScene {
	
	/** The game manager passed in from above. */
	private GameManager gm;
	
	/** The margins on the title scene. */
	private final int MAR = 30;
	/** The center of the screen. */
	private final int CEN;
	
	/** The height of the title block. */
	private final int TBH = 120;
	/** The font of the title. */
	private final Font TFON = new Font(null, Font.BOLD, 70);
	
	/** The height of a button. */
	private final int BUTH = 70;
	/** The font of the button text. */
	private final Font BFON = new Font(null, Font.BOLD, 50);
	
	/** The width of the arrow. */
	private final int AW = 30;
	/** The height of the arrow. */
	private final int AH = 30;
	
	/** The list of all the buttons we have. */
	private String[] buts = new String[]{"Start Game", "Other thing", "Other thing"};
	/** The list of the scenes the buttons correspond to. */
	private String[] butScen = new String[]{"GamePlayScene", null, null};
	/** Stores the index of the active button. */
	private int actI = 0;
	
	/** Stores the keys we have previously registered so we don't recount. */
	private Set<Integer> kCodesDown = new HashSet<>();
	
	/**
	 * Constructs the scene. Takes the game manager as input.
	 * @param gm The game manager passed in from above
	 */
	public TitleScene(GameManager gm){
		this.gm = gm;
		CEN = gm.WIDTH/2;
	}
	
	/** {@inheritDoc} */
	@Override
	public void start(){
	
	}
	
	/** {@inheritDoc} */
	@Override
	public void stop(){
	
	}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt, Set<Integer> kCodes){
		// Check if we are still registering a key that is no longer down
		if(!kCodes.contains(KeyEvent.VK_DOWN) && kCodesDown.contains(KeyEvent.VK_DOWN))
			kCodesDown.remove(KeyEvent.VK_DOWN);
		if(!kCodes.contains(KeyEvent.VK_UP) && kCodesDown.contains(KeyEvent.VK_UP))
			kCodesDown.remove(KeyEvent.VK_UP);
		
		// Register the keys we need to and move the cursor, checking if in bounds
		if(kCodes.contains(KeyEvent.VK_DOWN) && !kCodesDown.contains(KeyEvent.VK_DOWN)){
			kCodesDown.add(KeyEvent.VK_DOWN);
			actI = Math.min(actI+1, buts.length-1);
		}
		if(kCodes.contains(KeyEvent.VK_UP) && !kCodesDown.contains(KeyEvent.VK_UP)){
			kCodesDown.add(KeyEvent.VK_UP);
			actI = Math.max(actI-1, 0);
		}
		
		// If the user hits enter, switch scenes
		if(kCodes.contains(KeyEvent.VK_ENTER))
			gm.setActive(butScen[actI]);
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
		g2d.drawString("GET HOME", CEN - 190, MAR + TBH/2 + 20);
		
		// Buttons
		for(int b=0; b<buts.length; b++) {
			g2d.drawRect(gm.WIDTH / 3, butTopY(b), gm.WIDTH / 3, BUTH);
			g2d.setFont(BFON);
			g2d.drawString(buts[b], CEN - 140, butTopY(b) + BUTH / 2 + 20);
		}
		
		// Active button
		g2d.fillPolygon(
			new int[]{gm.WIDTH/3 - MAR, gm.WIDTH/3 - MAR - AW, gm.WIDTH/3 - MAR - AW},
			new int[]{butTopY(actI) + BUTH/2, butTopY(actI) + BUTH/2 - AH/2, butTopY(actI) + BUTH/2 + AH/2},
			3
		);
		
		return ret;
	}
	
	/**
	 * Returns the y coordinate of the top of the specified button.
	 * @param n The number of the specified button indexed from 0
	 * @return The y coordinate
	 */
	private int butTopY(int n){
		return (n+3)*MAR + TBH + n*BUTH;
	}
	
}
