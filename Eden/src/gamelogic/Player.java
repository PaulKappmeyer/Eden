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

/**
 * 
 * @author Paul
 *
 */
public class Player extends DrawableObject{

	public static final int MAX_WALKSPEED = 200;
	public static final float TIME_FOR_MAX_WALKSPEED = 1.25f;
	private float timeWalked;
	private boolean isMoving;	
	private int currentWalkspeed;
	private Vector2D walkDireciton;
	
	private int width;
	private int height;
	private BufferedImage image;
	private AnimationPlayer animationPlayer;
	private SoundPlayer soundPlayer;
	
	public Player(float x, float y) {
		this.position = new Vector2D(x, y);
		this.width = 128;
		this.height = 128;
		this.isMoving = false;
		this.currentWalkspeed = 0;
		this.walkDireciton = new Vector2D();
		try {	
			AnimationSet playerAnimationSet = RessourceLoader.load(AnimationSet.class, ".\\res\\eden_32.png");
			animationPlayer = new AnimationPlayer(playerAnimationSet);		
			
			soundPlayer = new SoundPlayer();
			soundPlayer.addSound("player_walk", RessourceLoader.load(Sound.class, ".\\res\\walking_female.wav"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		animationPlayer.play("player_walk_down");
		image = animationPlayer.getCurrentFrame();
	}

	@Override
	public void update(float tslf) {
		boolean isPressing = false;
		if(PlayerInput.isUpKeyDown()) {
			isMoving = true;
			isPressing = true;
			walkDireciton.x = 0;
			walkDireciton.y = -1;
			animationPlayer.play("player_walk_up");
		}
		if(PlayerInput.isLeftKeyDown()) {
			isMoving = true;
			isPressing = true;
			walkDireciton.x = -1;
			walkDireciton.y = 0;
			animationPlayer.play("player_walk_left");
		}
		if(PlayerInput.isDownKeyDown()) {
			isMoving = true;
			isPressing = true;
			walkDireciton.x = 0;
			walkDireciton.y = 1;
			animationPlayer.play("player_walk_down");
		}
		if(PlayerInput.isRightKeyDown()) {
			isMoving = true;
			isPressing = true;
			walkDireciton.x = 1;
			walkDireciton.y = 0;
			animationPlayer.play("player_walk_right");
		} 
		
		if(isMoving) {
			animationPlayer.update(tslf);
			soundPlayer.loop("player_walk");
			
			if(!isPressing) {
				animationPlayer.reset();
				animationPlayer.stop();
				
				soundPlayer.stop();
				
				walkDireciton.x = 0;
				walkDireciton.y = 0;
				timeWalked = 0;
				isMoving = false;
			}
		}
		image = animationPlayer.getCurrentFrame();
		
		timeWalked += tslf;
		if(timeWalked >= TIME_FOR_MAX_WALKSPEED) {
			currentWalkspeed = MAX_WALKSPEED;
		} else {
			currentWalkspeed = (int) (MAX_WALKSPEED * (timeWalked / TIME_FOR_MAX_WALKSPEED));
		}
		
		this.position.x += walkDireciton.x * currentWalkspeed * tslf;
		this.position.y += walkDireciton.y * currentWalkspeed * tslf;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
}
