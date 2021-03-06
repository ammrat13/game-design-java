package com.ammrat13.javagame;

import com.ammrat13.javagame.gamescenes.*;
import com.ammrat13.javagame.util.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;

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
	
	/** How often to update in milliseconds. */
	public final int UPFREQ = 30;
	
	/** The current system time. Used to pass change in time to scenes. */
	private long time;
	/** The set of keys that are pressed down. Passed to scenes. */
	public final Set<Integer> keysDown;
	
	/** The set of all game scenes. */
	private final Set<GameScene> gss;
	/** The pointer to the active scene. */
	private GameScene active;
	
	/** Public variables. */
	public final HashMap<String, Object> pubVars = new HashMap<>();
	
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
		gss.add(new TitleScene(this));
		gss.add(new InstructionScene(this));
		gss.add(new GamePlayScene(this));
		gss.add(new PauseScene(this));
		gss.add(new WinScene(this));
		gss.add(new LoseScene(this));
		setActive("TitleScene");
		
		// Update every frame
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, UPFREQ);
		
		// Loop the background sound forever
		try{
			Sound.getSoundClip("res/MainMusic.wav").loop(-1);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the active scene.
	 * @param ags The name of the scene to make active
	 */
	
	public void setActive(String ags){
		// Find the scene we want to make active
		for(GameScene gs : gss){
			if(gs.getClass().getSimpleName().equals(ags)){
				// Stop the current active scene
				if(active != null)
					active.stop();
				active = gs;
				active.start();
				break;
			}
		}
	}
	
	/**
	 * Returns the reference to a given scene.
	 * @param ags The name of the scene to return
	 */
	
	public GameScene getScene(String ags){
		// Find the scene we want to return
		for(GameScene gs : gss){
			if(gs.getClass().getSimpleName().equals(ags))
				return gs;
		}
		return null;
	}
	
	/**
	 * Called every frame to update the game.
	 */
	
	private void update(){
		// Update the scene
		active.update(Math.toIntExact((System.currentTimeMillis() - time) % Integer.MAX_VALUE));
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
