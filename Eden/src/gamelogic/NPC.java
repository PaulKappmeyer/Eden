/*
 * 
 */
package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;
import gameengine.graphics.AnimationPlayer;
import gameengine.maths.Vector2D;
import gameengine.sounds.SoundPlayer;

/**
 * 
 * @author Paul
 *
 */
public class NPC extends DrawableObject{
	public static final int MAX_WALKSPEED = 200;
	public static final float TIME_FOR_MAX_WALKSPEED = 0.5f;
	private float timeWalked;
	private float timeIdled;
	private boolean isMoving;	
	private int currentWalkspeed;
	private Vector2D walkDirectionVector;
	private Direction walkDirectionString;

	private int width;
	private int height;
	private BufferedImage image;
	private AnimationPlayer animationPlayer;
	private SoundPlayer soundPlayer;
	private float moveTime;
	private float standTime;

	public NPC(float x, float y) {
		super(x, y);
		this.width = 128;
		this.height = 128;
		this.isMoving = false;
		this.currentWalkspeed = 0;
		this.walkDirectionVector = new Vector2D();
		animationPlayer = new AnimationPlayer(GameResources.NPC_ANIMATION_SET);	
		soundPlayer = new SoundPlayer();
		soundPlayer.addSound("npc_walk", GameResources.PLAYER_WALK_SOUND);
		animationPlayer.play("npc_walk_down");
	}

	@Override
	public void update(float tslf) {
		if(isMoving) {
			animationPlayer.loop("npc_walk_" + walkDirectionString);
			soundPlayer.loop("npc_walk");

			timeWalked += tslf;
			if(timeWalked >= TIME_FOR_MAX_WALKSPEED) {
				currentWalkspeed = MAX_WALKSPEED;
			} else {
				currentWalkspeed = (int) (MAX_WALKSPEED * (timeWalked / TIME_FOR_MAX_WALKSPEED));
			}

			if(timeWalked >= moveTime) {
				stopWalking();
			}
		}else {
			timeIdled += tslf;

			if(timeIdled >= standTime) {
				if(position.x < 100) move(Direction.right, 2);
				else if(position.x > 700) move(Direction.left, 2);
				else if(position.y < 100) move(Direction.down, 2);
				else if(position.y > 700) move(Direction.up, 2);
				else {
					walkDirectionString = Main.RANDOM.nextDirection();
					moveTime = Main.RANDOM .nextFloat() * 3;
					standTime = Main.RANDOM.nextFloat() * 1.5f;
					move(walkDirectionString, moveTime);
				}

				timeIdled = 0;
			}
		}
		animationPlayer.update(tslf);
		image = animationPlayer.getCurrentFrame();

		this.position.x += walkDirectionVector.x * currentWalkspeed * tslf;
		this.position.y += walkDirectionVector.y * currentWalkspeed * tslf;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
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

	public void move(Direction direction) {
		if(direction == null) return;
		walkDirectionString = direction;
		switch (direction) {
		case up:
			isMoving = true;
			walkDirectionVector.y = -1;
			break;
		case down:
			isMoving = true;
			walkDirectionVector.y = 1;
			break;
		case left:
			isMoving = true;
			walkDirectionVector.x = -1;
			break;
		case right:
			isMoving = true;
			walkDirectionVector.x = 1;
			break;

		default:
			break;
		}
	}

	public void move(Direction direction, float moveTime) {
		move(direction);
		this.moveTime = moveTime;
	}

}
