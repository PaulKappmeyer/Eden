package gameengine;

import java.awt.Graphics;

import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.Hitbox;
import gameengine.hud.HealthBar;
import gameengine.maths.MyMaths;
import gameengine.maths.Vector2D;
import gameengine.sounds.SoundPlayer;

/**
 * 
 * @author Paul
 *
 */
public abstract class Mob extends MovableObject{

	//Constanst
	private int maxHealth;
	private int maxWalkspeed;
	private float timeForMaxWalkspeed;
	private int maxKnockbackAmount;
	private float maxKnockbackTime;
	
	//Health
	protected boolean gotDamaged = false;
	private boolean alive = true;
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
	private int currentMaxKnockbackAmount = 0;
	private float currentMaxKnockbackTime = 0;
	private float currentKnockbackAmount = 0;
	private float currentKnockbackTime = 0;
	
	public Mob(float x, float y, int width, int height, int maxHealth, int maxWalkspeed, float timeForMaxWalkspeed, int maxKnockbackAmount, float maxKnockbackTime) {
		super(x, y, width, height);
		this.maxHealth = maxHealth;
		this.maxWalkspeed = maxWalkspeed;
		this.maxKnockbackAmount = maxKnockbackAmount;
		this.maxKnockbackTime = maxKnockbackTime;
		
		this.currentHealth = maxHealth;
		this.healthBar = new HealthBar(this);
	}
	
	@Override
	public void update(float tslf) {
		super.update(tslf);
		healthBar.update(tslf);
		animationPlayer.update(tslf);
		image = animationPlayer.getCurrentFrame();
		
		//Damaged
		if (gotDamaged) {
			currentDamagedTime += tslf;
			if (currentDamagedTime >= currentMaxKnockbackTime) {
				if (alive) {
					animationPlayer.reset();
					animationPlayer.stop();
				}
				currentDamagedTime = 0;
				gotDamaged = false;
			}
		}
		
		//Knockback
		if (gotKnockbacked) {
			currentKnockbackTime += tslf;
			if (currentKnockbackTime >= currentMaxKnockbackTime) {
				currentKnockbackTime = 0;
				knockbackVector.x = 0;
				knockbackVector.y = 0;
				gotKnockbacked = false;
			}else {
				currentKnockbackAmount = MyMaths.linearInterpolation(currentMaxKnockbackAmount, 0, currentKnockbackTime, currentMaxKnockbackTime);
			}
		}
		
		//Walking
		if (isWalking) {
			timeWalked += tslf;
			currentWalkspeed = MyMaths.linearInterpolation(0, maxWalkspeed, timeWalked, timeForMaxWalkspeed);
		}
		
		this.moveVector.x = walkDirectionVector.x * currentWalkspeed + knockbackVector.x * currentKnockbackAmount;
		this.moveVector.y = walkDirectionVector.y * currentWalkspeed + knockbackVector.y * currentKnockbackAmount;
	}
	
	//----------------Drawing
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		healthBar.draw(graphics);
		hitbox.draw(graphics);
	}

	//------------------------Damaged
	public void getDamaged(float damageAmount) {
		lossHealth(damageAmount);
		if (!gotDamaged) {
			gotDamaged = true;
			stopWalking();
			healthBar.show();
		}
	}
	
	//Health
	public void healHealth(float amount) {
		currentHealth += amount;
		if (currentHealth > maxHealth) {
			currentHealth = maxHealth;
		}
	}
	
	public void lossHealth(float amount) {
		currentHealth -= amount;
		if (currentHealth <= 0) {
			currentHealth = 0;
			alive = false;
		}
	}
	
	//----------------Knockback
	public void getKnockbacked(Vector2D knockbackVector, int strength, float time) {
		if (!gotKnockbacked && alive) {
			gotKnockbacked = true;
			this.knockbackVector.x = knockbackVector.x;
			this.knockbackVector.y = knockbackVector.y;
			this.knockbackVector.makeUnitVector();
			currentKnockbackTime = 0;
			currentMaxKnockbackAmount = strength;
			currentMaxKnockbackTime = time;
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
		if (walkDirection == null) return;
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
		return maxHealth;
	}
	
	public float getCurrentHealth() {
		return currentHealth;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getMaxKnockbackAmount() {
		return maxKnockbackAmount;
	}
	
	public float getMaxKnockbackTime() {
		return maxKnockbackTime;
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
