package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;
import gameengine.graphics.AnimationPlayer;
import gameengine.maths.Vector2D;
import gameengine.sounds.SoundPlayer;

public class Zombie extends DrawableObject{

	public static final int MAX_WALKSPEED = 90;
	public static final float TIME_FOR_MAX_WALKSPEED = 0.1f;
	private float timeWalked;
	private boolean isMoving;	
	private int currentWalkspeed;
	private Vector2D walkDirectionVector;
	private Direction walkDirectionString;

	private int width;
	private int height;
	private BufferedImage image;
	private AnimationPlayer animationPlayer;
	private SoundPlayer soundPlayer;
	private float triggerDistance = 300;
	
	public Zombie(float x, float y) {
		super(x, y);
		this.width = 128;
		this.height = 128;
		this.isMoving = false;
		this.currentWalkspeed = 0;
		this.walkDirectionVector = new Vector2D();
		this.walkDirectionString = Main.RANDOM.nextDirection();
		animationPlayer = new AnimationPlayer(GameResources.ZOMBIE_ANIMATION_SET);
		soundPlayer = new SoundPlayer();
		soundPlayer.addSound("zombie_walk", GameResources.PLAYER_WALK_SOUND);
		animationPlayer.play("zombie_walk_" + walkDirectionString);
	}
	
	@Override
	public void update(float tslf) {
		if(isMoving) {
			float playerCenterX = Main.player.position.x + Main.player.getWidth()/2;
			float playerCenterY = Main.player.position.y + Main.player.getHeight()/2;
			Vector2D playerCenterPosition = new Vector2D(playerCenterX, playerCenterY);
			float zombieCenterX = this.position.x + this.width/2;
			float zombieCenterY = this.position.y + this.height/2;
			Vector2D zombieCenterPosition = new Vector2D(zombieCenterX, zombieCenterY);
			walkDirectionVector = (playerCenterPosition.subtract(zombieCenterPosition)).makeUnitVector();
			walkDirectionString = walkDirectionVector.getDirection();
			
			animationPlayer.loop("zombie_walk_" + walkDirectionString);
			soundPlayer.loop("zombie_walk");

			timeWalked += tslf;
			if(timeWalked >= TIME_FOR_MAX_WALKSPEED) {
				currentWalkspeed = MAX_WALKSPEED;
			} else {
				currentWalkspeed = (int) (MAX_WALKSPEED * (timeWalked / TIME_FOR_MAX_WALKSPEED));
			}
		}else {
			float playerCenterX = Main.player.position.x + Main.player.getWidth()/2;
			float playerCenterY = Main.player.position.y + Main.player.getHeight()/2;
			Vector2D playerCenterPosition = new Vector2D(playerCenterX, playerCenterY);
			float zombieCenterX = this.position.x + this.width/2;
			float zombieCenterY = this.position.y + this.height/2;
			Vector2D zombieCenterPosition = new Vector2D(zombieCenterX, zombieCenterY);
			
			if(triggerDistance*triggerDistance >= zombieCenterPosition.distanceSquared(playerCenterPosition)) {
				isMoving = true;
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

}
