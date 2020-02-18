package gameengine.graphics;

import gameengine.maths.Vector2D;
import gamelogic.Main;
import gamelogic.player.Player;

public class Camera {

	private Vector2D position;
	private Player player;

	private float velocity = 10; 
	private float strength = 0.5f; //in percent 
	private float setValue = 0.01f;
	
	private float offsetX = 200;
	private float offsetY = 200;
	
	public Camera() {
		this.position = new Vector2D();
	}

	public void update(float tslf) {
		if(player != null) {
			Player player = (Player)this.player;
			
			Vector2D movementVector = player.getWalkDirectionVector();
			float offsetX = Math.copySign(this.offsetX, movementVector.x);
			if(movementVector.x == 0) offsetX = 0;
			
			float offsetY = Math.copySign(this.offsetY, movementVector.y);
			if(movementVector.y == 0) offsetY = 0;
			
			float goalX;
			float goalY;
			goalX = (player.getX() + player.getWidth()/2 - Main.SCREEN_WIDTH/2 + offsetX);
			goalY = (player.getY() + player.getHeight()/2 - Main.SCREEN_HEIGHT/2 + offsetY);
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

	public void setFocusedObject(Player player) {
		this.player = player;
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
