package gamelogic.enemies;

import java.awt.Graphics;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.hitbox.Hitbox;
import gameengine.hud.HealthBar;
import gameengine.maths.MyMaths;
import gameengine.maths.Vector2D;
import gamelogic.GameResources;
import gamelogic.Main;

/**
 * 
 * @author Paul
 *
 */
public class Zombie extends Mob{

	public static final int MAX_WALKSPEED = 300 + Main.RANDOM.nextInt(50);
	public static final float TIME_FOR_MAX_WALKSPEED = 0.1f;
	public static final int MAX_KNOCKBACK_AMOUNT = 1000;
	public static final float MAX_KNOCKBACK_TIME = 0.35f;

	private ZombieBehavior zombieBehavior;
	private ZombieWatchBehavior zombieWatchBehavior;
	private int triggerDistance = 400;
	private int viewCone = 25;

	private Hitbox hitbox;
	private boolean gotDamaged;
	
	private Vector2D knockbackVector;
	private boolean gotKnockbacked;
	private float currentKnockbackAmount;
	private float currentKnockbackTime;

	public boolean isAlive = true;
	
	private HealthBar healthBar;

	public Zombie(float x, float y) {
		super(x, y, 128, 128, 100);
		this.walkDirectionString = Main.RANDOM.nextDirection();
		this.animationPlayer = new AnimationPlayer(GameResources.ZOMBIE_ANIMATION_SET, GameResources.ZOMBIE_ANIMATION_SET.getAnimation("zombie_walk_" + walkDirectionString));
		this.zombieBehavior = new ZombieBehavior(this, width);
		this.zombieWatchBehavior = new ZombieWatchBehavior(this, triggerDistance, viewCone);
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.currentHealth = MAX_HEALTH;
		this.knockbackVector = new Vector2D();
		this.healthBar = new HealthBar(this);
	}

	public void getDamaged(float damageAmount) {
		if(!gotDamaged  && isAlive) {
			gotDamaged = true;
			currentHealth -= damageAmount;
			healthBar.show();
			stopWalking();
			if(currentHealth <= 0) {
				isAlive = false;
				animationPlayer.play("zombie_die_" + walkDirectionString);
			}else {
				animationPlayer.loop("zombie_getDamaged_" + walkDirectionString);
			}
		}
	}

	public void getKnockbacked(Vector2D knockbackVector) {
		if(!gotKnockbacked && isAlive) {
			gotKnockbacked = true;
			this.knockbackVector.x = knockbackVector.x;
			this.knockbackVector.y = knockbackVector.y;
			this.knockbackVector.makeUnitVector();
			currentKnockbackTime = 0;
		}
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		healthBar.draw(graphics);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		healthBar.update(tslf);

		if(gotKnockbacked) {
			currentKnockbackTime += tslf;
			
			if(currentKnockbackTime >= MAX_KNOCKBACK_TIME) {
				currentKnockbackTime = 0;
				knockbackVector.x = 0;
				knockbackVector.y = 0;
				gotKnockbacked = false;
				gotDamaged = false;
				isWalking = true;
			}else {
				currentKnockbackAmount = MyMaths.linearInterpolation(MAX_KNOCKBACK_AMOUNT, 0, currentKnockbackTime, MAX_KNOCKBACK_TIME);
			}
		}
		if(isWalking && !gotDamaged && isAlive) {
			move(zombieBehavior.getVectorToPlayer());
			walkDirectionString = walkDirectionVector.getDirection();

			animationPlayer.loop("zombie_walk_" + walkDirectionString);

			timeWalked += tslf;
			currentWalkspeed = MyMaths.linearInterpolation(0, MAX_WALKSPEED, timeWalked, TIME_FOR_MAX_WALKSPEED);
		}else {
			if(zombieBehavior.isTriggered() || zombieWatchBehavior.isTriggered()) {
				isWalking = true;
			}
		}

		this.moveVector.x = walkDirectionVector.x * currentWalkspeed + knockbackVector.x * currentKnockbackAmount;
		this.moveVector.y = walkDirectionVector.y * currentWalkspeed + knockbackVector.y * currentKnockbackAmount;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}
}
