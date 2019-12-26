package gameengine;

import java.awt.Graphics;

import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.Hitbox;
import gameengine.hud.HealthBar;
import gameengine.maths.MyMaths;
import gameengine.maths.Vector2D;
import gameengine.sounds.SoundPlayer;
import gamelogic.Direction;

/**
 * 
 * @author Paul
 *
 */
public abstract class Mob extends MovableObject{
	
	//Constanst
	private int MAX_HEALTH;
	private int MAX_WALKSPEED;
	private float TIME_FOR_MAX_WALKSPEED;
	private int MAX_KNOCKBACK_AMOUNT;
	private float MAX_KNOCKBACK_TIME;
	
	//Health
	protected boolean alive = true;
	protected boolean gotDamaged = false;
	private float currentHealth;
	private HealthBar healthBar;
	private float currentDamagedTime = 0;
	
	//Hitbox
	protected Hitbox hitbox;
	
	//Animations & Sound
	protected AnimationPlayer animationPlayer = new AnimationPlayer();
	protected SoundPlayer soundPlayer = new SoundPlayer();
	
	//Walking
	protected boolean isWalking = false;
	protected Direction walkDirectionString = Direction.down;
	protected Vector2D walkDirectionVector = new Vector2D();
	private float currentWalkspeed = 0;
	private float timeWalked = 0;
	
	//Knockback
	protected boolean gotKnockbacked = false;
	private Vector2D knockbackVector = new Vector2D();
	private int CURRENT_MAX_KNOCKBACK_AMMOUNT;
	private float CURRENT_MAX_KNOCKBACK_TIME;
	private float currentKnockbackAmount = 0;
	private float currentKnockbackTime = 0;
	
	public Mob(float x, float y, int width, int height, int MAX_HEALTH, int MAX_WALKSPEED, float TIME_FOR_MAX_WALKSPEED, int MAX_KNOCKBACK_AMOUNT, float MAX_KNOCKBACK_TIME) {
		super(x, y, width, height);
		this.MAX_HEALTH = MAX_HEALTH;
		this.MAX_WALKSPEED = MAX_WALKSPEED;
		this.TIME_FOR_MAX_WALKSPEED = TIME_FOR_MAX_WALKSPEED;
		this.MAX_KNOCKBACK_AMOUNT = MAX_KNOCKBACK_AMOUNT;
		this.MAX_KNOCKBACK_TIME = MAX_KNOCKBACK_TIME;
		
		this.currentHealth = MAX_HEALTH;
		this.healthBar = new HealthBar(this);
	}
	
	@Override
	public void update(float tslf) {
		super.update(tslf);
		healthBar.update(tslf);
		animationPlayer.update(tslf);
		image = animationPlayer.getCurrentFrame();
		
		//Damaged
		if(gotDamaged) {
			currentDamagedTime += tslf;
			if(currentDamagedTime >= MAX_KNOCKBACK_TIME) {
				animationPlayer.stop();
				currentDamagedTime = 0;
				gotDamaged = false;
			}
		}
		
		//Knockback
		if(gotKnockbacked) {
			currentKnockbackTime += tslf;
			if(currentKnockbackTime >= CURRENT_MAX_KNOCKBACK_TIME) {
				currentKnockbackTime = 0;
				knockbackVector.x = 0;
				knockbackVector.y = 0;
				gotKnockbacked = false;
			}else {
				currentKnockbackAmount = MyMaths.linearInterpolation(CURRENT_MAX_KNOCKBACK_AMMOUNT, 0, currentKnockbackTime, CURRENT_MAX_KNOCKBACK_TIME);
			}
		}
		
		//Walking
		if(isWalking) {
			timeWalked += tslf;
			currentWalkspeed = MyMaths.linearInterpolation(0, MAX_WALKSPEED, timeWalked, TIME_FOR_MAX_WALKSPEED);
		}
		
		this.moveVector.x = walkDirectionVector.x * currentWalkspeed + knockbackVector.x * currentKnockbackAmount;
		this.moveVector.y = walkDirectionVector.y * currentWalkspeed + knockbackVector.y * currentKnockbackAmount;
	}
	
	//----------------Drawing
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		healthBar.draw(graphics);
	}

	//------------------------Damaged
	public void getDamaged(float damageAmount) {
		if(!gotDamaged) {
			gotDamaged = true;
			currentHealth -= damageAmount;
			stopWalking();
			healthBar.show();
		}
	}
	
	//----------------Knockback
	public void getKnockbacked(Vector2D knockbackVector, int strength, float time) {
		if(!gotKnockbacked && alive) {
			gotKnockbacked = true;
			this.knockbackVector.x = knockbackVector.x;
			this.knockbackVector.y = knockbackVector.y;
			this.knockbackVector.makeUnitVector();
			currentKnockbackTime = 0;
			CURRENT_MAX_KNOCKBACK_AMMOUNT = strength;
			CURRENT_MAX_KNOCKBACK_TIME = time;
		}
	}
	
	//-------------------------Walking
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
	
	//-------------------------Getters
	public int getMaxHealth() {
		return MAX_HEALTH;
	}
	
	public float getCurrentHealth() {
		return currentHealth;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getMAX_KNOCKBACK_AMOUNT() {
		return MAX_KNOCKBACK_AMOUNT;
	}
	
	public float getMAX_KNOCKBACK_TIME() {
		return MAX_KNOCKBACK_TIME;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public float getTimeWalked() {
		return timeWalked;
	}
	
	public Vector2D getWalkDirectionVector() {
		return walkDirectionVector;
	}
	
	public Direction getWalkDirectionString() {
		return walkDirectionString;
	}
}
