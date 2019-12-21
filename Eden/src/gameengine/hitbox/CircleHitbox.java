package gameengine.hitbox;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.maths.MyCollision;
import gameengine.maths.Vector2D;

public class CircleHitbox extends Hitbox{

	private Vector2D centerPosition;
	private float radius;
	
	public CircleHitbox(Vector2D centerPosition, float radius) {
		this.centerPosition = centerPosition;
		this.radius = radius;
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.GREEN);
		graphics.drawOval((int)(centerPosition.x-radius), (int)(centerPosition.y-radius), (int)(radius*2), (int)(radius*2));
	}

	@Override
	public boolean isOverlapping(Hitbox hitboxB) {
		if(hitboxB instanceof CircleHitbox) {
			CircleHitbox circleHitboxB = (CircleHitbox)hitboxB;
			return MyCollision.circleToCircle(this.centerPosition, this.radius, circleHitboxB.centerPosition, circleHitboxB.radius);
		} else return false;
	}
}
