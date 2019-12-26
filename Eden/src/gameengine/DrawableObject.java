/*
 * 
 */
package gameengine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public abstract class DrawableObject {

	protected Vector2D position;
	protected BufferedImage image;
	protected int width;
	protected int height;
	
	public DrawableObject() {
		this.position = new Vector2D();
	}
	public DrawableObject(float x, float y) {
		this.position = new Vector2D(x, y);
	}
	public DrawableObject(float x, float y, int width, int height) {
		this.position = new Vector2D(x, y);
		this.width = width;
		this.height = height;
	}
	
	public abstract void update(float tslf);
	
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
	
	//----------------------------Getters
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
