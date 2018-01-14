package com.ammrat13.javagame;

import com.ammrat13.javagame.gamescenes.GamePlayScene;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

/**
 * This class will manage all aspects of the game, including scene cordination.
 * It will start on a given scene, and transition between them.
 *
 * @author Ammar Ratnani
 */

public class GameManager extends JPanel implements KeyListener {
	
	public final int WIDTH;
	public final int HEIGHT;
	
	// Map of all the scenes we have
	public Map<String, GameScene> gss;
	public GameScene active;
	
	// To pass to the active scene
	private Set<Integer> keysDown;
	
	public GameManager(int w, int h){
		WIDTH = w;
		HEIGHT = h;
		keysDown = new HashSet<>();
		
		gss = new HashMap<>();
		gss.put("GamePlay", new GamePlayScene(this));
		setActive("GamePlay");
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, 20);
	}
	
	public void setActive(String ags){
		if(active != null)
			active.stop();
		active = gss.get(ags);
	}
	
	private void update(){
		// Key handling
		for(int c : keysDown)
			active.keyDown(c);
		active.update();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(active.render(), 0, 0, null);
	}
	
	@Override
	public void keyTyped(KeyEvent e){}
	
	@Override
	public void keyPressed(KeyEvent e){
		keysDown.remove(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		keysDown.add(e.getKeyCode());
	}
	
}
