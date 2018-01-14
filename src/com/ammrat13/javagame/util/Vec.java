package com.ammrat13.javagame.util;

/**
 * This class represents a two dimensional vector
 *
 * @author Ammar Ratnani
 */

public class Vec {
	
	public static final Vec ZERO = new Vec(0,0);
	public static final Vec IHAT = new Vec(1,0);
	public static final Vec JHAT = new Vec(0,1);
	
	public double x;
	public double y;
	
	public Vec(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the vector sum of this and the parameter.
	 *
	 * @param v A vector
	 * @return {@code this + v}
	 */
	public Vec add(Vec v){
		return new Vec(this.x + v.x, this.y + v.y);
	}
	
	/**
	 * Returns this vector multiplied by the scalar parameter
	 *
	 * @param c A scalar
	 * @return {@code c * this}
	 */
	public Vec mul(double c){
		return new Vec(c*this.x, c*this.y);
	}
	
	/**
	 * Returns a copy of this vector
	 *
	 * @return A copy of this vector
	 */
	public Vec copy(){
		return new Vec(x,y);
	}
	
	@Override
	public int hashCode(){
		return Double.hashCode(x*y);
	}
	
}
