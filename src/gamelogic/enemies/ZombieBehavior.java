package gamelogic.enemies;

import gameengine.Mob;
import gameengine.maths.Vector2D;
import gamelogic.Main;

/**
 * This class is used to detect whether the "zombie" is in range of the player or not.
 * @author Paul
 *
 */
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
