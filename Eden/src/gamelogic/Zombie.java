package gamelogic;

import java.awt.Graphics;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.hitbox.Hitbox;

public class Zombie extends Mob{

	public static final int MAX_WALKSPEED = 30 + Main.RANDOM.nextInt(20);
	public static final float TIME_FOR_MAX_WALKSPEED = 0.1f;
	public static final int MAX_HEALTH = 400;
	
	private ZombieBehavior zombieBehavior;
	private ZombieWatchBehavior zombieWatchBehavior;
	private int triggerDistance = 400;
	private int viewCone = 25;
	
	private Hitbox hitbox;
	private float currentHealth;
	
	public Zombie(float x, float y) {
		super(x, y, 128, 128);
		this.walkDirectionString = Main.RANDOM.nextDirection();
		this.animationPlayer = new AnimationPlayer(GameResources.ZOMBIE_ANIMATION_SET, GameResources.ZOMBIE_ANIMATION_SET.getAnimation("zombie_walk_" + walkDirectionString));
		this.soundPlayer.addSound("zombie_walk", GameResources.PLAYER_WALK_SOUND);
		this.zombieBehavior = new ZombieBehavior(this, width);
		this.zombieWatchBehavior = new ZombieWatchBehavior(this, triggerDistance, viewCone);
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.currentHealth = MAX_HEALTH;
	}
	
	public void getDamaged(float damageAmount) {
		currentHealth -= damageAmount;
	}
	
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
	}
	
	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		if(isWalking) {
			move(zombieBehavior.getVectorToPlayer());
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
			if(zombieBehavior.isTriggered() || zombieWatchBehavior.isTriggered()) {
				isWalking = true;
			}
		}
		
		this.moveVector.x = walkDirectionVector.x * currentWalkspeed;
		this.moveVector.y = walkDirectionVector.y * currentWalkspeed;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public float getCurrentHealth() {
		return currentHealth;
	}
}
