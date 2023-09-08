package gamelogic;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.DrawableObject;
import gameengine.Mob;
import gameengine.hitbox.CircleHitbox;
import gameengine.hitbox.Hitbox;
import gameengine.maths.Vector2D;

public class Projectile extends DrawableObject{

	public static final int SPEED = 2000;
	private Vector2D centerPosition;
	private Vector2D velocityVector;
	private Hitbox hitbox;
	private Mob owner;
	
	public Projectile(Mob owner, float x, float y, float velocityX, float velocityY) {
		super(x, y, 10, 10);
		this.owner = owner;
		this.centerPosition = new Vector2D(position.x + width/2, position.y + height/2);
		this.velocityVector = new Vector2D(velocityX, velocityY).makeUnitVector();
		this.hitbox = new CircleHitbox(centerPosition, width);
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.YELLOW);
		graphics.fillOval((int) position.x, (int) position.y, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawOval((int) position.x, (int) position.y, width, height);
	}
	
	@Override
	public void update(float tslf) {
		position.x += velocityVector.x * SPEED * tslf;
		position.y += velocityVector.y * SPEED * tslf;
		centerPosition.x = position.x + width/2;
		centerPosition.y = position.y + height/2;
	}
	
	//--------------------------Getters
	public Mob getOwner() {
		return owner;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public Vector2D getVelocityVector() {
		return velocityVector;
	}
	
}
