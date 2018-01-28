package com.ammrat13.javagame;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.gamescenes.GameScene;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
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
	
	// Passed in from the window
	public final int WIDTH;
	public final int HEIGHT;
	
	// The game manger manages the background sound as it persists between scenes
	private static final String BG_SOUND = "sound/MainMusic.wav";
	
	// To pass the change in time to the scene
	private long time;
	// To pass to the active scene
	private final Set<Integer> keysDown;
	
	// Set of all the scenes we have and which one is active
	private final Set<GameScene> gss;
	private GameScene active;
	
	public GameManager(int w, int h){
		WIDTH = w;
		HEIGHT = h;
		
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
		getSoundClip(BG_SOUND).loop(-1);
	}
	
	/**
	 * Sets the active scene.
	 *
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
				break;
			}
		}
		// Start the new scene
		active.start();
	}
	
	/**
	 * Gets the clip for a sound.
	 *
	 * @param soundPath The path of the sound to get the clip for
	 * @return The clip object for the sound, or null if failure
	 */
	
	public Clip getSoundClip(String soundPath){
		// Source: https://www.geeksforgeeks.org/play-audio-file-using-java/
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			return clip;
		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
			e.printStackTrace();
		}
		return null;
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
	
	@Override
	protected void paintComponent(Graphics g){
		// Just draw the active scene
		g.drawImage(active.render(), 0, 0, null);
	}
	
	@Override
	public void keyTyped(KeyEvent e){}
	
	@Override
	public void keyPressed(KeyEvent e){
		keysDown.add(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		keysDown.remove(e.getKeyCode());
	}
	
}
