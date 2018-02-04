package com.ammrat13.javagame.gamescenes;


import com.ammrat13.javagame.GameManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * This class is displays the instructions for the game.
 *
 * @author Ammar Ratnani
 */

public class InstructionScene implements GameScene {
	
	/** The game manager passed in from above. */
	private GameManager gm;
	
	/** The margins on the instructions scene. */
	private final int MAR = 30;
	/** The center of the screen. */
	private final int CEN;
	
	/** The height of the title block. */
	private final int TBH = 120;
	/** The font of the title. */
	private final Font TFON = new Font(null, Font.BOLD, 70);
	
	/** The font of the instructions. */
	private final Font IFON = new Font(null, 0, 20);
	/** The instruction content. */
	private final String INSTSTR = "Controls:\nUP: Accelerate\nLEFT: Turn Left\nRIGHT: Turn Right\nESC: Pause/Exit";
	
	/**
	 * Constructs the scene. Takes the game manager as input.
	 * @param gm The game manager passed in from above
	 */
	public InstructionScene(GameManager gm){
		this.gm = gm;
		CEN = gm.WIDTH/2;
	}
	
	/** {@inheritDoc} */
	@Override
	public void start(){}
	
	/** {@inheritDoc} */
	@Override
	public void stop(){}
	
	/** {@inheritDoc} */
	@Override
	public void update(int dt){
		if(gm.keysDown.contains(KeyEvent.VK_ESCAPE))
			gm.setActive("TitleScene");
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
		
		// Text box
		g2d.setFont(IFON);
		g2d.drawRect(MAR, 2*MAR+TBH, gm.WIDTH-2*MAR, gm.HEIGHT-4*MAR-TBH);
		// Draw string doesn't split newlines
		int yCurr = 3*MAR + TBH - (int) (.3*g2d.getFontMetrics().getHeight());
		for(String s : INSTSTR.split("\n"))
			g2d.drawString(s,2*MAR, yCurr += (int) (1.3*g2d.getFontMetrics().getHeight()));
		
		return ret;
	}

}
