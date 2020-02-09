/*
 * 
 */
package gamelogic.player;

import gameengine.Direction;
import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.maths.Vector2D;
import gamelogic.GameResources;
import gamelogic.Gun;
import gamelogic.Main;

/**
 * 
 * @author Paul
 *
 */
public class Player extends Mob{

	private Gun gun;

	private int level = 1;
	private int exp = 0;
	private int maxExp[] = new int[] {100, 110, 130, 160, 200, 250, 300, 500, 1000, 2000, 5000, 10000, 20000, 30000, 40000, 50000};

	public Player(float x, float y) {
		super(x, y, 128, 128, 400, 400, 0.1f, 700, 0.2f);
		this.animationPlayer = new AnimationPlayer(GameResources.PLAYER_ANIMATION_SET, GameResources.PLAYER_ANIMATION_SET.getAnimation("player_stand_" + walkDirectionString));
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.gun = new Gun(this, 100, 0.1f, 1);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		gun.update(tslf);
		
		if(exp >= maxExp[level-1]) {
			exp -= maxExp[level-1];
			if(level < maxExp.length-1) level++;
			else {
				level = maxExp.length;
				exp = maxExp[level-1];
			}
		}

		//Shooting
		if(PlayerInput.isLeftMouseButtonDown()) {
			if(gun.canShoot()) {
				Vector2D mousePosition = PlayerInput.getMousePosition();
				Vector2D velocityVector = new Vector2D(mousePosition.x - centerPosition.x + Main.camera.getX(), mousePosition.y - centerPosition.y + Main.camera.getY());
				gun.shoot(getCenterPosition(), velocityVector);
				stopWalking();
				getKnockbacked(new Vector2D(-velocityVector.x, -velocityVector.y), getMaxKnockbackAmount()/2, getMaxKnockbackTime()/2);
			}
		}
		
		//Walking
		if(!gotDamaged) {
			checkInput();

			if(isWalking) {
				animationPlayer.loop("player_walk_" + walkDirectionString);
			}
		}
	}
	
	public void addExp(int ammount) {
		exp += ammount;
	}
	
	public void checkInput() {
		boolean isPressing = false;
		walkDirectionVector.x = 0;
		walkDirectionVector.y = 0;
		if(PlayerInput.isUpKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = -1;
			walkDirectionString = Direction.up;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.7f;
			walkDirectionVector.x = 0.7f;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.7f;
			walkDirectionVector.x = -0.7f;
		}
		if(PlayerInput.isDownKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = 1;
			walkDirectionString = Direction.down;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.7f;
			walkDirectionVector.x = 0.7f;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.7f;
			walkDirectionVector.x = -0.7f;
		}
		if(PlayerInput.isLeftKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = -1;
			walkDirectionString = Direction.left;
		}
		if(PlayerInput.isRightKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = 1;
			walkDirectionString = Direction.right;
		}
		if(isPressing) {
			isWalking = true;
		} else 	if(walkDirectionVector.x == 0 && walkDirectionVector.y == 0) {
			stopWalking();
		}
	}

	//------------------------Damaged
	@Override
	public void getDamaged(float damageAmount) {
		if(!gotDamaged) {
			super.getDamaged(damageAmount);
			animationPlayer.loop("player_getDamaged_" + walkDirectionString);
		}
	}

	//--------------------------Getters
	public int getLevel() {
		return level;
	}

	public int getExp() {
		return exp;
	}

	public int[] getMaxExp() {
		return maxExp;
	}
	
	public Gun getGun() {
		return gun;
	}
}
