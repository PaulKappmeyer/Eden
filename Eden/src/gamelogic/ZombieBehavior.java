package gamelogic;

import gameengine.Mob;
import gameengine.maths.Vector2D;

public class ZombieBehavior {

	private Mob object;
	private int triggerDistance;
	
	public ZombieBehavior(Mob object, int triggerDistance) {
		this.object = object;
		this.triggerDistance = triggerDistance;
	}
	
	public boolean isTriggered() {
		Vector2D playerCenterPosition = Main.player.getCenterPosition();
		Vector2D zombieCenterPosition = object.getCenterPosition();
		
		return triggerDistance*triggerDistance >= zombieCenterPosition.distanceSquared(playerCenterPosition) ?  true : false;
	}
	
	public Vector2D getVectorToPlayer() {
		Vector2D playerCenterPosition = Main.player.getCenterPosition();
		Vector2D zombieCenterPosition = object.getCenterPosition();
		
		return (Vector2D.subtract(playerCenterPosition, zombieCenterPosition)).makeUnitVector();
	}
}
