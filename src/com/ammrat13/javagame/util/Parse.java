package com.ammrat13.javagame.util;

import com.ammrat13.javagame.gamescenes.GamePlayScene;
import com.ammrat13.javagame.objects.GamePlaySceneObject;
import com.ammrat13.javagame.objects.Spaceship;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for parsing level files.
 *
 * @author Ammar Ratnani
 */

public class Parse {
	
	/**
	 * Given the path to the level file, this method will parse it and return a
	 * list of all the objects in the scene.
	 * @param gps The {@code GamePlayScene} to pass to each object
	 * @param fname The path to the level file
	 * @return The list of all the objects in the scene
	 */
	public ArrayList<GamePlaySceneObject> parseLvl(GamePlayScene gps, String fname){
		try{
			ArrayList<GamePlaySceneObject> ret = new ArrayList<>();
			Scanner in = new Scanner(new File(fname));
			
			while(in.hasNextLine()){
				try {
					String lin = in.nextLine();
					// Try every case
					if(lin.startsWith("Spaceship"))
						ret.add(parseSpaceship(gps, lin));
				} catch(IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				}
			}
			
			return ret;
			
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Takes in a String describing a spaceship and parses it. The format is
	 * {@code Spaceship xxi xyi vxi vyi t }.
	 * @param gps The {@code GamePlayScene} to pass to the spaceship
	 * @param s The String describing the spaceship
	 * @return The spaceship
	 * @throws IllegalArgumentException When the String is invalid
	 */
	private Spaceship parseSpaceship(GamePlayScene gps, String s) throws IllegalArgumentException {
		try {
			String[] ts = s.split(" ");
			return new Spaceship(
				gps,
				new Vec(Double.parseDouble(ts[1]), Double.parseDouble(ts[2])),
				new Vec(Double.parseDouble(ts[3]), Double.parseDouble(ts[4])),
				Double.parseDouble(ts[5])
			);
		} catch(IndexOutOfBoundsException | NumberFormatException e){
			e.printStackTrace();
			throw new IllegalArgumentException("Failed to parse spaceship: " + s);
		}
	}
	
}
