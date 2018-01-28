package com.ammrat13.javagame;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.gamescenes.GameScene;
import com.ammrat13.javagame.util.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will manage all aspects of the game, including scene cordination.
 * It will start on a given scene, and transition between them.
 *
 * @author Ammar Ratnani
 */

public class GameManager extends JPanel implements KeyListener {
	
	/** The width of the canvas. */
	public final int WIDTH;
	/** The height of the canvas. */
	public final int HEIGHT;
	
	/** The filename of the background music. This music is constant between scenes. */
	private static final String BG_SOUND = "sound/MainMusic.wav";
	
	/** The current system time. Used to pass change in time to scenes. */
	private long time;
	/** The set of keys that are pressed down. Passed to scenes. */
	private final Set<Integer> keysDown;
	
	/** The set of all game scenes. */
	private final Set<GameScene> gss;
	/** The pointer to the active scene. */
	private GameScene active;
	
	/**
	 * Constructs the game manager. Sets the width, height, and current time,
	 * and initializes all the fields. Also starts the sound and sets the
	 * {@code update()} method to call every frame.
	 * @param width The width of the game
	 * @param height The height of the game
	 */
	public GameManager(int width, int height){
		WIDTH = width;
		HEIGHT = height;
		
		time = System.currentTimeMillis();
		keysDown = new HashSet<>();
		
		gss = new HashSet<>();
		gss.add(new GamePlayScene(this));
		setActive("GamePlayScene");
		
		// Update every frame
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, 30);
		
		// Loop the background sound forever
		try{
			Sound.getSoundClip(BG_SOUND).loop(-1);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the active scene.
	 * @param ags The name of the scene to make active
	 */
	
	public void setActive(String ags){
		// Stop the current active scene
		if(active != null)
			active.stop();
		// Find the scene we want to make active
		for(GameScene gs : gss){
			if(gs.getClass().getSimpleName().equals(ags)){
				active = gs;
				active.start();
				break;
			}
		}
	}
	
	/**
	 * Called every frame to update the game.
	 */
	
	private void update(){
		// Update the scene
		active.update(
			Math.toIntExact((System.currentTimeMillis() - time) % Integer.MAX_VALUE),
			keysDown
		);
		time = System.currentTimeMillis();
		
		this.repaint();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void paintComponent(Graphics g){
		// Just draw the active scene
		g.drawImage(active.render(), 0, 0, null);
	}
	
	/** {@inheritDoc} */
	@Override
	public void keyTyped(KeyEvent e){}
	
	/** {@inheritDoc} */
	@Override
	public void keyPressed(KeyEvent e){
		keysDown.add(e.getKeyCode());
	}
	
	/** {@inheritDoc} */
	@Override
	public void keyReleased(KeyEvent e){
		keysDown.remove(e.getKeyCode());
	}
	
}
