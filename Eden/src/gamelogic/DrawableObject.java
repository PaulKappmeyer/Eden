/*
 * 
 */
package gamelogic;

import java.awt.Graphics;

import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public abstract class DrawableObject {

	Vector2D position;
	
	public DrawableObject() {
		position = new Vector2D();
	}
	public DrawableObject(float x, float y) {
		position = new Vector2D(x, y);
	}
	
	public abstract void update(float tslf);	
	public abstract void draw(Graphics g);
}
