package gamelogic.enemies;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gamelogic.GameResources;
import gamelogic.Main;

/**
 * Behavior: When the enemy sees the Player or comes in range of the player it starts to chase the Player.
 * @author Paul
 *
 */
public class Zombie extends Mob{

	private ZombieBehavior zombieBehavior;
	private ZombieWatchBehavior zombieWatchBehavior;
	private int triggerDistance = 400;
	private int viewCone = 25; //in degrees

	public Zombie(float x, float y) {
		super(x, y, 128, 128, 100, Main.RANDOM.nextInt(50, 300), 0.1f, 1000, 0.35f);
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.walkDirectionString = Main.RANDOM.nextDirection();
		this.animationPlayer = new AnimationPlayer(GameResources.ZOMBIE_ANIMATION_SET, GameResources.ZOMBIE_ANIMATION_SET.getAnimation("zombie_walk_" + walkDirectionString));
		this.zombieBehavior = new ZombieBehavior(this, width);
		this.zombieWatchBehavior = new ZombieWatchBehavior(this, triggerDistance, viewCone);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		//Walking
		if (isWalking && !gotDamaged && !gotKnockbacked && isAlive()) {
			move(zombieBehavior.getVectorToPlayer());
			walkDirectionString = walkDirectionVector.getDirection();

			animationPlayer.loop("zombie_walk_" + walkDirectionString);
		} else {
			if (zombieBehavior.isTriggered() || zombieWatchBehavior.isTriggered()) {
				isWalking = true;
			}
		}
	}
	
	@Override
	public void getDamaged(float damageAmount) {
		if (!gotDamaged  && isAlive()) {
			super.getDamaged(damageAmount);
			if (getCurrentHealth() <= 0) {
				animationPlayer.play("zombie_die_" + walkDirectionString);
			} else {
				isWalking = true;
				animationPlayer.loop("zombie_getDamaged_" + walkDirectionString);
			}
		}
	}
}
