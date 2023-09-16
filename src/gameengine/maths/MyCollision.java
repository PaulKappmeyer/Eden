package gameengine.maths;


/**
 * This class implements basic collision-methods.
 * @author Paul
 *
 */
public class MyCollision {

	public static boolean circleToCircle(Vector2D centerPositionA, float radiusA, Vector2D centerPositionB, float radiusB) {
		float sumRadius = radiusA + radiusB;
		float diffX = centerPositionA.x - centerPositionB.x;
		float diffY = centerPositionA.y - centerPositionB.y;
		float distanceSquared = (diffX*diffX) + (diffY*diffY);
		return distanceSquared <= (sumRadius*sumRadius) ? true : false;
	}
	
}
