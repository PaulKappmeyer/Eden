package gameengine.hitbox;

import java.awt.Graphics;

public abstract class Hitbox {

	public abstract void draw(Graphics graphics);
	
	public abstract boolean isOverlapping(Hitbox hitboxB);
}
