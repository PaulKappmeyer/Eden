package gamelogic.enemies;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.Mob;
import gameengine.maths.Vector2D;
import gamelogic.Direction;
import gamelogic.Main;

/**
 * This class is used to detect whether the "zombie" can see the player or not.
 * @author Paul
 *
 */
public class ZombieWatchBehavior {

	private Mob object;
	private int triggerDistance;
	private int viewCone;

	public ZombieWatchBehavior(Mob object, int triggerDistance, int viewCone) {
		this.object = object;
		this.triggerDistance = triggerDistance;
		this.viewCone = viewCone;
	}

	public void draw(Graphics graphics) {
		int t = triggerDistance;
		float v = viewCone/2;
		graphics.setColor(new Color(255, 255, 255, 150));
		switch (object.getWalkDirectionString()) {
		case right:
			graphics.fillArc((int)(object.getCenterPositionX() - t), (int)(object.getCenterPositionY() - t), t*2, t*2, (int)(0-v), (int)(v*2));	
			break;
		case left:
			graphics.fillArc((int)(object.getCenterPositionX() - t), (int)(object.getCenterPositionY() - t), t*2, t*2, (int)(180-v), (int)(v*2));	
			break;
		case up:
			graphics.fillArc((int)(object.getCenterPositionX() - t), (int)(object.getCenterPositionY() - t), t*2, t*2, (int)(90-v), (int)(v*2));	
			break;
		case down:
			graphics.fillArc((int)(object.getCenterPositionX() - t), (int)(object.getCenterPositionY() - t), t*2, t*2, (int)(-90-v), (int)(v*2));	
			break;

		default:
			break;
		}
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
