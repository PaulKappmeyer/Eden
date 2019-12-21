package gameengine;

import gameengine.graphics.AnimationPlayer;
import gameengine.maths.Vector2D;
import gameengine.sounds.SoundPlayer;
import gamelogic.Direction;

public class Mob extends MovableObject{

	protected AnimationPlayer animationPlayer;
	protected SoundPlayer soundPlayer;
	
	protected boolean isWalking;
	protected float currentWalkspeed;
	protected float timeWalked;
	protected Vector2D walkDirectionVector;
	protected Direction walkDirectionString;
	
	public Mob(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.soundPlayer = new SoundPlayer();
		this.animationPlayer = new AnimationPlayer();
		this.isWalking = false;
		this.currentWalkspeed = 0;
		this.timeWalked = 0;
		this.walkDirectionVector = new Vector2D();
	}
	
	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		animationPlayer.update(tslf);
		image = animationPlayer.getCurrentFrame();
	}

	public void stopWalking() {
		animationPlayer.reset();
		animationPlayer.stop();

		soundPlayer.stop();

		walkDirectionVector.x = 0;
		walkDirectionVector.y = 0;
		timeWalked = 0;
		isWalking = false;
	}
	
	public void move(Vector2D walkVector) {
		walkDirectionVector = walkVector;
		walkDirectionString = walkVector.getDirection();
	}
	
	public void move(Direction walkDirection) {
		if(walkDirection == null) return;
		walkDirectionString = walkDirection;
		switch (walkDirection) {
		case up:
			isWalking = true;
			walkDirectionVector.y = -1;
			break;
		case down:
			isWalking = true;
			walkDirectionVector.y = 1;
			break;
		case left:
			isWalking = true;
			walkDirectionVector.x = -1;
			break;
		case right:
			isWalking = true;
			walkDirectionVector.x = 1;
			break;

		default:
			break;
		}
	}
}
