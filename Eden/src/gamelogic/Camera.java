package gamelogic;

import gameengine.DrawableObject;
import gameengine.maths.Vector2D;

public class Camera {

	private Vector2D position;
	private DrawableObject focusedObject;
	
	public Camera() {
		this.position = new Vector2D();
	}
	
	public void update(float tslf) {
		if(focusedObject != null) {
			position.x = focusedObject.getX() + focusedObject.getWidth()/2 - Main.SCREEN_WIDTH/2;
			position.y = focusedObject.getY() + focusedObject.getHeight()/2 - Main.SCREEN_HEIGHT/2;
			if(position.x < 0) position.x = 0;
			if(position.y < 0) position.y = 0;
		}
	}
	
	public void setFocusedObject(DrawableObject object) {
		this.focusedObject = object;
	}
	
	public boolean isVisibleOnCamera(float x, float y, int width, int height) {
		if(x + width > position.x && x < position.x + Main.SCREEN_WIDTH && y + height > position.y && y < position.y + Main.SCREEN_HEIGHT) return true;
		return false;
	}
	
	//--------------------------------Getters
	public float getX(){
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
}
