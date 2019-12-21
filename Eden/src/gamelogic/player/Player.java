/*
 * 
 */
package gamelogic.player;

import java.awt.Graphics;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.hitbox.Hitbox;
import gameengine.maths.Vector2D;
import gamelogic.Direction;
import gamelogic.GameResources;

/**
 * 
 * @author Paul
 *
 */
public class Player extends Mob{

	public static final int MAX_WALKSPEED = 400;
	public static final float TIME_FOR_MAX_WALKSPEED = 0.1f;

	public Hitbox hitbox;
	
	public Player(float x, float y) {
		super(x, y, 128, 128);
		this.currentWalkspeed = 0;
		this.walkDirectionVector = new Vector2D();
		this.walkDirectionString = Direction.down;
		this.animationPlayer = new AnimationPlayer(GameResources.PLAYER_ANIMATION_SET, GameResources.PLAYER_ANIMATION_SET.getAnimation("player_stand_" + walkDirectionString));
		this.soundPlayer.addSound("player_walk", GameResources.PLAYER_WALK_SOUND);
		this.hitbox = new CircleHitbox(centerPosition, 35);
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
	}
	
	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		boolean isPressing = false;
		walkDirectionVector.x = 0;
		walkDirectionVector.y = 0;
		if(PlayerInput.isUpKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = -1;
			walkDirectionString = Direction.up;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.7f;
			walkDirectionVector.x = 0.7f;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.7f;
			walkDirectionVector.x = -0.7f;
		}
		if(PlayerInput.isDownKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = 1;
			walkDirectionString = Direction.down;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.7f;
			walkDirectionVector.x = 0.7f;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.7f;
			walkDirectionVector.x = -0.7f;
		}
		if(PlayerInput.isLeftKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = -1;
			walkDirectionString = Direction.left;
		}
		if(PlayerInput.isRightKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = 1;
			walkDirectionString = Direction.right;
		}
		if(isPressing) {
			isWalking = true;
		}

		if(isWalking) {
			animationPlayer.loop("player_walk_" + walkDirectionString);
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
		
		this.moveVector.x = walkDirectionVector.x * currentWalkspeed;
		this.moveVector.y = walkDirectionVector.y * currentWalkspeed;
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
		isWalking = false;
	}
}
