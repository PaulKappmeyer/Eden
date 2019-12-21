package gameengine.maths;

import java.util.Random;

import gamelogic.Direction;

@SuppressWarnings("serial")
public class MyRandom extends Random{

	public int nextInt(int lowerBound, int upperBound) {
		return lowerBound == upperBound ? lowerBound : lowerBound + nextInt(upperBound-lowerBound);
	}
	
	public Vector2D nextVector2D(int lowerBoundX, int lowerBoundY, int upperBoundX, int upperBoundY) {
		return new Vector2D(nextInt(lowerBoundX, upperBoundX), nextInt(lowerBoundY, upperBoundY));
	}
	
	public Vector2D nextVector2D(int boundX, int boundY) {
		return new Vector2D(nextInt(boundX), nextInt(boundY));
	}
	
	public Direction nextDirection() {
		int direction = nextInt(4);
		switch (direction) {
		case 0:
			return Direction.up;
			
		case 1:
			return Direction.down;

		case 2:
			return Direction.left;

		case 3:
			return Direction.right;

		default:
			return Direction.down;
		}
	}
	
}
