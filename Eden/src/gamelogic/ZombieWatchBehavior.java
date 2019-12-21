package gamelogic;

import gameengine.Mob;
import gameengine.maths.Vector2D;

public class ZombieWatchBehavior {

	private Mob object;
	private float triggerDistance;
	
	public ZombieWatchBehavior(Mob object, float triggerDistance) {
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

		if(triggerDistance*triggerDistance >= zombieCenterPosition.distanceSquared(playerCenterPosition)) {
			Vector2D newCenterPosition = playerCenterPosition.subtract(zombieCenterPosition);
			Vector2D zombieWatchVector = Direction.directionToVector(object.getWalkDirectionString());
			float scalar = newCenterPosition.x * zombieWatchVector.x + newCenterPosition.y * zombieWatchVector.y;

			return scalar > 0 ? true : false;
		} else return false;
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
