/*
 * 
 */
package gameengine.maths;

import gamelogic.Direction;

/**
 * A vector representing a x-and y-location in coordinate space, specified in float precision
 * @author Paul
 * @category Game-math
 */
public class Vector2D {

	public float x;
	public float y;
	
	/* ---------------------------------------------Constructors------------------------------------ */
	/**
	 * Constructs and initializes a new Vector with x and y equals zero
	 */
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}
	/**
	 * Constructs and initializes a new vector with specified x-and y-position
	 * @param x The x-position of the new vector
	 * @param y The y-position of the new vector
	 */
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Constructs and initializes a new vector with the x-and y-position of the specified vector
	 * @param vector The specified vector
	 */
	public Vector2D(Vector2D vector) {
		this.x = vector.x;
		this.y = vector.y;
	}
	
	/* ---------------------------------------------methods------------------------------------ */	
	public Vector2D subtract(Vector2D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	
	/**
	 * Returns the distance between the vector and a specified x-and y-position
	 * @param x The x-position
	 * @param y The y-position
	 * @return The distance between the vector and the specified x-and y-position
	 */
	public float distance(float x, float y) {
		float distX = Math.abs(this.x - x);
		float distY = Math.abs(this.y - y);
		return (float) Math.sqrt(distX * distX + distY * distY);
	}
	/**
	 * Returns the distance between the vector and another specified vector
	 * @param vector The specified vector
	 * @return The distance between the vector and the specified vector
	 */
	public float distance(Vector2D vector) {
		float distX = Math.abs(this.x - vector.x);
		float distY = Math.abs(this.y - vector.y);
		return (float) Math.sqrt(distX * distX + distY * distY);
	}
	/**
	 * Returns the squared distance between the vector and a specified x-and y-position
	 * @param x The x-position
	 * @param y The y-position
	 * @return The squared distance between the vector and the specified x-and y-position
	 */
	public float distanceSquared(float x, float y) {
		float distX = Math.abs(this.x - x);
		float distY = Math.abs(this.y - y);
		return (distX * distX + distY * distY);
	}
	/**
	 * Returns the squared distance between the vector and another specified vector
	 * @param vector The specified vector
	 * @return The squared distance between the vector and the specified vector
	 */
	public float distanceSquared(Vector2D vector) {
		float distX = Math.abs(this.x - vector.x);
		float distY = Math.abs(this.y - vector.y);
		return (distX * distX + distY * distY);
	}
	
	/**
	 * Returns the length of the vector
	 * @return The length of the vector
	 * @see {@link #getMagnitude()} 
	 */
	public float getLength() {
		return (float) Math.sqrt(x*x + y*y);
	}
	/**
	 * Returns the magnitude of the vector
	 * @return The magnitude of the vector
	 * @see {@link #getLength()} 
	 */
	public float getMagnitude() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	/**
	 * This method sets the vector to a unit vector by dividing the x-and y-position by the length of the vector
	 */
	public Vector2D makeUnitVector() {
		float length = this.getLength();
		this.x /= length;
		this.y /= length;
		return this;
	}
	public Vector2D normalize() {
		float length = this.getLength();
		this.x /= length;
		this.y /= length;
		return this;
	}
	/**
	 * Returns if the vector is a unit vector with a length of one
	 * @return true if the vector is a unit vector, false if not
	 */
	public boolean isUnitVector() {
		return Math.round((x*x + y*y) * 1000)/1000 == 1 ? true : false;
	}

	public Direction getDirection() {
		if(x <= 1 && x >= 0.7) return Direction.right;
		else if(x <= -0.7 && x >= -1) return Direction.left;
		else if(y <= 1 && y >= 0.7) return Direction.down;
		else if(y <= -0.7 && y >= -1) return Direction.up;
		else return null;
	}
	
	@Override
	public String toString() {
		return "[x:" + String.format("%1.2f", x) + " y:" + String.format("%1.2f", y) + "]"; 
	}
}