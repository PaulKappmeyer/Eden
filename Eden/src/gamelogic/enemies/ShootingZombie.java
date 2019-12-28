package gamelogic.enemies;

import java.awt.Graphics;
import java.util.LinkedList;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.maths.Vector2D;
import gamelogic.GameResources;
import gamelogic.Main;
import gamelogic.Projectile;

/**
 * Behavior: Acts the same like the {@link Zombie}, but when it chases the Player and is in range it starts to shoot at the Player.
 * @author Paul
 *
 */
public class ShootingZombie extends Mob{

	public static final float RANGE = 500;
	public static final float SHOOT_COOLDOWN = 2.f;

	private ZombieBehavior zombieBehavior;
	private ZombieWatchBehavior zombieWatchBehavior;
	private int triggerDistance = 400;
	private int viewCone = 25;

	public LinkedList<Projectile> projectiles;
	private float currentShootCooldown = 0;
	private boolean canShoot = false;

	public ShootingZombie(float x, float y) {
		super(x, y, 128, 128, 200, Main.RANDOM.nextInt(50, 100), 0.1f, 1000, 0.35f);
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.walkDirectionString = Main.RANDOM.nextDirection();
		this.animationPlayer = new AnimationPlayer(GameResources.ZOMBIE_ANIMATION_SET, GameResources.ZOMBIE_ANIMATION_SET.getAnimation("zombie_walk_" + walkDirectionString));
		this.zombieBehavior = new ZombieBehavior(this, width);
		this.zombieWatchBehavior = new ZombieWatchBehavior(this, triggerDistance, viewCone);
		this.projectiles = new LinkedList<Projectile>();
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		//This loop is running from last element to first element because elements get deleted;
		for(int i = projectiles.size()-1; i >= 0; i--) {
			Projectile projectile = projectiles.get(i);
			projectile.update(tslf);
			if(Main.player.getHitbox().isOverlapping(projectile.getHitbox())) {
				Main.player.getKnockbacked(projectile.getVelocityVector(), Main.player.getMAX_KNOCKBACK_AMOUNT()/2, Main.player.getMAX_KNOCKBACK_TIME()/2);
				Main.player.getDamaged(50);
				projectiles.remove(i);
			}
			else if(projectile.getX() < 0 || projectile.getY() < 0 || projectile.getX() > Main.tiledMap.getFullWidth() || projectile.getY() > Main.tiledMap.getFullHeight()) {
				projectiles.remove(i);
				continue;
			}
		}

		//Shooting
		if(canShoot && isAlive()) {
			currentShootCooldown += tslf;
			if(currentShootCooldown >= SHOOT_COOLDOWN/2) {
				Vector2D playerPosition = Main.player.getCenterPosition();
				Vector2D velocityVector = new Vector2D(playerPosition.x - centerPosition.x, playerPosition.y - centerPosition.y);
				Projectile projectile = new Projectile(getCenterPositionX(), getCenterPositionY(), velocityVector.x, velocityVector.y);
				projectiles.add(projectile);
				getKnockbacked(new Vector2D(-velocityVector.x, -velocityVector.y), getMAX_KNOCKBACK_AMOUNT()/2, getMAX_KNOCKBACK_TIME());
				canShoot = false;
				currentShootCooldown = 0;
				isWalking = true;
			}
		}
		//Walking
		if(isWalking && !gotDamaged && !gotKnockbacked && !canShoot && isAlive()) {
			move(zombieBehavior.getVectorToPlayer());
			walkDirectionString = walkDirectionVector.getDirection();
			animationPlayer.loop("zombie_walk_" + walkDirectionString);

			//Prepare for shoot
			if(centerPosition.distanceSquared(Main.player.getCenterPosition()) <= RANGE*RANGE) { 
				currentShootCooldown += tslf;
				if(currentShootCooldown >= SHOOT_COOLDOWN) {
					canShoot = true;
					currentShootCooldown = 0;
					stopWalking();
				}
			}else {
				currentShootCooldown = 0;
			}
		}else {
			if(zombieBehavior.isTriggered() || zombieWatchBehavior.isTriggered()) {
				isWalking = true;
			}
		}
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
	}

	@Override
	public void getDamaged(float damageAmount) {
		if(!gotDamaged  && isAlive()) {
			super.getDamaged(damageAmount);
			if(getCurrentHealth() <= 0) {
				animationPlayer.play("zombie_die_" + walkDirectionString);
			}else {
				isWalking = true;
				animationPlayer.loop("zombie_getDamaged_" + walkDirectionString);
			}
		}
	}
}
