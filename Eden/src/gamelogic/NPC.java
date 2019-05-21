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
	private boolean isMoving;	
	private int currentWalkspeed;
	private Vector2D walkDirectionVector;
	private String walkDirectionString;

	private int width;
	private int height;
	private BufferedImage image;
	private AnimationPlayer animationPlayer;
	private SoundPlayer soundPlayer;

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
		animationPlayer.loop("npc_walk_down");
	}

	String direction = Player.DOWN;
	@Override
	public void update(float tslf) {
		if(position.y > 500) {
			direction = Player.UP;
		}
		if(position.y < 100) {
			direction = Player.DOWN;
		}
		move(direction);

		if(isMoving) {
			animationPlayer.loop("npc_walk_" + walkDirectionString);
			animationPlayer.update(tslf);
			//soundPlayer.loop("npc_walk");

			timeWalked += tslf;
			if(timeWalked >= TIME_FOR_MAX_WALKSPEED) {
				currentWalkspeed = MAX_WALKSPEED;
			} else {
				currentWalkspeed = (int) (MAX_WALKSPEED * (timeWalked / TIME_FOR_MAX_WALKSPEED));
			}
		} else {
			animationPlayer.reset();
			animationPlayer.stop();

			soundPlayer.stop();

			walkDirectionVector.x = 0;
			walkDirectionVector.y = 0;
			timeWalked = 0;
			isMoving = false;
		}
		image = animationPlayer.getCurrentFrame();

		this.position.x += walkDirectionVector.x * currentWalkspeed * tslf;
		this.position.y += walkDirectionVector.y * currentWalkspeed * tslf;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}

	public void move(String direction) {
		if(direction == null) return;
		switch (direction) {
		case Player.UP:
			isMoving = true;
			walkDirectionVector.y = -1;
			walkDirectionString = Player.UP;
			break;
		case Player.DOWN:
			isMoving = true;
			walkDirectionVector.y = 1;
			walkDirectionString = Player.DOWN;
			break;
		case Player.LEFT:
			isMoving = true;
			walkDirectionVector.x = -1;
			walkDirectionString = Player.LEFT;
			break;
		case Player.RIGHT:
			isMoving = true;
			walkDirectionVector.x = 1;
			walkDirectionString = Player.RIGHT;
			break;

		default:
			break;
		}
	}

}
