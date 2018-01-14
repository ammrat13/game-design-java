package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.GameManager;
import com.ammrat13.javagame.GameScene;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This scene contains the game itself.
 *
 * @author Ammar Ratnani
 */

public class GamePlayScene implements GameScene {
	
	private GameManager gm;
	
	public GamePlayScene(GameManager gm){
		this.gm = gm;
	}
	
	@Override
	public void start(){
	
	}
	
	@Override
	public void stop(){
	
	}
	
	@Override
	public void update(){
	
	}
	
	@Override
	public void keyDown(int kCode){
	
	}
	
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(gm.WIDTH, gm.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0, gm.WIDTH, gm.HEIGHT);
		
		return ret;
	}
	
}
