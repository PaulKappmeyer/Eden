package gamelogic;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;

public class Zombie extends Mob{

	public static final int MAX_WALKSPEED = 30;
	public static final float TIME_FOR_MAX_WALKSPEED = 0.1f;

	private float triggerDistance = 300;
	private ZombieWatchBehavior zombieBehavior;
	
	public Zombie(float x, float y) {
		super(x, y, 128, 128);
		walkDirectionString = Main.RANDOM.nextDirection();
		animationPlayer = new AnimationPlayer(GameResources.ZOMBIE_ANIMATION_SET, GameResources.ZOMBIE_ANIMATION_SET.getAnimation("zombie_walk_" + walkDirectionString));
		soundPlayer.addSound("zombie_walk", GameResources.PLAYER_WALK_SOUND);
		zombieBehavior = new ZombieWatchBehavior(this, triggerDistance);
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
			if(zombieBehavior.isTriggered()) {
				isWalking = true;
			}
		}
		
		this.moveVector.x = walkDirectionVector.x * currentWalkspeed;
		this.moveVector.y = walkDirectionVector.y * currentWalkspeed;
	}
}
