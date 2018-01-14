package com.ammrat13.javagame;

import com.ammrat13.javagame.gamescenes.GamePlayScene;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.util.*;
import java.util.Timer;

import javax.sound.sampled.*;

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
	
	// The sound playing
	private Clip clip;
	private static final String BG_SOUND = "sound/MainMusic.wav";
	
	// Map of all the scenes we have
	public Map<String, GameScene> gss;
	private GameScene active;
	
	// To pass to the active scene
	private Set<Integer> keysDown;
	
	public GameManager(int w, int h){
		WIDTH = w;
		HEIGHT = h;
		keysDown = new HashSet<>();
		
		gss = new HashMap<>();
		gss.put("GamePlay", new GamePlayScene(this));
		setActive("GamePlay");
		
		// Update every frame
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, 1);
		
		setSound(BG_SOUND);
	}
	
	/**
	 * Sets the active scene.
	 *
	 * @param ags The name of the scene to make active
	 */
	
	public void setActive(String ags){
		if(active != null)
			active.stop();
		active = gss.get(ags);
		active.start();
	}
	
	/**
	 * Sets the background sound to play continuously
	 *
	 * @param soundPath The path of the sound to play
	 * @return The length of the sound
	 */
	
	private int setSound(String soundPath){
		// Source: https://www.geeksforgeeks.org/play-audio-file-using-java/
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath).getAbsoluteFile());
			
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			return (int) (1000 * ((double) ais.getFrameLength() / ais.getFormat().getFrameRate()));
		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Interrupts the background sound and plays one loop of the sound provided.
	 *
	 * @param soundPath The path of the sound to play
	 */
	
	public void interruptSound(String soundPath){
		Timer s = new Timer();
		s.schedule(new TimerTask() {
			@Override
			public void run() {
				setSound(BG_SOUND);
			}
		}, setSound(soundPath));
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
		keysDown.add(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		keysDown.remove(e.getKeyCode());
	}
	
}
