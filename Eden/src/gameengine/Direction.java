package gameengine;

import gameengine.maths.Vector2D;

public enum Direction {
	up,
	down,
	left,
	right;
	
	public static Vector2D directionToVector(Direction direcion) {
		switch (direcion) {
		case up:
			return new Vector2D(0, -1);
		case down:
			return new Vector2D(0, 1);
		case left:
			return new Vector2D(-1, 0);
		case right:
			return new Vector2D(1, 0);

		default:
			return new Vector2D();
		}
	}
	
	public static Direction vectorToDirection(Vector2D vector) {
		if(vector.x <= 1 && vector.x >= 0.7) return Direction.right;
		else if(vector.x <= -0.7 && vector.x >= -1) return Direction.left;
		else if(vector.y <= 1 && vector.y >= 0.7) return Direction.down;
		else if(vector.y <= -0.7 && vector.y >= -1) return Direction.up;
		else return null;
	}
}
