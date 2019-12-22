package gamelogic;

import java.awt.Graphics;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.hitbox.Hitbox;
import gameengine.hud.HealthBar;
import gameengine.maths.MyMaths;
import gameengine.maths.Vector2D;

public class Zombie extends Mob{

	public static final int MAX_WALKSPEED = 30 + Main.RANDOM.nextInt(20);
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
	private float currentKnockbackAmount;
	private float time;

	private HealthBar healthBar;
	
	public Zombie(float x, float y) {
		super(x, y, 128, 128, 400);
		this.walkDirectionString = Main.RANDOM.nextDirection();
		this.animationPlayer = new AnimationPlayer(GameResources.ZOMBIE_ANIMATION_SET, GameResources.ZOMBIE_ANIMATION_SET.getAnimation("zombie_walk_" + walkDirectionString));
		this.soundPlayer.addSound("zombie_walk", GameResources.PLAYER_WALK_SOUND);
		this.zombieBehavior = new ZombieBehavior(this, width);
		this.zombieWatchBehavior = new ZombieWatchBehavior(this, triggerDistance, viewCone);
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.currentHealth = MAX_HEALTH;
		this.knockbackVector = new Vector2D();
		this.healthBar = new HealthBar(this);
	}

	public void getDamaged(float damageAmount) {
		if(!gotDamaged) {
			healthBar.show();
			gotDamaged = true;
			currentHealth -= damageAmount;
			stopWalking();
		}
	}

	public void getKnockbacked(Vector2D knockbackVector) {
		this.knockbackVector.x = knockbackVector.x;
		this.knockbackVector.y = knockbackVector.y;
		currentKnockbackAmount = MAX_KNOCKBACK_AMOUNT;
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

		if(gotDamaged) {
			time += tslf;

			if(time >= MAX_KNOCKBACK_TIME) {
				time = 0;
				knockbackVector.x = 0;
				knockbackVector.y = 0;
				animationPlayer.reset();
				animationPlayer.stop();
				gotDamaged = false;
				isWalking = true;
			}else {
				currentKnockbackAmount = MAX_KNOCKBACK_AMOUNT - MAX_KNOCKBACK_AMOUNT * time/MAX_KNOCKBACK_TIME;
			}
		}
		else if(isWalking) {
			move(zombieBehavior.getVectorToPlayer());
			walkDirectionString = walkDirectionVector.getDirection();

			animationPlayer.loop("zombie_walk_" + walkDirectionString);
			soundPlayer.loop("zombie_walk");

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
