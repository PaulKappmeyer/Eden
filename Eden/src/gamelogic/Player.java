/*
 * 
 */
package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;

import gameengine.DrawableObject;
import gameengine.graphics.AnimationPlayer;
import gameengine.graphics.AnimationSet;
import gameengine.loaders.RessourceLoader;
import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public class Player extends DrawableObject{

	int width;
	int height;
	BufferedImage image;
	AnimationPlayer animationPlayer;
	
	//Clip clipWalking;
	
	boolean isMoving;
	int walkspeed;
	Vector2D walkDireciton;
	
	public Player(float x, float y) {
		this.position = new Vector2D(x, y);
		this.width = 128;
		this.height = 128;
		this.isMoving = false;
		this.walkspeed = 100;
		this.walkDireciton = new Vector2D();
		try {	
			AnimationSet playerAnimationSet = RessourceLoader.load(AnimationSet.class, ".\\res\\eden_32.png");
			animationPlayer = new AnimationPlayer(playerAnimationSet);		
			
			//clipWalking = RessourceLoader.load(Clip.class, ".\\res\\walking.wav"); <- Clip kann nicht geöffnet werden		
		} catch (Exception e) {
			e.printStackTrace();
		}
		animationPlayer.play("player_walk_down");
	}

	@Override
	public void update(float tslf) {
		this.isMoving = false;
		if(PlayerInput.isUpKeyDown()) {
			isMoving = true;
			walkDireciton.x = 0;
			walkDireciton.y = -1;
			animationPlayer.play("player_walk_up");
		}
		if(PlayerInput.isLeftKeyDown()) {
			isMoving = true;
			walkDireciton.x = -1;
			walkDireciton.y = 0;
			animationPlayer.play("player_walk_left");
		}
		if(PlayerInput.isDownKeyDown()) {
			isMoving = true;
			walkDireciton.x = 0;
			walkDireciton.y = 1;
			animationPlayer.play("player_walk_down");
		}
		if(PlayerInput.isRightKeyDown()) {
			isMoving = true;
			walkDireciton.x = 1;
			walkDireciton.y = 0;
			animationPlayer.play("player_walk_right");
		} 
		if(!isMoving){
			animationPlayer.reset();
			animationPlayer.stop();
			walkDireciton.x = 0;
			walkDireciton.y = 0;
		}
		
		animationPlayer.update(tslf);
		this.image = animationPlayer.getCurrentFrame();
		
		this.position.x += walkDireciton.x * walkspeed * tslf;
		this.position.y += walkDireciton.y * walkspeed * tslf;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
}
