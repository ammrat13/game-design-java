package com.ammrat13.javagame.util;

/**
 * This class represents a two dimensional vector.
 *
 * @author Ammar Ratnani
 */

public class Vec {
	
	/** The vector {@code (0,0)} */
	public static final Vec ZERO = new Vec(0,0);
	/** The vector {@code (1,0)} */
	public static final Vec IHAT = new Vec(1,0);
	/** The vector {@code (0,1)} */
	public static final Vec JHAT = new Vec(0,1);
	
	/** Stores the {@code x} component of the vector */
	public double x;
	/** Stores the {@code y} component of the vector */
	public double y;
	
	/**
	 * Constructs the vector.
	 * @param x The {@code x} component
	 * @param y The {@code y} component
	 */
	public Vec(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the vector sum of this and the parameter.
	 * @param v A vector
	 * @return {@code this + v}
	 */
	public Vec add(Vec v){
		return new Vec(this.x + v.x, this.y + v.y);
	}
	
	/**
	 * Returns this vector multiplied by the scalar parameter
	 * @param c A scalar
	 * @return {@code c * this}
	 */
	public Vec mul(double c){
		return new Vec(c*this.x, c*this.y);
	}
	
	/**
	 * Returns the absolute value of this vector.
	 * @return {@code |this|}
	 */
	public double abs(){
		return Math.sqrt(x*x+y*y);
	}
	
	/**
	 * Returns a copy of this vector
	 * @return A copy of this vector
	 */
	public Vec copy(){
		return new Vec(x,y);
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode(){
		return Double.hashCode(x*y);
	}
	
}
