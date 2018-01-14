package com.ammrat13.javagame.gamescenes;

import com.ammrat13.javagame.GameManager;
import com.ammrat13.javagame.GameScene;
import com.ammrat13.javagame.objects.TestObject;
import com.ammrat13.javagame.util.Vec;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This scene contains the game itself.
 *
 * @author Ammar Ratnani
 */

public class GamePlayScene implements GameScene {
	
	// The vector to add so (0,0) is the center of the screen
	private final Vec corr;
	
	private GameManager gm;
	
	private ArrayList<GamePlaySceneObject> gpsos;
	
	public GamePlayScene(GameManager gm){
		this.gm = gm;
		corr = new Vec(gm.WIDTH/2, gm.HEIGHT/2);
		
		gpsos = new ArrayList<>();
		gpsos.add(new TestObject(0,0));
	}
	
	@Override
	public void start(){}
	
	@Override
	public void stop(){}
	
	@Override
	public void update(){
		for(GamePlaySceneObject gpso : gpsos)
			gpso.update();
	}
	
	@Override
	public void keyDown(int kCode){
		for(GamePlaySceneObject gpso : gpsos)
			gpso.keyDown(kCode);
	}
	
	@Override
	public BufferedImage render(){
		BufferedImage ret = new BufferedImage(gm.WIDTH, gm.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) ret.getGraphics();
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0, gm.WIDTH, gm.HEIGHT);
		
		// Make the first object the one that is centered
		Vec fPosCorr = gpsos.get(0).getPos().mul(-1);
		for(GamePlaySceneObject gpso : gpsos){
			g2d.drawImage(
					gpso.render(),
					(int) (gpso.getPos().add(gpso.renderOffset()).add(corr).add(fPosCorr)).x,
					(int) (gpso.getPos().add(gpso.renderOffset()).add(corr).add(fPosCorr)).y,
					null
			);
		}
		
		return ret;
	}
	
}
