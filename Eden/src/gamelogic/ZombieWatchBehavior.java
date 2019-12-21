package gamelogic;

import gameengine.Mob;
import gameengine.maths.Vector2D;

public class ZombieWatchBehavior {

	private Mob object;
	private float triggerDistance;
	private float viewCone = 90;

	public ZombieWatchBehavior(Mob object, int triggerDistance, int viewCone) {
		this.object = object;
		this.triggerDistance = triggerDistance;
		this.viewCone = viewCone;
	}

	public boolean isTriggered() {
		Vector2D playerCenterPosition = Main.player.getCenterPosition();
		Vector2D zombieCenterPosition = object.getCenterPosition();
		
		if(triggerDistance*triggerDistance >= zombieCenterPosition.distanceSquared(playerCenterPosition)) {
			Vector2D zombieWatchVector = Direction.directionToVector(object.getWalkDirectionString());
			Vector2D newCenterPosition = Vector2D.subtract(playerCenterPosition, zombieCenterPosition);
			
			float scalar = newCenterPosition.x * zombieWatchVector.x + newCenterPosition.y * zombieWatchVector.y;
			float length = newCenterPosition.getLength();
			float value = scalar / length;
			float degrees = (float) Math.toDegrees(Math.acos(value));
			
			return degrees <= viewCone/2 ? true : false;
		} else return false;
	}

	public Vector2D getVectorToPlayer() {
		Vector2D playerCenterPosition = Main.player.getCenterPosition();
		Vector2D zombieCenterPosition = object.getCenterPosition();

		return (Vector2D.subtract(playerCenterPosition, zombieCenterPosition)).makeUnitVector();
	}

}
