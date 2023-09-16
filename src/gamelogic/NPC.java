/*
 * 
 */
package gamelogic;

import gameengine.Direction;
import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;

/**
 * 
 * @author Paul
 *
 */
public class NPC extends Mob{
	
	private float timeIdled;

	private float moveTime;
	private float standTime;

	public NPC(float x, float y) {
		super(x, y, 128, 128, 400, 200, 0.5f, 1000, 0.35f);
		walkDirectionString = Direction.down;
		animationPlayer = new AnimationPlayer(GameResources.NPC_ANIMATION_SET, GameResources.NPC_ANIMATION_SET.getAnimation("npc_walk_" + walkDirectionString));
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		if (isWalking) {
			animationPlayer.loop("npc_walk_" + walkDirectionString);

			if (getTimeWalked() >= moveTime) {
				stopWalking();
			}
		}else {
			timeIdled += tslf;

			if (timeIdled >= standTime) {
				if (position.x < 100) {
					move(Direction.right, 2);
				}
				else if (position.x > 700) {
					move(Direction.left, 2);
				}
				else if (position.y < 100) {
					move(Direction.down, 2);
				}
				else if (position.y > 700) {
					move(Direction.up, 2);
				}
				else {
					walkDirectionString = Main.RANDOM.nextDirection();
					moveTime = Main.RANDOM .nextFloat() * 3;
					standTime = Main.RANDOM.nextFloat() * 1.5f;
					move(walkDirectionString, moveTime);
				}

				timeIdled = 0;
			}
		}
	}

	public void move(Direction direction, float moveTime) {
		move(direction);
		this.moveTime = moveTime;
	}

}
