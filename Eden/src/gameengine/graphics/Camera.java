package gameengine.graphics;

import gameengine.DrawableObject;
import gameengine.maths.Vector2D;
import gamelogic.Main;

public class Camera {

	private Vector2D position;
	private DrawableObject focusedObject;

	private float velocity = 10; 
	private float strength = 0.5f; //in percent 
	private float setValue = 0.01f;
	
	public Camera() {
		this.position = new Vector2D();
	}

	public void update(float tslf) {
		if(focusedObject != null) {
			float goalX;
			float goalY;
			goalX = (focusedObject.getX() + focusedObject.getWidth()/2 - Main.SCREEN_WIDTH/2);
			goalY = (focusedObject.getY() + focusedObject.getHeight()/2 - Main.SCREEN_HEIGHT/2);
			if(goalX < 0)  goalX = 0;
			if(goalY < 0) goalY = 0;

			float diffX = goalX - position.x;
			float amountX = velocity * (diffX * strength) * tslf;
			position.x += amountX;

			float diffY = goalY - position.y;
			float amountY = velocity * (diffY * strength) * tslf;
			position.y += amountY;
			
			//At this point the difference is too small so the value gets set to avoid shaking of the camera
			if(-setValue < diffX && diffX < setValue) position.x = goalX;
			if(-setValue < diffY && diffY < setValue) position.y = goalY;
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
