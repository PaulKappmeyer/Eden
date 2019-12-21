package gameengine;

import gameengine.maths.Vector2D;

public class MovableObject extends DrawableObject{

	protected Vector2D moveVector;

	public MovableObject(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.moveVector = new Vector2D();
	}
	
	@Override
	public void update(float tslf) {
		this.position.x += moveVector.x * tslf;
		this.position.y += moveVector.y * tslf;
	}
}
