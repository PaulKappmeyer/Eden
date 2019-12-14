/*
 * 
 */
package gamelogic.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;
import gameengine.graphics.AnimationPlayer;
import gameengine.maths.Vector2D;
import gameengine.sounds.SoundPlayer;
import gamelogic.GameResources;

/**
 * 
 * @author Paul
 *
 */
public class Player extends DrawableObject{

	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final int MAX_WALKSPEED = 400;
	public static final float TIME_FOR_MAX_WALKSPEED = 0.2f;
	
	private float timeWalked;
	private boolean isMoving;	
	private int currentWalkspeed;
	private Vector2D walkDirectionVector;
	private String walkDirectionString;
	
	private int width;
	private int height;
	private BufferedImage image;
	private AnimationPlayer animationPlayer;
	private SoundPlayer soundPlayer;
	
	public Player(float x, float y) {
		super(x, y);
		this.width = 128;
		this.height = 128;
		this.isMoving = false;
		this.currentWalkspeed = 0;
		this.walkDirectionVector = new Vector2D();
		this.animationPlayer = new AnimationPlayer(GameResources.PLAYER_ANIMATION_SET);		
		this.soundPlayer = new SoundPlayer();
		this.soundPlayer.addSound("player_walk", GameResources.PLAYER_WALK_SOUND);
	}

	@Override
	public void update(float tslf) {
		boolean isPressing = false;
		walkDirectionVector.x = 0;
		walkDirectionVector.y = 0;
		if(PlayerInput.isUpKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = -1;
			walkDirectionString = UP;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.75f;
			walkDirectionVector.x = 0.75f;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.75f;
			walkDirectionVector.x = -0.75f;
		}
		if(PlayerInput.isDownKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = 1;
			walkDirectionString = DOWN;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.75f;
			walkDirectionVector.x = 0.75f;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.75f;
			walkDirectionVector.x = -0.75f;
		}
		if(PlayerInput.isLeftKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = -1;
			walkDirectionString = LEFT;
		}
		if(PlayerInput.isRightKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = 1;
			walkDirectionString = RIGHT;
		}
		if(isPressing) {
			isMoving = true;
		}

		if(isMoving) {
			animationPlayer.loop("player_walk_" + walkDirectionString);
			animationPlayer.update(tslf);
			 soundPlayer.loop("player_walk");

			timeWalked += tslf;
			if(timeWalked >= TIME_FOR_MAX_WALKSPEED) {
				currentWalkspeed = MAX_WALKSPEED;
			} else {
				currentWalkspeed = (int) (MAX_WALKSPEED * (timeWalked / TIME_FOR_MAX_WALKSPEED));
			}
		}
		if(!isPressing || (walkDirectionVector.x == 0 && walkDirectionVector.y == 0)) {
			stopWalking();
		}
		image = animationPlayer.getCurrentFrame();

		this.position.x += walkDirectionVector.x * currentWalkspeed * tslf;
		this.position.y += walkDirectionVector.y * currentWalkspeed * tslf;
	}
	
	/**
	 * 
	 */
	public void stopWalking() {
		animationPlayer.reset();
		animationPlayer.stop();
		
		soundPlayer.stop();

		walkDirectionVector.x = 0;
		walkDirectionVector.y = 0;
		timeWalked = 0;
		isMoving = false;
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
