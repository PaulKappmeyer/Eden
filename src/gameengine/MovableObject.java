package gameengine;

import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public abstract class MovableObject extends DrawableObject{

	protected Vector2D centerPosition;
	protected Vector2D moveVector;
	
	public MovableObject(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.centerPosition = new Vector2D(x + width/2, y + width/2);
		this.moveVector = new Vector2D();
	}
	
	@Override
	public void update(float tslf) {
		position.x += moveVector.x * tslf;
		position.y += moveVector.y * tslf;
		centerPosition.x = position.x + width/2;
		centerPosition.y = position.y + height/2;
	}
	
	//------------------------------------Getters
	public Vector2D getCenterPosition() {
		return centerPosition;
	}
	
	public float getCenterPositionX() {
		return centerPosition.x;
	}
	
	public float getCenterPositionY() {
		return centerPosition.y;
	}
}
