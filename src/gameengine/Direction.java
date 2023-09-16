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
		return vector.getDirection();
	}
}
