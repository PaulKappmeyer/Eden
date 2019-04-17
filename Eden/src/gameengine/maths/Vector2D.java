/*
 * 
 */
package gameengine.maths;

/**
 * A vector representing a x-and y-location in coordinate space, specified in float precision
 * @author Paul
 * @category Game-math
 */
public class Vector2D {

	public float x;
	public float y;
	
	/* ---------------------------------------------CONSTRUCOTRS------------------------------------ */
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
	
	/* ---------------------------------------------METHODS------------------------------------ */
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
}