/*
 * 
 */
package gamelogic;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public class Player extends DrawableObject{

	Vector2D position;
	int width;
	int height;
	
	public Player(float x, float y) {
		this.position = new Vector2D(x, y);
		this.width = 16;
		this.height = 16;
	}

	@Override
	public void update(float tslf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.BLUE);
		graphics.fillRect((int)position.x, (int)position.y, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawRect((int)position.x, (int)position.y, width, height);
	}
	
}
