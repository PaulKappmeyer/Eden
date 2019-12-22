/*
 * 
 */
package gamelogic;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.maths.MyMaths;

/**
 * 
 * @author Paul
 *
 */
public class NPC extends Mob{
	
	public static final int MAX_WALKSPEED = 200;
	public static final float TIME_FOR_MAX_WALKSPEED = 0.5f;
	
	private float timeIdled;

	private float moveTime;
	private float standTime;

	public NPC(float x, float y) {
		super(x, y, 128, 128, 400);
		walkDirectionString = Direction.down;
		animationPlayer = new AnimationPlayer(GameResources.NPC_ANIMATION_SET, GameResources.NPC_ANIMATION_SET.getAnimation("npc_walk_" + walkDirectionString));
		soundPlayer.addSound("npc_walk", GameResources.PLAYER_WALK_SOUND);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		if(isWalking) {
			animationPlayer.loop("npc_walk_" + walkDirectionString);
			soundPlayer.loop("npc_walk");

			timeWalked += tslf;
			currentWalkspeed = MyMaths.linearInterpolation(0, MAX_WALKSPEED, timeWalked, TIME_FOR_MAX_WALKSPEED);

			if(timeWalked >= moveTime) {
				stopWalking();
			}
		}else {
			timeIdled += tslf;

			if(timeIdled >= standTime) {
				if(position.x < 100) move(Direction.right, 2);
				else if(position.x > 700) move(Direction.left, 2);
				else if(position.y < 100) move(Direction.down, 2);
				else if(position.y > 700) move(Direction.up, 2);
				else {
					walkDirectionString = Main.RANDOM.nextDirection();
					moveTime = Main.RANDOM .nextFloat() * 3;
					standTime = Main.RANDOM.nextFloat() * 1.5f;
					move(walkDirectionString, moveTime);
				}

				timeIdled = 0;
			}
		}

		this.moveVector.x = walkDirectionVector.x * currentWalkspeed;
		this.moveVector.y = walkDirectionVector.y * currentWalkspeed;
	}

	public void move(Direction direction, float moveTime) {
		move(direction);
		this.moveTime = moveTime;
	}

}
