package gamelogic;

import gameengine.DrawableObject;
import gameengine.maths.Vector2D;

public class ZombieBehavior {

	private DrawableObject object;
	private float triggerDistance;
	
	public ZombieBehavior(DrawableObject object, float triggerDistance) {
		this.object = object;
		this.triggerDistance = triggerDistance;
	}
	
	public boolean isTriggered() {
		float playerCenterX = Main.player.getX() + Main.player.getWidth()/2;
		float playerCenterY = Main.player.getY() + Main.player.getHeight()/2;
		Vector2D playerCenterPosition = new Vector2D(playerCenterX, playerCenterY);
		float zombieCenterX = object.getX() + object.getWidth()/2;
		float zombieCenterY = object.getY() + object.getHeight()/2;
		Vector2D zombieCenterPosition = new Vector2D(zombieCenterX, zombieCenterY);
		
		return triggerDistance*triggerDistance >= zombieCenterPosition.distanceSquared(playerCenterPosition) ?  true : false;
	}
	
	public Vector2D getVectorToPlayer() {
		float playerCenterX = Main.player.getX() + Main.player.getWidth()/2;
		float playerCenterY = Main.player.getY() + Main.player.getHeight()/2;
		Vector2D playerCenterPosition = new Vector2D(playerCenterX, playerCenterY);
		float zombieCenterX = object.getX() + object.getWidth()/2;
		float zombieCenterY = object.getY() + object.getHeight()/2;
		Vector2D zombieCenterPosition = new Vector2D(zombieCenterX, zombieCenterY);
		return (playerCenterPosition.subtract(zombieCenterPosition)).makeUnitVector();
	}
}
