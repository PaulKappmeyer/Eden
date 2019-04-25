/*
 * 
 */
package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;
import gameengine.graphics.AnimationPlayer;
import gameengine.graphics.AnimationSet;
import gameengine.loaders.RessourceLoader;
import gameengine.maths.Vector2D;
import gameengine.sounds.Sound;
import gameengine.sounds.SoundPlayer;
import gameengine.sounds.SoundSet;

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
	SoundPlayer soundPlayer;
	
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
			
			SoundSet soundSet = new SoundSet();
			soundSet.addSound("player_walk", RessourceLoader.load(Sound.class, ".\\res\\walking_female.wav"));
			soundPlayer = new SoundPlayer(soundSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		soundPlayer.play("player_walk");                //TODO: Steht hier, da sonst soundPlayer.stop(); einen nullPointerExecption wirft.
		animationPlayer.play("player_walk_down");       //TODO: Steht hier, da sonst animationPlayer.stop(); einen nullPointerExecption wirft.
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
			
			soundPlayer.stop();
			
			walkDireciton.x = 0;
			walkDireciton.y = 0;
		}
		else {
			soundPlayer.loop("player_walk");
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
